package com.example.jdbcgreenatom;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ConnectionConfig {
    @Bean
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/greenatom";
        String username = "postgres";
        String password = "251323Nn";

        return DriverManager.getConnection(url, username, password);
    }
}
