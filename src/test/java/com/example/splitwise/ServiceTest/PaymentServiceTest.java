package com.example.splitwise.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import com.example.splitwise.Service.PaymentService;
import com.example.splitwise.Strategy.Methods.FixedSplit;
import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    PaymentService service;

    @Test
    public void expectsToSaveUserInDatabase() {
        User jack = service.saveUser(new User("Jack", "7777777777", "jack@123.com"));

        Optional<User> savedUser = service.findUser(jack.getId());
        assertTrue(savedUser.isPresent());
    }

    @Test
    public void expectsToCreateASplitWiseGroupInDatabase() {
        User jack = service.saveUser(new User("Jack", "7777777777", "jack@123.com"));
        User jill = service.saveUser(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);

        Splitwise group = service.saveGroup(new Splitwise("Group", members));
        Optional<Splitwise> savedGroup = service.findGroup(1);

        assertTrue(savedGroup.isPresent());
        assertEquals(2, savedGroup.get().getMembers().size());
        assertEquals(1, jack.getGroups().size());
    }

    @Test
    public void expectsAUserToPayBillWithEqualShare() {
        User jack = service.saveUser(new User("Jack", "7777777777", "jack@123.com"));
        User jill = service.saveUser(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);
        Splitwise group = service.saveGroup(new Splitwise("Group", members));

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(jill, 60.));
        splits.add(new FixedSplit(jack, 40.));

        service.pay(group, jill, amount, SplitStrategy.FIXED, splits);

        Optional<User> userJack = service.findUser(1);
        Optional<User> userJill = service.findUser(2);
        assertEquals(-40.0, userJack.get().getUserOwedTo().get(userJill.get().getId()));
        assertEquals(40.0, userJill.get().getUserOwedTo().get(userJack.get().getId()));
    }

    @Test
    public void expectsAUserToPayBillWithFixedAmounts() {
        User jack = service.saveUser(new User("Jack", "7777777777", "jack@123.com"));
        User jill = service.saveUser(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);
        Splitwise group = service.saveGroup(new Splitwise("Group", members));

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(jill, 60.));
        splits.add(new FixedSplit(jack, 40.));

        service.pay(group, jill, amount, SplitStrategy.FIXED, splits);

        Optional<User> userJack = service.findUser(1);
        Optional<User> userJill = service.findUser(2);
        assertEquals(-40.0, userJack.get().getUserOwedTo().get(userJill.get().getId()));
        assertEquals(40.0, userJill.get().getUserOwedTo().get(userJack.get().getId()));
    }

    @Test
    public void expectsAUserToPayBillWithGivenPercentage() {
        User jack = service.saveUser(new User("Jack", "7777777777", "jack@123.com"));
        User jill = service.saveUser(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);
        Splitwise group = service.saveGroup(new Splitwise("Group", members));

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(jill, 60.));
        splits.add(new FixedSplit(jack, 40.));

        service.pay(group, jill, amount, SplitStrategy.FIXED, splits);

        Optional<User> userJack = service.findUser(1);
        Optional<User> userJill = service.findUser(2);
        assertEquals(-40.0, userJack.get().getUserOwedTo().get(userJill.get().getId()));
        assertEquals(40.0, userJill.get().getUserOwedTo().get(userJack.get().getId()));
    }

    @Test
    public void expectsToSettleBetweenUsers() {
        User jack = service.saveUser(new User("Jack", "7777777777", "jack@123.com"));
        User jill = service.saveUser(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);
        Splitwise group = service.saveGroup(new Splitwise("Group", members));

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(jill, 60.));
        splits.add(new FixedSplit(jack, 40.));

        service.pay(group, jill, amount, SplitStrategy.FIXED, splits);

        Optional<User> foundUser = service.findUser(1);
        assertEquals(1, foundUser.get().getUserOwedTo().size());

        service.settle(jack, jill);
        foundUser = service.findUser(1);
        assertEquals(0, foundUser.get().getUserOwedTo().size());

    }

}
