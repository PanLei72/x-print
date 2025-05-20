package com.x.print.application;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.deepoove.poi.XWPFTemplate;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.x.print.domain.model.label.ILabelService;
import com.x.print.domain.model.label.Label;
import com.x.print.domain.model.labeldesign.ILabelDesignService;
import com.x.print.domain.model.labeldesign.LabelDesign;
import com.x.print.domain.model.poitltemplate.IPoitlTemplateService;
import com.x.print.domain.model.poitltemplate.PoitlTemplate;
import com.x.print.domain.model.printqueue.IPrintQueueService;
import com.x.print.domain.model.printqueue.PrintQueue;
import com.x.print.infrastructure.constants.LabelCategory;
import com.x.print.infrastructure.constants.Orientation;
import com.x.print.infrastructure.constants.PrintStatus;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.FileStorageLocator;
import io.jmix.core.security.Authenticated;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Component;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.INTERFACES)
public class PrintWorker implements Runnable {

    protected static final Logger logger = LogManager.getLogger(PrintWorker.class);

    @Autowired
    private IPrintQueueService printQueueService;

    @Autowired
    private ILabelDesignService labelDesignService;

    @Autowired
    private IPoitlTemplateService poitlTemplateService;

    @Autowired
    private FileStorageLocator fileStorageLocator;

    @Autowired
    private ILabelService labelService;
    protected boolean workerStatus = true;

    private String printerName = null;

    protected Integer sleepInterval = 3000;


    public synchronized void setWorkerStatus(boolean workerStatus) {
        this.workerStatus = workerStatus;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    // authenticates the entire method
    @Authenticated
    @ManagedOperation
    @Override
    public void run() {
        try {
            while (workerStatus) {
                try {
                    Thread.sleep(sleepInterval);

                    List<PrintQueue> printQueueList = printQueueService.loadPrintQueueByPrinterName(printerName);
                    if (printQueueList != null && printQueueList.size() > 0) {
                        //开始打印
                        //通俗理解就是书、文档
                        Book book = new Book();
                        //把 PageFormat 和 Printable 添加到书中，组成一个页面

                        List<PrintQueue> successPrintQueueList = new ArrayList<>();
                        for (PrintQueue printQueue : printQueueList) {
                            Label label = printQueue.getLabel();

                            LabelCategory labelCategory = label.getCategory();

                            if(LabelCategory.LABEL.equals(labelCategory))
                            {
                                this.generateLabelBook(label, book);
                            }else if(LabelCategory.POITL.equals(labelCategory)){
                                this.generatePOITLBook(label, book);
                            }else {
                                //默认为Java模板标签
                                this.generateLabelBook(label, book);
                            }
                            //保存打印成功的标签
                            successPrintQueueList.add(printQueue);
                        }
                        if(book.getNumberOfPages() == 0)
                        {
                            continue;
                        }
                        this.printBook(successPrintQueueList, book);
                    }
                } catch (Exception e) {
                    logger.error("[" + printerName + "]循环打印异常:" + e.getMessage());
                    logger.error("[" + printerName + "]循环打印异常:", e);
                }
            }
            logger.warn("[" + printerName + "]监听线程关闭");
        } catch (Exception e) {
            logger.error("[" + printerName + "]Run运行异常:" + e.getMessage());
            logger.error("[" + printerName + "]Run运行异常:", e);
        }
    }
    /**
     * 生产标签文档
     */
    private void generateLabelBook(Label label, Book book) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String labelName = label.getLabelName();//标签名称
        int labelQuantity = label.getLabelQuantity();//标签数量

        LabelDesign labelDesign = labelDesignService.loadLabelDesignByLabelName(labelName);
        if (labelDesign == null) {
            logger.error("[" + printerName + "]标签【" + labelName + "】不存在");
            throw new RuntimeException("[" + printerName + "]标签【" + labelName + "】不存在");
        }

        String className = labelDesign.getClassName();

        int labelWidth = labelDesign.getWidth().intValue();
        int labelHeight = labelDesign.getHeight().intValue();

        logger.info("[" + printerName + "]getOrientation：" + labelDesign.getOrientation());

        PageFormat pageFormat = new PageFormat();
        if (Orientation.LANDSCAPE.equals(labelDesign.getOrientation())) {
            pageFormat.setOrientation(PageFormat.LANDSCAPE); //横向打印
        } else {
            pageFormat.setOrientation(PageFormat.PORTRAIT); //纵向打印
        }
        //通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
        Paper paper = new Paper();
        paper.setSize(labelWidth, labelHeight);//纸张大小

        //p.setImageableArea(0,0,284,340);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        paper.setImageableArea(0, 0, labelWidth, labelHeight);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
        pageFormat.setPaper(paper);

        Class<?> cls = Class.forName(className);

        Object labelObject = cls.newInstance();//初始化一个实例
        LabelPrintable labelPrintableInstance = (LabelPrintable) labelObject;
        if (labelPrintableInstance == null) {
            logger.error("[" + printerName + "]实例化对象失败");
            throw new RuntimeException("[" + printerName + "]实例化对象失败");
        }
        labelPrintableInstance.setPrinterName(printerName);

        for (int i = 0; i < labelQuantity; i++) {
            String labelDataStr = label.getLabelData();
            JSONObject labelData = JSONObject.parseObject(labelDataStr);
            labelPrintableInstance.setLabelData(labelData);

            book.append(labelPrintableInstance, pageFormat);
        }
    }


