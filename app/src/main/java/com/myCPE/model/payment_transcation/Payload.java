package com.myCPE.model.payment_transcation;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Payload {

    @SerializedName("is_last")
    private boolean isLast;

    @SerializedName("transaction")
    private List<TransactionItem> transaction;

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setTransaction(List<TransactionItem> transaction) {
        this.transaction = transaction;
    }

    public List<TransactionItem> getTransaction() {
        return transaction;
    }

    @Override
    public String toString() {
        return
                "Payload{" +
                        "is_last = '" + isLast + '\'' +
                        ",transaction = '" + transaction + '\'' +
                        "}";
    }
}