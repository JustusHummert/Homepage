package com.server.homepage.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {
    //id of the only admin
    @Id
    private Integer id = 0;

    //password of the admin
    private String password;

    public Admin() {
    }

    public Admin(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
