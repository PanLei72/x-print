package com.x.print.domain.model.printqueue;

import com.x.print.domain.model.label.Label;
import io.jmix.core.DataManager;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
@Component
public class PrintQueueService implements IPrintQueueService {
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Sequences sequences;

    @Override
    public PrintQueue createPrintQueue() {
        return dataManager.create(PrintQueue.class);
    }

    @Override
    public PrintQueue savePrintQueue(PrintQueue printQueue) {
        if(printQueue == null)
        {
            return null;
        }
        return dataManager.save(printQueue);
    }

    @Override
    public PrintQueue addLabelToPrintQueue(Label label) {
        String printerName = label.getPrinterName();
        Long sequenceNumber = this.getNextSequenceNumber("PRINT_QUEUE");
        PrintQueue printQueue = this.createPrintQueue();
        printQueue.setPrinterName(printerName);
        printQueue.setSequenceNumber(sequenceNumber);
        printQueue.setLabel(label);

        this.savePrintQueue(printQueue);

        return printQueue;
    }

    private Long getNextSequenceNumber(String sequenceName)
    {
        Long number = sequences.createNextValue(Sequence.withName(sequenceName)
                .setStartValue(100)
                .setIncrement(100));
        return number;
    }

    @Override
    public List<PrintQueue> loadPrintQueueByPrinterName(String printerName) {
        List<PrintQueue> printQueueList =  dataManager.load(PrintQueue.class)
                .query("select e from PrintQueue e where e.printerName=:printerName order by e.sequenceNumber")
                .parameter("printerName", printerName)
                .maxResults(10)
                .list();

        return printQueueList;
    }

    @Override
    public void deletePrintQueue(PrintQueue printQueue) {
        if(printQueue != null)
        {
            dataManager.remove(printQueue);
        }
    }
}
