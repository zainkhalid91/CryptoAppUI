package com.wallet.yukoni.models;

public class BuyTokenRequest {
    private Float amount;
    private String to;
    private String symbol;


    public BuyTokenRequest(Float amount, String to, String symbol) {
        this.amount = amount;
        this.to = to;
        this.symbol = symbol;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
