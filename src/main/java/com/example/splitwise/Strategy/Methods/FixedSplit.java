package com.example.splitwise.Strategy.Methods;

import com.example.splitwise.Model.User;
import com.example.splitwise.Strategy.Split;

public class FixedSplit extends Split {

    public FixedSplit(User user, Double amount) {
        super(user, amount);
    }

}