    /**
     * 生成POITL标签文档
     *
     * @param label
     * @param book
     */
    private void generatePOITLBook(Label label, Book book) throws IOException {
        String labelName = label.getLabelName();//标签名称
        int labelQuantity = label.getLabelQuantity();//标签数量

        PoitlTemplate poitlTemplate = poitlTemplateService.loadPoitlTemplateByTemplateName(labelName);
        if (poitlTemplate == null) {
            logger.error("[" + printerName + "]POITL模板【" + labelName + "】不存在");
            throw new RuntimeException("[" + printerName + "]POITL模板【" + labelName + "】不存在");
        }

        FileRef poitlTemplateFile = poitlTemplate.getTemplateFile();
        // 获取文件存储
        FileStorage fileStorage = fileStorageLocator.getDefault();

        // 使用 Downloader 下载文件内容
        InputStream inputStream = fileStorage.openStream(poitlTemplateFile);


        // 1. 使用 POITL 生成 Word 文档
        Map<String, Object> data = new HashMap<>();

        String labelDataStr = label.getLabelData();
        if(StringUtils.isNotBlank(labelDataStr))
        {
//            JSONObject labelData = JSONObject.parseObject(labelDataStr);

            Map<String, String> params = JSONObject.parseObject(labelDataStr, new TypeReference<Map<String, String>>(){});
            data.putAll(params);
        }

        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(data);
        try (FileOutputStream out = new FileOutputStream("output.docx")) {
            template.write(out);
        }

        // 2. 将 Word 文档转换为 PDF
        this.wordToPDFWithJACOB();

        //3. 将PDF转为Book
        // 加载 PDF 文档
        File pdfFile = new File("output.pdf");
        PDDocument document = Loader.loadPDF(pdfFile);
        System.out.println("PDF 加载成功，版本: " + document.getVersion());
        // 创建 PDF 渲染器
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // 获取页面格式（默认 A4 大小）
        PageFormat pageFormat = new PageFormat();

        // 遍历 PDF 页面
        for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
            // 渲染页面为 BufferedImage
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300); // 300 DPI

            // 创建 Printable 对象
            Printable printable = new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                    // 缩放图像以适应页面
                    double scaleX = pageFormat.getImageableWidth() / image.getWidth();
                    double scaleY = pageFormat.getImageableHeight() / image.getHeight();
                    double scale = Math.min(scaleX, scaleY);
                    g2d.scale(scale, scale);

                    // 绘制图像
                    g2d.drawImage(image, 0, 0, null);
                    return PAGE_EXISTS;
                }
            };

            // 将页面添加到 Book
            book.append(printable, pageFormat);
        }

        // 关闭 PDF 文档
        document.close();
    }

    private void wordToPDFWithJACOB()
    {
        // 初始化 COM 线程
        com.jacob.com.ComThread.InitSTA();

        try {
            // 创建 Word 应用程序对象
            ActiveXComponent wordApp = new ActiveXComponent("Word.Application");
            wordApp.setProperty("Visible", new Variant(false)); // 设置 Word 不可见
            Dispatch documents = wordApp.getProperty("Documents").toDispatch();

            // 打开 Word 文档
            String absoluteDocxPath = new File("output.docx").getAbsolutePath();
            Dispatch document = Dispatch.call(documents, "Open", new Variant(absoluteDocxPath)).toDispatch();

            // 保存为 PDF（17 表示 PDF 格式）
            String outputPdf = new File("output.pdf").getAbsolutePath();
            Dispatch.call(document, "SaveAs2", new Variant(outputPdf), new Variant(17));

            // 关闭文档
            Dispatch.call(document, "Close");
            Dispatch.call(wordApp, "Quit");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放 COM 线程
            com.jacob.com.ComThread.Release();
        }

        System.out.println("Word 文档已成功转换为 PDF！");
    }

    /**
     * 打印文档
     *
     * @param printQueueList
     * @param book
     */
    private void printBook(List<PrintQueue> printQueueList, Book book)
    {
        //获取打印服务对象
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        // 设置打印类
        printerJob.setPageable(book);

        HashAttributeSet hashAttributeSet = new HashAttributeSet();
        hashAttributeSet.add(new PrinterName(printerName, null));
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, hashAttributeSet);
        if (printServices.length == 0) {
            logger.error("[" + printerName + "]" + "无法找到打印机");
            return;
        }

        try {
            printerJob.setCopies(1);
            printerJob.setPrintService(printServices[0]);
            logger.debug("[" + printerName + "] Print label start------------");
            printerJob.print();
            logger.debug("[" + printerName + "] Print label end------------");
            //成功打印后，更新打印状态， 删除打印队列
            for (PrintQueue printQueue : printQueueList) {
                Label label = printQueue.getLabel();
                label.setPrintTime(new Date());
                label.setPrintStatus(PrintStatus.PRINTED);
                labelService.saveLabelHistory(label);

                printQueueService.deletePrintQueue(printQueue);
            }
        } catch (Exception ex) {
            logger.error("[" + printerName + "]打印Job" + ex.getMessage());
            logger.error("[" + printerName + "]打印Job:", ex);
        }
    }
}
