package com.x.print.rest;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.domain.model.label.ILabelService;
import com.x.print.domain.model.label.Label;
import com.x.print.domain.model.printqueue.IPrintQueueService;
import com.x.print.infrastructure.constants.LabelCategory;
import com.x.print.infrastructure.constants.PrintStatus;
import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-29
 */
@Service("print_PrintService")
public class PrintService {

    private static final Logger logger = LogManager.getLogger(PrintService.class);
    @Autowired
    private IPrintQueueService printQueueService;
    @Autowired
    private ILabelService labelService;

    public PrintResponse printLabel(PrintRequest printRequest) {
        PrintResponse printResponse = new PrintResponse();
        printResponse.setSuccess(false);

        if (printRequest == null) {
            printResponse.setErrorMessage("参数不能为空");
            return printResponse;
        }
        String labelIdentity = printRequest.getLabelIdentity();
        if (StringUtils.isEmpty(labelIdentity)) {
            printResponse.setErrorMessage("标签标识[labelIdentity]不能为空");
            return printResponse;
        }
        String labelName = printRequest.getLabelName();
        if (StringUtils.isEmpty(labelName)) {
            printResponse.setErrorMessage("标签名称[labelName]不能为空");
            return printResponse;
        }
        String printerName = printRequest.getPrinterName();
        if (StringUtils.isEmpty(printerName)) {
            printResponse.setErrorMessage("打印机[printerName]不能为空");
            return printResponse;
        }
        String category = printRequest.getCategory();
        LabelCategory labelCategory = null;
        if(!StringUtils.isEmpty(category)){
            labelCategory = LabelCategory.valueOf(category);
        }

        String labelData = printRequest.getLabelData();
        try {
            JSONObject.parseObject(labelData);
        } catch (Exception e)
        {
            logger.error("labelData=" + labelData);
            logger.error("解析标签数据失败");

            printResponse.setErrorMessage("解析标签数据失败，标签数据应为JSON格式");
            return printResponse;
        }

        //保存标签历史, 打印成功后更新状态
        Label label = labelService.createLabelHistory();
        label.setLabelIdentity(labelIdentity);
        label.setPrinterName(printerName);
        label.setCategory(labelCategory);
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
