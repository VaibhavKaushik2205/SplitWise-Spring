package com.example.splitwise.Strategy.Methods;

import com.example.splitwise.Model.User;
import com.example.splitwise.Strategy.Split;

public class EqualSplit extends Split {

    public EqualSplit(User user, Double amount) {
        super(user, amount);
    }
}
