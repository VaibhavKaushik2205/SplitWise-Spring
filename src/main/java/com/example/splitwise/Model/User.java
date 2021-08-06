package com.example.splitwise.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }
}
