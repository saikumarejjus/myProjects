package com.choreographydemoproj.basedomains.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class OrderDetails {

    @Id
    private String orderId;

    private int userID;
    private int productId;
    private int productQuant;

    @OneToOne
    @JoinColumn(name = "order_id")
    private OrderEvent orderEvent;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuant() {
        return productQuant;
    }

    public void setProductQuant(int productQuant) {
        this.productQuant = productQuant;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", userID=" + userID +
                ", productId=" + productId +
                ", productQuant=" + productQuant +
                '}';
    }

    public OrderDetails(String orderId, int userID, int productId, int productQuant) {
        this.orderId = orderId;
        this.userID = userID;
        this.productId = productId;
        this.productQuant = productQuant;
    }

    public OrderDetails() {
    }
}
