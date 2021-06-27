package com.wallet.yukoni.models;

public class BuyTokenResponse {
    private Boolean status;
    private String msg;
    private String remaining;


    public BuyTokenResponse(Boolean status, String msg, String remaining) {
        this.status = status;

        this.msg = msg;
        this.remaining = remaining;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }
}
