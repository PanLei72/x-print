package com.x.print.domain.model.label;

import com.x.print.domain.model.NamedEntity;
import com.x.print.infrastructure.constants.LabelCategory;
import com.x.print.infrastructure.constants.PrintStatus;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JmixEntity
@Table(name = "LABEL")
@Entity
public class Label extends NamedEntity {

    @Column(name = "LABEL_IDENTITY")
    @NotNull
    private String labelIdentity;

    @Column(name = "PRINTER_NAME")
    @NotNull
    private String printerName;

    @Column(name = "LABEL_NAME")
    @NotNull
    private String labelName;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "LABEL_DATA")
    @NotNull
    private String labelData;

    @Column(name = "LABEL_QUANTITY")
    @NotNull
    private Integer labelQuantity;

    @Column(name = "PRINT_STATUS")
    @NotNull
    private String printStatus;

    @Column(name = "PRINT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date printTime;

    public String getLabelIdentity() {
        return labelIdentity;
    }

    public void setLabelIdentity(String labelIdentity) {
        this.labelIdentity = labelIdentity;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public LabelCategory getCategory() {
        return category != null ? LabelCategory.fromId(category) : null;
    }

    public void setCategory(LabelCategory category) {
        this.category = category == null ? null : category.getId();
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

    public PrintStatus getPrintStatus() {
        return printStatus != null ? PrintStatus.fromId(printStatus) : null;
    }

    public void setPrintStatus(PrintStatus printStatus) {
        this.printStatus = printStatus == null ? null : printStatus.getId();
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }
}