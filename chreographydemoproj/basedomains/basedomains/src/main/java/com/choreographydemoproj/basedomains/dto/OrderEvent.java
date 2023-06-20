package com.choreographydemoproj.basedomains.dto;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class OrderEvent {


    public OrderEvent() {
        super();
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public String getDeliverStatus() {
        return deliverStatus;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "deliverStatus='" + deliverStatus + '\'' +
                ", eventId='" + eventId + '\'' +
                ", message='" + message + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", order=" + orderDetails +
                '}';
    }

    public OrderEvent(String deliverStatus, String eventId, String message, String orderStatus, String paymentStatus, OrderDetails order) {
        this.deliverStatus = deliverStatus;
        this.eventId = eventId;
        this.message = message;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.orderDetails = order;
    }

    public void setDeliverStatus(String deliverStatus) {
        this.deliverStatus = deliverStatus;
    }

    private String deliverStatus;

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setOrder(OrderDetails order) {
        this.orderDetails = order;
    }

    public String getMessage() {
        return message;
    }



    public OrderDetails getOrder() {
        return orderDetails;
    }

    @Id
    private String eventId;
    private String message;
    private String orderStatus;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    private String paymentStatus;

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    @OneToOne(mappedBy = "orderEvent",cascade = CascadeType.ALL)
    private OrderDetails orderDetails;


}
