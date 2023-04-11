package com.example.mpp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresJDBC {
    public static Connection getConnection(Properties properties) {
        Connection c = null;
        try {
            c = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
