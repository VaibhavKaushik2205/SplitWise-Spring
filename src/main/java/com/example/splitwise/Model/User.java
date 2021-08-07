package com.example.splitwise.Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.persistence.Column;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column(unique = true)
    private String contact;
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_splitwise",
        joinColumns = {@JoinColumn(name = "users_id")},
        inverseJoinColumns = {@JoinColumn(name = "splitwise_id")})
    private Set<Splitwise> groups = new HashSet<>();

    private Map<User, Double> userOwesTo = new HashMap<>();

    private Map<User, Double>

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
        if (userOwesTo.containsKey(owedUser)) {
            Double newAmountOwed = userOwesTo.get(owedUser) + amountOwed;
            userOwesTo.put(owedUser, newAmountOwed);
            return;
        }
        userOwesTo.put(owedUser, amountOwed);

    }
`
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
}
