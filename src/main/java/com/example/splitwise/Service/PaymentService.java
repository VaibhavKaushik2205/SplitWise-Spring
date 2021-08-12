package com.example.splitwise.Service;

import com.example.splitwise.Model.ExpenseReport;
import com.example.splitwise.Model.Splitwise;
import com.example.splitwise.Model.User;
import com.example.splitwise.Repository.SplitwiseRepository;
import com.example.splitwise.Repository.UserRepository;
import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SplitwiseRepository splitwiseRepository;

    public void pay(Splitwise splitwise, User payee, Double amount, SplitStrategy strategy,
        List<Split> splits) {
        ExpenseReport report = new ExpenseReport(splitwise.getId(), payee.getId(), amount,
            strategy, splits);
        report.validate();

        for (Split split : splits) {
            if (!split.getUser().getId().equals(payee.getId())) {
                split.getUser().addOwesTo(payee, -1 * split.getAmount());
                payee.addOwesTo(split.getUser(), split.getAmount());
                userRepository.save(split.getUser());
            }
        }
        userRepository.save(payee);
    }

    public void settle(User user1, User user2){
        user1.removeOwesTo(user2);
        user2.removeOwesTo(user1);
        userRepository.save(user1);
        userRepository.save(user2);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Splitwise saveGroup(Splitwise group){
        return splitwiseRepository.save(group);
    }

    public Optional<User> findUser(Integer userID){
        return userRepository.findById(userID);
    }

    public Optional<Splitwise> findGroup(Integer groupID){
        return splitwiseRepository.findById(groupID);
    }

}
