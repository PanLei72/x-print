package com.x.print.domain.model.printer;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Entity;

@JmixEntity
public class Printer {

    @InstanceName
    private String printerName;

    private String printerState;

    private String queuedJobCount;

    private String printerIsAcceptingJobs;

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getPrinterState() {
        return printerState;
    }

    public void setPrinterState(String printerState) {
        this.printerState = printerState;
    }

    public String getQueuedJobCount() {
        return queuedJobCount;
    }

    public void setQueuedJobCount(String queuedJobCount) {
        this.queuedJobCount = queuedJobCount;
    }

    public String getPrinterIsAcceptingJobs() {
        return printerIsAcceptingJobs;
    }

    public void setPrinterIsAcceptingJobs(String printerIsAcceptingJobs) {
        this.printerIsAcceptingJobs = printerIsAcceptingJobs;
    }
}