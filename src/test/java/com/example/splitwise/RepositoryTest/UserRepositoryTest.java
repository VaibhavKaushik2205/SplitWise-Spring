package com.example.splitwise.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.splitwise.Model.User;
import com.example.splitwise.Repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void expectsToCreateUserInPersistentStorage() {
        User User1 = userRepository.save(new User("John", "9999999999", "john@123.com"));

        Optional<User> userFoundById = userRepository.findById(User1.getId());
        assertTrue(userFoundById.isPresent());
    }

    @Test
    public void expectsToFindUserWithValidID() {
        Optional<User> userFoundById = userRepository.findById(1);
        assertTrue(userFoundById.isPresent());
    }
}
