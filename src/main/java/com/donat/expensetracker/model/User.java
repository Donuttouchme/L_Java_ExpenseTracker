package com.donat.expensetracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    public User(){}
    public User(String _username, String _password, String _role){
        username = _username;
        password = _password;
        role = _role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long _id){
        id = _id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String _username){
        username = _username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String _password) {
        password = _password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String _role) {
        role = _role;
    }
}
