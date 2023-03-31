package com.x.print.rest;

import com.x.print.domain.model.label.ILabelService;
import com.x.print.domain.model.label.Label;
import com.x.print.domain.model.printqueue.IPrintQueueService;
import com.x.print.domain.model.printqueue.PrintQueue;
import com.x.print.infrastructure.constants.PrintStatus;
import io.jmix.data.Sequence;
import io.jmix.data.Sequences;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-29
 */
@Service("print_PrintService")
public class PrintService {
    @Autowired
    private IPrintQueueService printQueueService;
    @Autowired
    private ILabelService labelService;

    public PrintResponse printLabel(PrintRequest printRequest) {
        PrintResponse printResponse = new PrintResponse();

        if (printRequest == null) {
            printResponse.setSuccess(false);
            printResponse.setErrorMessage("参数不能为空");
            return printResponse;
        }
        String labelIdentity = printRequest.getLabelIdentity();
        if (StringUtils.isEmpty(labelIdentity)) {
            printResponse.setSuccess(false);
            printResponse.setErrorMessage("labelIdentity不能为空");
            return printResponse;
        }

        String printerName = printRequest.getPrinterName();

        //保存标签历史, 打印成功后更新状态
        Label label = labelService.createLabelHistory();
        label.setLabelIdentity(labelIdentity);
        label.setPrinterName(printerName);
        label.setLabelName(printRequest.getLabelName());
        label.setLabelData(printRequest.getLabelData());
        label.setLabelQuantity(printRequest.getLabelQuantity());
        label.setPrintStatus(PrintStatus.UNPRINTED);
        labelService.saveLabelHistory(label);

        //保存打印队列，打印成功后删除
        printQueueService.addLabelToPrintQueue(label);

        printResponse.setSuccess(true);
        return printResponse;
    }


}
