package com.example.womenshop.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBManager {

    private static final String url;
    private static final String user;
    private static final String password;

    static {
        Properties props = new Properties();
        try (InputStream input = DBManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


       url = props.getProperty("db.url");
        user = props.getProperty("db.user");
       password = props.getProperty("db.password");
    }

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

