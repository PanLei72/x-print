package com.x.print.application;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.domain.model.label.ILabelService;
import com.x.print.domain.model.label.Label;
import com.x.print.domain.model.labeldesign.ILabelDesignService;
import com.x.print.domain.model.labeldesign.LabelDesign;
import com.x.print.domain.model.printqueue.IPrintQueueService;
import com.x.print.domain.model.printqueue.PrintQueue;
import com.x.print.infrastructure.constants.Orientation;
import com.x.print.infrastructure.constants.PrintStatus;
import io.jmix.core.security.Authenticated;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
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
