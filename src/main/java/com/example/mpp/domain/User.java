package com.example.mpp.domain;

public class User extends Entity {
    private String userName;

    public User(Integer id, String userName) {
        super(id);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
