package com.example.splitwise.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import com.example.splitwise.Repository.SplitwiseRepository;
import com.example.splitwise.Repository.UserRepository;
import java.util.HashSet;
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
        //assertEquals(2, savedGroup.get().getMembers().size());
    }


}
