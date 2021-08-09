package com.example.splitwise.Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;
    @Column(unique = true)
    private String contact;
    @Column(unique = true)
    private String email;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<Splitwise> groups = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "users_owed",
        joinColumns =
        @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "owedUser_id")
    @Column(name = "amount")
    private Map<Integer, Double> userOwesTo = new HashMap<>();

    public User() {
    }

    public User(String name, String contact, String email) {
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

    public User(String name, String contact, String email, Integer id) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

    public void addOwesTo(User owedUser, Double amountOwed) {
        if (userOwesTo.containsKey(owedUser.getId())) {
            Double newAmountOwed = userOwesTo.get(owedUser.getId()) + amountOwed;
            userOwesTo.put(owedUser.getId(), newAmountOwed);
            return;
        }
        userOwesTo.put(owedUser.getId(), amountOwed);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getContact() {
        return this.contact;
    }

    public String getEmail() {
        return this.email;
    }

    public Set<Integer> getUserOwedTo() {
        return userOwesTo.keySet();
    }

    public void addGroup(Splitwise group) {
        groups.add(group);
    }

    public void removeGroup(Splitwise group) {
        groups.remove(group);
    }

    public Set<Splitwise> getGroups() {
        return groups;
    }
}
