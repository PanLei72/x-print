package com.x.print.application;

import com.alibaba.fastjson2.JSONObject;
import com.x.print.domain.model.labeldatadefinition.LabelDataDefinition;

import java.awt.print.Printable;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-28
 */
public abstract class LabelPrintable implements Printable {

    private String printerName;

    private JSONObject labelData;

    private JSONObject labelDefinitionData;

    public JSONObject getLabelDefinitionData() {
        return labelDefinitionData;
    }

    public void setLabelDefinitionData(JSONObject labelDefinitionData) {
        this.labelDefinitionData = labelDefinitionData;
    }

    public JSONObject getLabelData() {
        return labelData;
    }

    public void setLabelData(JSONObject labelData) {
        this.labelData = labelData;
    }

    public String getPrinterName() {
        return this.printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    /**
     * 获取标签的数据，为通过接口输入的值
     * @param labelVariable
     * @return
     */
    public String getLabelDataValue(String labelVariable)
    {
        if(labelData == null) {
            return labelVariable;
        }
        if(labelData.containsKey(labelVariable))
        {
            return labelData.getString(labelVariable) != null ? labelData.getString(labelVariable) : labelVariable;
        }
        return labelVariable;
    }

    /**
     * 获取标签定义的值，主要为固定值
     * @param labelVariable
     * @return
     */
    public String getLabelDefinitionDataValue(String labelVariable)
    {
        if(labelDefinitionData == null) {
            return labelVariable;
        }
        if(labelDefinitionData.containsKey(labelVariable))
        {
            return labelDefinitionData.getString(labelVariable) != null ? labelDefinitionData.getString(labelVariable) : labelVariable;
        }

        return labelVariable;
    }
}
