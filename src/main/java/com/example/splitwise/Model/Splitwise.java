package com.example.splitwise.Model;

import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "splitwiseGroups")
public class Splitwise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_splitwise",
        joinColumns =
        @JoinColumn(name = "splitwise_id"),
        inverseJoinColumns =
        @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();

    private String nameOfGroup;

    public Splitwise() {

    }

    public Splitwise(String nameOfGroup, Set<User> members) {
        this.nameOfGroup = nameOfGroup;
        for (User user : members) {
            user.addGroup(this);
            this.members.add(user);
        }
    }

    public Splitwise(String nameOfGroup, Set<User> members, Integer id) {
        this.nameOfGroup = nameOfGroup;
        this.id = id;
        for (User user : members) {
            user.addGroup(this);
            this.members.add(user);
        }
    }

    public void pay(User payee, Double amount, SplitStrategy strategy, List<Split> splits) {
        ExpenseReport report = new ExpenseReport(this.id, payee.getId(), amount, strategy, splits);
        report.validate();

        for (Split split : splits) {
            if (!split.getUser().getId().equals(payee.getId())) {
                split.getUser().addOwesTo(payee, -1 * split.getAmount());
                payee.addOwesTo(split.getUser(), split.getAmount());
            }
        }
    }

    public void addUser(User user) {
        this.members.add(user);
        user.addGroup(this);
    }

    public void removeUser(User user) {
        this.members.remove(user);
        user.removeGroup(this);
    }

    public Integer getId() {
        return this.id;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public Set<User> getMembers() {
        return this.members;
    }
}
