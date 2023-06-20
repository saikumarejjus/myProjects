package com.choreographydemoproj.deliveryservice.entity;

public class DelivetyDto
{
    private String eventId;
    private Boolean orderStatus;

    public String geteventId() {
        return eventId;
    }

    public void seteventId(String eventId) {
        this.eventId = eventId;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "DelivetyDto{" +
                "eventId='" + eventId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }

    public DelivetyDto(String eventId, Boolean orderStatus) {
        this.eventId = eventId;
        this.orderStatus = orderStatus;
    }

    public DelivetyDto() {
    }
}
