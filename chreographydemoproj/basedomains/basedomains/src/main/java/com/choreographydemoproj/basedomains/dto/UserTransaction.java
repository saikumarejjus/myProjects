package com.choreographydemoproj.basedomains.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class UserTransaction {

    @Id
    @GeneratedValue
    private int transactionId;

    @Override
    public String toString() {
        return "UserTransaction{" +
                "transactionId=" + transactionId +
                ", userId=" + userId +
                ", transactionAmount=" + transactionAmount +
                ", orderId='" + orderId + '\'' +
                '}';
    }

    public UserTransaction() {
    }



    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    private int userId;
    private int transactionAmount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public UserTransaction(  int userId, int transactionAmount, String orderId) {

        this.userId = userId;
        this.transactionAmount = transactionAmount;
        this.orderId = orderId;
    }

    private String orderId;
}
