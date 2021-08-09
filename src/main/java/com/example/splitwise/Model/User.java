package com.example.splitwise.Model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    @Column
    private String contact;
    @Column
    private String email;

    @ManyToMany
    @JoinTable(
        name = "splitwise_member",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "splitwise_groupID"))
    private Set<Splitwise> memberInGroups;

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
