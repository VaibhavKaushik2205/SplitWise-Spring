package com.example.splitwise.Repository;

import com.example.splitwise.Model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    public Optional<User> findByContact(String contact);
}
