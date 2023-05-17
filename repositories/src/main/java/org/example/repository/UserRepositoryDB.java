package org.example.repository;

import org.example.domain.User;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryDB implements Repository<User> {
    private Connection connection;
    private Logger logger;

    public UserRepositoryDB(Connection connection, Logger logger) {
        this.connection = connection;
        this.logger = logger;
    }

    @Override
    public void push(User item) throws SQLException {

    }

    @Override
    public List<User> get(Integer id) {
        return null;
    }

    @Override
    public List<User> get(RepositoryFilter<User> filter) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM labmpppb6.users " + filter.getSql();
        ResultSet rs = statement.executeQuery(sql);

        List<User> users = new ArrayList<>();

        while(rs.next()) {
            Integer id = rs.getInt("id");
            String username = rs.getString("username");
            User user = new User(id, username);

            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User oldValue, User newValue) {

    }

    @Override
    public int delete(Integer id) {
        return 0;
    }

    @Override
    public int delete(RepositoryFilter<User> filter) {
        return 0;
    }
}
