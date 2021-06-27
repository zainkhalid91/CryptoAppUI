package com.wallet.yukoni.models;

public class NewPinCodeResponse {

    boolean status;
    String msg;
    String data;

    public NewPinCodeResponse(boolean status, String msg, String data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
