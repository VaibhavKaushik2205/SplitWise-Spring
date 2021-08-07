package com.example.splitwise.Model;

import com.example.splitwise.Exception.InvalidSplitException;
import com.example.splitwise.Service.ExpenseReport;
import com.example.splitwise.Strategy.Split;
import com.example.splitwise.Strategy.SplitStrategy;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "splitwise")
public class Splitwise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(mappedBy = "groups")
    private Set<User> members;

    @Column
    private String nameOfGroup;

    public Splitwise() {

    }

    public Splitwise(String nameOfGroup, Set<User> members) {
        this.nameOfGroup = nameOfGroup;
        this.members = members;
        System.out.println(members.size());
    }

    public Splitwise(String nameOfGroup, Set<User> members, Integer id) {
        this.nameOfGroup = nameOfGroup;
        this.members = members;
        this.id = id;
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
