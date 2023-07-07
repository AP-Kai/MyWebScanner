package com.dhee.entity;

public class R {
    private boolean success;
    private String message;
    public R() {
    }

    public R(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
