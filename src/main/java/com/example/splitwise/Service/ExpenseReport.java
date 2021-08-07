package com.example.splitwise.Service;

import com.example.splitwise.Exception.InvalidSplitException;
import com.example.splitwise.Strategy.Methods.PercentageSplit;
import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.List;

public class ExpenseReport {

    private final Integer groupId;
    private final Integer paidBy;
    private final Double totalAmount;
    private final SplitStrategy splitStrategy;

    private final List<Split> splits;

    public ExpenseReport(Integer groupId, Integer paidBy, Double totalAmount,
        SplitStrategy splitStrategy, List<Split> splits) {
        this.groupId = groupId;
        this.paidBy = paidBy;
        this.totalAmount = totalAmount;
        this.splitStrategy = splitStrategy;
        this.splits = splits;
    }

    public void validate() {
        switch (splitStrategy) {
            case FIXED:
                validateFixed();
                break;
            case PERCENT:
                validatePercent();
                break;
        }
    }

    private void validateFixed() {
        double splitTotal = splits.stream().mapToDouble(Split::getAmount).sum();

        if (totalAmount != splitTotal) {
            throw new InvalidSplitException("Total sum of split is not equal to total amount." +
                " Difference: " + Math.abs(splitTotal - totalAmount));
        }
    }

    private void validatePercent() {
        double splitPercentTotal = splits.stream()
            .map(split -> (PercentageSplit)split)
            .mapToDouble(PercentageSplit::getPercent)
            .sum();

        if (splitPercentTotal != 100.00) {
            throw new InvalidSplitException("Total sum of split is not equal to 100%." +
                " Difference: " + Math.abs(100 - splitPercentTotal));
        }
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getPaidBy() {
        return paidBy;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public SplitStrategy getSplitStrategy() {
        return splitStrategy;
    }

    public List<Split> getSplits() {
        return splits;
    }


}
