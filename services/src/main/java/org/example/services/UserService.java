package org.example.services;

import org.example.domain.User;
import org.example.interfaces.UserServiceInterface;
import org.example.repository.Repository;
import org.example.repository.RepositoryFilter;
import org.example.repository.UserRepositoryFilterByUsername;

import java.sql.SQLException;
import java.util.List;

public class UserService implements UserServiceInterface {
    Repository<User> userRepository;

    public UserService(Repository<User> userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String username) throws SQLException {
        RepositoryFilter<User> filter = new UserRepositoryFilterByUsername(username);
        List<User> matchedUsers = this.userRepository.get(filter);

        if (matchedUsers.size() == 0) {
            return null;
        }

        return matchedUsers.get(0);
    }
}
