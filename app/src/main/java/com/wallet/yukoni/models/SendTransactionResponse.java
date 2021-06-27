package com.wallet.yukoni.models;

public class SendTransactionResponse {
    String symbol, to;
    Double amount;

    public SendTransactionResponse(String symbol, String to, Double amount) {
        this.symbol = symbol;
        this.to = to;
        this.amount = amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
