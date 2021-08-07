package com.example.splitwise.Strategy.Methods;

import com.example.splitwise.Model.User;
import com.example.splitwise.Strategy.Split;

public class PercentageSplit extends Split {

    private final Double percent;

    public PercentageSplit(User user, Double amount, double percent) {
        super(user, amount);
        this.percent = percent;
    }

    public Double getPercent() {
        return percent;
    }
}
