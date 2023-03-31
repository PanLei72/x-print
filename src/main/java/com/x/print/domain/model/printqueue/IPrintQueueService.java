package com.x.print.domain.model.printqueue;

import com.x.print.domain.model.label.Label;
import com.x.print.domain.model.printer.Printer;

import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
public interface IPrintQueueService {


    PrintQueue createPrintQueue();

    PrintQueue savePrintQueue(PrintQueue printQueue);

    PrintQueue addLabelToPrintQueue(Label label);

    List<PrintQueue> loadPrintQueueByPrinterName(String printerName);

    void deletePrintQueue(PrintQueue printQueue);
}
