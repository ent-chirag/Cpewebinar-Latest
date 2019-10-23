package com.entigrity.model.payment_transcation;

import com.google.gson.annotations.SerializedName;


public class TransactionItem {

    @SerializedName("transaction_id")
    private String transactionId;

    @SerializedName("amount")
    private String amount;

    @SerializedName("webinar_id")
    private int webinarId;

    @SerializedName("receipt")
    private String receipt;

    @SerializedName("webinar_type")
    private String webinarType;

    @SerializedName("title")
    private String title;

    @SerializedName("payment_date")
    private String paymentDate;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setWebinarId(int webinarId) {
        this.webinarId = webinarId;
    }

    public int getWebinarId() {
        return webinarId;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setWebinarType(String webinarType) {
        this.webinarType = webinarType;
    }

    public String getWebinarType() {
        return webinarType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    @Override
    public String toString() {
        return
                "TransactionItem{" +
                        "transaction_id = '" + transactionId + '\'' +
                        ",amount = '" + amount + '\'' +
                        ",webinar_id = '" + webinarId + '\'' +
                        ",receipt = '" + receipt + '\'' +
                        ",webinar_type = '" + webinarType + '\'' +
                        ",title = '" + title + '\'' +
                        ",payment_date = '" + paymentDate + '\'' +
                        "}";
    }
}