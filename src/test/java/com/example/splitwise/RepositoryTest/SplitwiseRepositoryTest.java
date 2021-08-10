package com.example.splitwise.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import com.example.splitwise.Repository.SplitwiseRepository;
import com.example.splitwise.Repository.UserRepository;
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
public class SplitwiseRepositoryTest {

    @Autowired
    SplitwiseRepository splitwiseRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void expectsToCreateASplitWiseGroupInDatabase() {
        User jack = userRepository.save(new User("Jack", "7777777777", "jack@123.com"));
        User jill = userRepository.save(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);

        Splitwise group = splitwiseRepository.save(new Splitwise("Group", members));
        Optional<Splitwise> savedGroup = splitwiseRepository.findById(1);

        assertTrue(savedGroup.isPresent());
        assertEquals(2, savedGroup.get().getMembers().size());
        assertEquals(1, jack.getGroups().size());
    }

    @Test
    public void expectsToAddGroupMembersToPayeesDebtList(){
        User jack = userRepository.save(new User("Jack", "7777777777", "jack@123.com"));
        User jill = userRepository.save(new User("Jill", "8888888888", "jill@123.com"));

        Set<User> members = new HashSet<>();
        members.add(jack);
        members.add(jill);
        Splitwise group = splitwiseRepository.save(new Splitwise("Group", members));

        Double amount = 100.;
        List<Split> splits = new ArrayList<>();
        splits.add(new FixedSplit(jill, 60.));
        splits.add(new FixedSplit(jack, 40.));

        group.pay(jill, amount, SplitStrategy.FIXED, splits);

        User savedJack = userRepository.save(jack);
        User savedJill = userRepository.save(jill);

        Optional<User> user = userRepository.findById(1);
        assertEquals(1, user.get().getUserOwedTo().size());
    }

}
