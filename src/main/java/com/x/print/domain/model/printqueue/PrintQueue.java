package com.x.print.domain.model.printqueue;

import com.x.print.domain.model.NamedEntity;
import com.x.print.domain.model.label.Label;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "PRINT_QUEUE")
@Entity
public class PrintQueue extends NamedEntity {

    @Column(name = "PRINTER_NAME")
    @NotNull
    private String printerName;


    @Column(name = "SEQUENCE_NUMBER")
    @NotNull
    private Long sequenceNumber;

    @JoinColumn(name = "LABEL_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Label label;

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }
}