package org.example.interfaces;

import org.example.domain.User;
import org.example.repository.Repository;
import org.example.repository.RepositoryFilter;
import org.example.repository.UserRepositoryFilterByUsername;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserServiceInterface {
    User getByUsername(String username) throws SQLException, IOException;
}
