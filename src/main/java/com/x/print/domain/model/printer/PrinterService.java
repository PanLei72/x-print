package com.x.print.domain.model.printer;

import org.springframework.stereotype.Component;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterState;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class PrinterService implements IPrinterService {

    @Override
    public List<Printer> lookupPrint() {
        List<Printer> printerList = new ArrayList<>();
        HashPrintRequestAttributeSet requestAttributeSet = new HashPrintRequestAttributeSet();
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        //查找所有的可用的打印服务
        PrintService[] printService = PrintServiceLookup.lookupPrintServices(flavor, requestAttributeSet);

        if (printService == null || printService.length == 0) {
            return new ArrayList<>();
        }
        for (PrintService print : printService) {
            Printer printer = new Printer();

            printer.setPrinterName(print.getName());

            PrintServiceAttributeSet printServiceAttributeSet = print.getAttributes();
            Attribute printerStateAttribute = print.getAttributes().get(PrinterState.class);

            printer.setPrinterState(printerStateAttribute != null ? printerStateAttribute.toString() : null);

            Attribute[] attributes = printServiceAttributeSet.toArray();
            for (Attribute attribute : attributes) {
                String attrName = attribute.getName();
                String attrValue = attribute.toString();
                if ("queued-job-count".equals(attrName)) {
                    printer.setQueuedJobCount(attrValue);
                } else if ("printer-is-accepting-jobs".equals(attrName)) {
                    printer.setPrinterIsAcceptingJobs(attrValue);
                }
            }

            printerList.add(printer);
        }
        return printerList;
    }

}
