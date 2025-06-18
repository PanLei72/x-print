package com.x.print.application;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.deepoove.poi.XWPFTemplate;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.x.print.domain.model.label.ILabelService;
import com.x.print.domain.model.label.Label;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;
import com.x.print.domain.model.labeldatadefinition.LabelVariable;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    if (printQueueList != null && !printQueueList.isEmpty()) {
                        for (PrintQueue printQueue : printQueueList) {
                            Label label = printQueue.getLabel();
                            LabelCategory labelCategory = label.getCategory(); //标签模板类型

                            if (LabelCategory.LABEL.equals(labelCategory)) {
                                this.printLabel(printQueue);
                            } else if (LabelCategory.POITL.equals(labelCategory)) {
                                this.printPOITL(printQueue);
                            } else {
                                //默认为Java模板标签
                                this.printLabel(printQueue);
                            }
                        }

                    }
                } catch (Exception e) {
                    logger.error("[{}]循环打印异常:", printerName, e);
                }
            }
            logger.warn("[{}]监听线程关闭", printerName);
        } catch (Exception e) {
            logger.error("[{}]Run运行异常:", printerName, e);
        }
    }


    /**
     * 打印标签文档
     */
    private void printLabel(PrintQueue printQueue) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        //开始打印
        //通俗理解就是书、文档
        Book book = new Book();
        Label label = printQueue.getLabel();

        String labelName = label.getLabelName();//标签名称
        int labelQuantity = label.getLabelQuantity();//标签数量

        LabelDesign labelDesign = labelDesignService.loadLabelDesignByLabelName(labelName);
        if (labelDesign == null) {
            logger.error("[{}]标签【{}】不存在", printerName, labelName);
            throw new RuntimeException("[" + printerName + "]标签【" + labelName + "】不存在");
        }

        String className = labelDesign.getClassName();

        int labelWidth = labelDesign.getWidth().intValue();
        int labelHeight = labelDesign.getHeight().intValue();

        logger.info("[{}]getOrientation：{}", printerName, labelDesign.getOrientation());

        PageFormat pageFormat = this.getPageFormat(labelDesign, labelWidth, labelHeight);

        Class<?> cls = Class.forName(className);

        Object labelObject = cls.newInstance();//初始化一个实例
        LabelPrintable labelPrintableInstance = (LabelPrintable) labelObject;
        labelPrintableInstance.setPrinterName(printerName);

        LabelDataDefinition labelDataDefinition = labelDesign.getLabelDataDefinition();

        JSONObject labelDataJson = new JSONObject();
        if (labelDataDefinition != null) {
            JSONObject labelDefinitionData = this.coverteLabelDataDefinitionToJSONObject(labelDataDefinition);

            labelDataJson.putAll(labelDefinitionData);
        }

        String labelDataStr = label.getLabelData();
        if (StringUtils.isNotBlank(labelDataStr)) {
            JSONObject labelData = JSONObject.parseObject(labelDataStr);
            labelDataJson.putAll(labelData);
        }

        for (int i = 0; i < labelQuantity; i++) {
            labelPrintableInstance.setLabelData(labelDataJson);
            book.append(labelPrintableInstance, pageFormat);
        }

        if (book.getNumberOfPages() == 0) {
            return;
        }
        this.printBook(printQueue, book, labelQuantity);
    }


    /**
     * 打印POITL标签文档
     *
     * @param printQueue
     */
    private void printPOITL(PrintQueue printQueue) throws IOException {
        Label label = printQueue.getLabel();

        String labelName = label.getLabelName();//标签名称
        int labelQuantity = label.getLabelQuantity();//标签数量
        PoitlTemplate poitlTemplate = poitlTemplateService.loadPoitlTemplateByTemplateName(labelName);
        if (poitlTemplate == null) {
            logger.error("[{}]POITL模板【{}】不存在", printerName, labelName);
            throw new RuntimeException("[" + printerName + "]POITL模板【" + labelName + "】不存在");
        }

        FileRef poitlTemplateFile = poitlTemplate.getTemplateFile();
        String fileName = poitlTemplateFile.getFileName();
        // 获取文件存储
        FileStorage fileStorage = fileStorageLocator.getDefault();

        InputStream inputStream = fileStorage.openStream(poitlTemplateFile);

        // 1. 使用 POITL 生成 Word 文档
        Map<String, Object> data = new HashMap<>();

        LabelDataDefinition labelDataDefinition = poitlTemplate.getLabelDataDefinition();
        if (labelDataDefinition != null) {
            // 将标签数据定义转换为 Map
            Map<String, String> labelDefinitionData = this.coverteLabelDataDefinitionToMap(labelDataDefinition);
            data.putAll(labelDefinitionData);
        }

        String labelDataStr = label.getLabelData();
        if (StringUtils.isNotBlank(labelDataStr)) {
            Map<String, String> params = JSONObject.parseObject(labelDataStr, new TypeReference<Map<String, String>>() {
            });
            data.putAll(params);
        }

        XWPFTemplate template = XWPFTemplate.compile(inputStream).render(data);
        try (FileOutputStream out = new FileOutputStream(fileName + "-output" + ".docx")) {
            template.write(out);
        }

        // 2. 将 Word 文档转换为 PDF
        this.wordToPDFWithJACOB(fileName);

        //3. 将PDF转为Book
        // 加载 PDF 文档
        File pdfFile = new File(fileName + "-output" + ".pdf");
        PDDocument document = Loader.loadPDF(pdfFile);

        // 创建 PDF 渲染器
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // 获取页面格式（默认 A4 大小）
        PageFormat pageFormat = new PageFormat();

        //开始打印
        //通俗理解就是书、文档
        Book book = new Book();

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
        if (book.getNumberOfPages() == 0) {
            return;
        }
        this.printBook(printQueue, book, labelQuantity);
    }

    /**
     * 打印文档
     *
     * @param printQueue 打印队列
     * @param book
     */
    private void printBook(PrintQueue printQueue, Book book, int copies) {
        //获取打印服务对象
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        // 设置打印类
        printerJob.setPageable(book);

        HashAttributeSet hashAttributeSet = new HashAttributeSet();
        hashAttributeSet.add(new PrinterName(printerName, null));
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, hashAttributeSet);
        if (printServices.length == 0) {
            logger.error("[{}]无法找到打印机", printerName);
            return;
        }

        try {
            printerJob.setCopies(copies);
            printerJob.setPrintService(printServices[0]);
            logger.debug("[{}] Print label start------------", printerName);
            printerJob.print();
            logger.debug("[{}] Print label end------------", printerName);

            //成功打印后，更新打印状态， 删除打印队列
            Label label = printQueue.getLabel();
            label.setPrintTime(new Date());
            label.setPrintStatus(PrintStatus.PRINTED);
            labelService.saveLabelHistory(label);

            printQueueService.deletePrintQueue(printQueue);
        } catch (Exception ex) {
            logger.error("[{}]打印Job{}", printerName, ex.getMessage());
            logger.error("[{}]打印Job:", printerName, ex);
        }
    }

    /**
     * 将标签数据定义转换为Map
     *
     * @param labelDataDefinition
     * @return
     */
    private Map<String, String> coverteLabelDataDefinitionToMap(LabelDataDefinition labelDataDefinition) {
        Map<String, String> labelDefinitionData = new HashMap<>();
        if (labelDataDefinition != null && labelDataDefinition.getLabelVariables() != null) {
            for (LabelVariable entry : labelDataDefinition.getLabelVariables()) {
                String key = entry.getLabelVariableName();
                String value = entry.getDefaultValue();
                labelDefinitionData.put(key, value);
            }
        }
        return labelDefinitionData;
    }


    /**
     * Word转PDF文档
     *
     * @param fileName
     * @throws IOException
     */
    private void wordToPDFWithJACOB(String fileName) throws IOException {
        // 初始化 COM 线程
        com.jacob.com.ComThread.InitSTA();

        try {
            // 创建 Word 应用程序对象
            ActiveXComponent wordApp = new ActiveXComponent("Word.Application");
            wordApp.setProperty("Visible", new Variant(false)); // 设置 Word 不可见
            Dispatch documents = wordApp.getProperty("Documents").toDispatch();

            // 打开 Word 文档
            String absoluteDocxPath = new File(fileName + "-output" + ".docx").getAbsolutePath();
            Dispatch document = Dispatch.call(documents, "Open", new Variant(absoluteDocxPath)).toDispatch();

            // 保存为 PDF（17 表示 PDF 格式）
            String outputPdf = new File(fileName + "-output" + ".pdf").getAbsolutePath();
            Dispatch.call(document, "SaveAs2", new Variant(outputPdf), new Variant(17));

            // 关闭文档
            Dispatch.call(document, "Close");
            Dispatch.call(wordApp, "Quit");

        } catch (Exception e) {
            logger.error("Word转PDF异常", e);
        } finally {
            // 释放 COM 线程
            com.jacob.com.ComThread.Release();
        }

        logger.info("Word 文档[{}]转PDF成功！", fileName);
    }

    /**
     * 获取页面格式
     *
     * @param labelDesign
     * @param labelWidth
     * @param labelHeight
     * @return
     */
    private PageFormat getPageFormat(LabelDesign labelDesign, int labelWidth, int labelHeight) {
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
        return pageFormat;
    }

    /**
     * 将标签数据定义转换为JSON对象
     *
     * @param labelDataDefinition
     * @return
     */
    private JSONObject coverteLabelDataDefinitionToJSONObject(LabelDataDefinition labelDataDefinition) {
        JSONObject labelDefinitionData = new JSONObject();
        if (labelDataDefinition != null && labelDataDefinition.getLabelVariables() != null) {
            for (LabelVariable entry : labelDataDefinition.getLabelVariables()) {
                String key = entry.getLabelVariableName();
                String value = entry.getDefaultValue();
                labelDefinitionData.put(key, value);
            }
        }
        return labelDefinitionData;
    }

}
