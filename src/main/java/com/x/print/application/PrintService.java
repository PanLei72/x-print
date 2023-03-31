package com.x.print.application;

import com.x.print.infrastructure.utility.BeanManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class PrintService implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(PrintService.class);
    private boolean refreshPrintWorker = true;

    private Integer refreshPrintWorkerInterval = 10000;

    @Override
    public void run(ApplicationArguments args) {
        this.startService();
    }

    public void stopService() {
        refreshPrintWorker = false;
    }


    public void startService() {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Hashtable<String, PrintWorker> printWorkerHashtable = new Hashtable<>();
                            while (refreshPrintWorker) {
                                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                                DocFlavor flavor = DocFlavor.BYTE_ARRAY.PNG;
                                javax.print.PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, printRequestAttributeSet);

                                //添加新打印机
                                List<String> printNameList = new ArrayList<>();
                                for (int i = 0; i < printService.length; i++) {
                                    String printerName = printService[i].getName();
                                    printNameList.add(printerName);

                                    //不存在这个打印机，启动监听打印
                                    if (!printWorkerHashtable.containsKey(printerName)) {
                                        try {
                                            PrintWorker printWorker = this.startListenPrint(printerName);
                                            printWorkerHashtable.put(printerName, printWorker);
                                        } catch (Exception e) {
                                            logger.error("[" + printerName + "]异常" + e.getMessage());
                                            logger.error("[" + printerName + "]异常", e);
                                        }
                                        Thread.sleep(500);
                                    }
                                }
                                //消除不用的打印机
                                List<String> removePrinter = new ArrayList<>();
                                Enumeration<String> _keys = printWorkerHashtable.keys();
                                while (_keys.hasMoreElements()) {
                                    String _item = _keys.nextElement();
                                    if (!printNameList.contains(_item)) {
                                        removePrinter.add(_item);
                                    }
                                }
                                for (String _item : removePrinter) {
                                    PrintWorker _removeThread = printWorkerHashtable.get(_item);
                                    if (removePrinter != null) {
                                        _removeThread.setWorkerStatus(false);
                                        printWorkerHashtable.remove(_item);
                                    }
                                }
                                Thread.sleep(refreshPrintWorkerInterval);
                            }
                            logger.warn("[系统消息]打印服务停止");
                            if (!refreshPrintWorker) {
                                return;
                            }
                        } catch (Exception e) {
                            logger.error("[系统异常]轮询打印机，" + e.getMessage());
                            logger.error("[系统异常]轮询打印机，", e);
                            if (!refreshPrintWorker) {
                                return;
                            } else {
                                try {
                                    Thread.sleep(50000);
                                } catch (InterruptedException e1) {
                                    logger.error("系统异常，", e);
                                }
                            }
                        }
                    }
                }

                public PrintWorker startListenPrint(String printerName) {
                    PrintWorker printWorker = BeanManager.getBean(PrintWorker.class);

                    printWorker.setPrinterName(printerName);

                    Thread thread = new Thread(printWorker);
                    thread.setName("Thread-" + printerName);
                    // 将服务线程设定为用户线程
                    thread.setDaemon(false);
                    thread.start();
                    logger.warn("[" + printerName + "]成功启动线程");

                    return printWorker;
                }
            });
            thread.setDaemon(false);
            thread.start();
        } catch (Exception e) {
            logger.error("[系统异常]" + e.getMessage());
            logger.error("[系统异常]", e);
        }
    }

}
