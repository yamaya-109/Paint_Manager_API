package com.example.paintapi.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String passwordHash;

    public User() {
    }

    public User(String userName, String passwordHash) {
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    // 必要なら：email, createdAtなど
    public String getuserName() {
        return userName;
    }

    public void setuserName(String username) {
        this.userName = username;
    }

    public String getpasswordHash() {
        return passwordHash;
    }

    public void setpasswordHash(String hash) {
        this.passwordHash = hash;
    }

}
