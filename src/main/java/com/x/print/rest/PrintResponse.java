package com.x.print.rest;

import java.io.Serializable;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-29
 */
public class PrintResponse implements Serializable {

    private boolean success;

    private String errorMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
