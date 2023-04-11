package com.example.mpp.repository;

import com.example.mpp.domain.User;

public class UserRepositoryFilterByUsername implements RepositoryFilter<User> {
    private String username;

    public UserRepositoryFilterByUsername(String username) {
        this.username = username;
    }

    @Override
    public String getSql() {
        return "WHERE username = '" + username + "'";
    }
}
