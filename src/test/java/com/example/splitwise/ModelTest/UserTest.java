package com.example.splitwise.ModelTest;

import com.example.splitwise.Model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void expectToCreateUser() {
        User john = new User("John", "99999999999", "john@123.com", 1);
        Assertions.assertEquals(1, john.getId());
        Assertions.assertEquals("John", john.getName());
    }
}
