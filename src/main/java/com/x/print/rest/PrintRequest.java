package com.x.print.rest;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-29
 */
public class PrintRequest {

    private String labelIdentity;
    private String labelName;

    private String printerName;

    private String labelData;

    private Integer labelQuantity;

    /**
     * 类型，用于区分模板类型，LABEL：java模板，POITL:word模板
     */
    private String category;

    public String getLabelIdentity() {
        return labelIdentity;
    }

    public void setLabelIdentity(String labelIdentity) {
        this.labelIdentity = labelIdentity;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getLabelData() {
        return labelData;
    }

    public void setLabelData(String labelData) {
        this.labelData = labelData;
    }

    public Integer getLabelQuantity() {
        return labelQuantity;
    }

    public void setLabelQuantity(Integer labelQuantity) {
        this.labelQuantity = labelQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
