package com.example.splitwise.ModelTest;

import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import java.util.HashSet;
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

}
