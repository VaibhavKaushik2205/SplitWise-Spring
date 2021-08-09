package com.example.splitwise.ModelTest;

import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import com.example.splitwise.Strategy.Methods.EqualSplit;
import com.example.splitwise.Strategy.Methods.FixedSplit;
import com.example.splitwise.Strategy.Methods.PercentageSplit;
import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SplitwiseTest {

    @Test
    public void expectsToCreateANewSplitwiseGroup(){
        User user1 = Mockito.mock(User.class);
        User user2 = Mockito.mock(User.class);

        Set<User> members = new HashSet<>();
        members.add(user1);
        members.add(user2);

        Splitwise group = new Splitwise("Group1", members);
        Assertions.assertEquals(members, group.getMembers());
    }

    @Test
    public void expectsAUserToPayBillWithEqualShare(){
        User john = new User("John", "99999999999", "john@123.com", 1);
        User jack = new User("Jack", "99999999998", "jack@123.com", 2);

        Set<User> members = new HashSet<>();
        members.add(john);
        members.add(jack);
        Splitwise group = new Splitwise("Group1", members, 1);

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new EqualSplit(john, 50.));
        splits.add(new EqualSplit(jack, 50.));

        group.pay(john, amount, SplitStrategy.EQUAL, splits);

        Assertions.assertEquals(1, john.getUserOwedTo().size());
        Assertions.assertEquals(1, jack.getUserOwedTo().size());
    }

    @Test
    public void expectsAUserToPayBillWithFixedAmounts(){
        User john = new User("John", "99999999999", "john@123.com", 1);
        User jack = new User("Jack", "99999999998", "jack@123.com", 2);

        Set<User> members = new HashSet<>();
        members.add(john);
        members.add(jack);
        Splitwise group = new Splitwise("Group1", members, 1);

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(john, 60.));
        splits.add(new FixedSplit(jack, 40.));

        group.pay(john, amount, SplitStrategy.FIXED, splits);

        Assertions.assertEquals(1, john.getUserOwedTo().size());
        Assertions.assertEquals(1, jack.getUserOwedTo().size());
    }

    @Test
    public void expectsAUserToPayBillWithGivenPercentage(){
        User john = new User("John", "99999999999", "john@123.com", 1);
        User jack = new User("Jack", "99999999998", "jack@123.com", 2);

        Set<User> members = new HashSet<>();
        members.add(john);
        members.add(jack);
        Splitwise group = new Splitwise("Group1", members, 1);

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new PercentageSplit(john, 60., 60));
        splits.add(new PercentageSplit(jack, 40., 40));

        group.pay(john, amount, SplitStrategy.PERCENT, splits);

        Assertions.assertEquals(1, john.getUserOwedTo().size());
        Assertions.assertEquals(1, jack.getUserOwedTo().size());
    }

}
