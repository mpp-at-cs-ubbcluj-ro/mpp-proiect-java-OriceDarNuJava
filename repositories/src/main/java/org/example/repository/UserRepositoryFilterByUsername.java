package org.example.repository;

import org.example.domain.User;

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
