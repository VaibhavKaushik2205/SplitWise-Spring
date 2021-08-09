package com.example.splitwise.RepositoryTest;

import static org.junit.jupiter.api.Assertions.assertNull;
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
        User user1 = userRepository.save(new User("John", "9999999999", "john@123.com"));

        Optional<User> userFoundById = userRepository.findById(user1.getId());
        assertTrue(userFoundById.isPresent());
    }

    @Test
    public void expectsToFindUserWithValidID() {
        Optional<User> userFoundById = userRepository.findById(1);
        assertTrue(userFoundById.isPresent());
    }

    @Test
    public void expectsToNotCreateDuplicateUserWithSameContact() {
        User user1 = userRepository.save(new User("John", "9999999999", "john@123.com"));
        User user2 = null;
        try{
            user2 = userRepository.save(new User("Jill", "9999999999", "jill@123.com"));
        }
        catch (Exception ignored){
        }
        assertNull(user2);
    }

    @Test
    public void expectsToNotCreateDuplicateUserWithSameEmail() {
        User user1 = userRepository.save(new User("Jill", "9999999999", "abc@123.com"));
        User user2 = null;
        try{
            user2 = userRepository.save(new User("John", "9876543210", "abc@123.com"));
        }
        catch (Exception ignored){
        }
        assertNull(user2);
    }
}
