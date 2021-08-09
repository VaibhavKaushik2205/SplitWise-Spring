package com.example.splitwise.ModelTest;

import com.example.splitwise.Exception.InvalidSplitException;
import com.example.splitwise.Model.ExpenseReport;
import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import com.example.splitwise.Strategy.Methods.EqualSplit;
import com.example.splitwise.Strategy.Methods.FixedSplit;
import com.example.splitwise.Strategy.Methods.PercentageSplit;
import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ExpenseReportTest {

    @Test
    public void expectsToCreateAnExpenseReportOfBill(){
        User user = Mockito.mock(User.class);
        Splitwise group = Mockito.mock(Splitwise.class);

        Mockito.when(user.getId()).thenReturn(1);
        Mockito.when(group.getId()).thenReturn(1);

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new EqualSplit(user, 50.));
        splits.add(new EqualSplit(user, 50.));

        ExpenseReport report = new ExpenseReport(group.getId(), user.getId(), amount,
            SplitStrategy.EQUAL, splits);
    }

    @Test
    public void expectsToThrowExceptionWhenPaymentExceedsBill(){
        User user = Mockito.mock(User.class);
        Splitwise group = Mockito.mock(Splitwise.class);

        Mockito.when(user.getId()).thenReturn(1);
        Mockito.when(group.getId()).thenReturn(1);

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(user, 60.));
        splits.add(new FixedSplit(user, 50.));

        ExpenseReport report = new ExpenseReport(group.getId(), user.getId(), amount,
            SplitStrategy.FIXED, splits);

        try{
            report.validate();
        }
        catch (InvalidSplitException ex){
            Assertions.assertEquals("Total sum of split is not equal to total amount. Difference:"
                + " 10.0", ex.getMessage());
        }
    }

    @Test
    public void expectsToThrowExceptionWhenPaymentPercentageExceeds100(){
        User user = Mockito.mock(User.class);
        Splitwise group = Mockito.mock(Splitwise.class);

        Mockito.when(user.getId()).thenReturn(1);
        Mockito.when(group.getId()).thenReturn(1);

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new PercentageSplit(user, 60., 60));
        splits.add(new PercentageSplit(user, 50., 50));

        ExpenseReport report = new ExpenseReport(group.getId(), user.getId(), amount,
            SplitStrategy.PERCENT, splits);

        try{
            report.validate();
        }
        catch (InvalidSplitException ex){
            Assertions.assertEquals("Total sum of split is not equal to 100%. Difference: 10.0",
                ex.getMessage());
        }
    }
}
