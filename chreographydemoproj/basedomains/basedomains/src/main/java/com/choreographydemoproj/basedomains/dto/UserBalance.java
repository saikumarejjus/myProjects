package com.choreographydemoproj.basedomains.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class UserBalance {

    public UserBalance(int userId, int userBalance) {
        this.userId = userId;
        this.userBalance = userBalance;
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "userId=" + userId +
                ", userBalance=" + userBalance +
                '}';
    }

    public UserBalance() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

    @Id
    @GeneratedValue
    private int userId;
    private int userBalance;
}
