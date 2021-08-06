package com.example.splitwise.Repository;

import com.example.splitwise.Model.Splitwise;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface SplitwiseRepository extends CrudRepository<Splitwise, Integer> {

    public Optional<Splitwise> findByNameOfGroup(String name);
}
