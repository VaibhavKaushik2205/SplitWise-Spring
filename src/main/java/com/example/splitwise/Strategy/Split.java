package com.example.splitwise.Strategy;

import com.example.splitwise.Model.User;

public abstract class Split {

    private User user;
    private Double amount;

    public Split() {
    }

    public Split(User user, Double amount) {
        this.user = user;
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public Double getAmount() {
        return amount;
    }

}
