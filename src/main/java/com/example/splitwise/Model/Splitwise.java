package com.example.splitwise.Model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    private Integer groupID;

    @Column
    private String nameOfGroup;

    @Column
    private double amount;

    @ManyToMany(mappedBy = "memberInGroups", fetch = FetchType.EAGER)
    private Set<User> members = new HashSet<>();

    public Splitwise() {

    }

    public Splitwise(String nameOfGroup, Set<User> members) {
        this.nameOfGroup = nameOfGroup;
        this.members = members;
    }

    public Splitwise(String nameOfGroup, double amount, Set<User> members) {
        this.nameOfGroup = nameOfGroup;
        this.amount = amount;
        this.members = members;
    }

    public Splitwise(String nameOfGroup, double amount, Set<User> members, Integer groupID) {
        this.nameOfGroup = nameOfGroup;
        this.amount = amount;
        this.members = members;
        this.groupID = groupID;
    }

    public void addUser(User user) {
        this.members.add(user);
    }

    public Integer getGroupId() {
        return this.groupID;
    }

    public double getAmount() {
        return this.amount;
    }

    public Set<User> getMembers() {
        return this.members;
    }
}
