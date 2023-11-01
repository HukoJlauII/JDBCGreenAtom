package com.example.jdbcgreenatom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDao {
    private final Connection connection;

    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (id, username, creation_date, email, password) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getUsername());
            statement.setDate(3, user.getCreationDate());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPassword());

            statement.executeUpdate();
        }
    }

    public void updateUser(User user) throws SQLException {
        String query = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());

            statement.executeUpdate();
        }
    }

    public void deleteUser(Long userId) throws SQLException {
        String query = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            statement.executeUpdate();
        }
    }

    public User getUserById(Long userId) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractUserFromResultSet(resultSet);
                }
            }
        }

        return null;
    }

    public List<User> getUsersWithFilterAndPagination(String usernameFilter, int limit, int offset) throws SQLException {
        String query = "SELECT * FROM users WHERE username LIKE ? ORDER BY id LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "%" + usernameFilter + "%");
            statement.setLong(2, limit);
            statement.setLong(3, offset);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<User> users = new ArrayList<>();

                while (resultSet.next()) {
                    User user = extractUserFromResultSet(resultSet);
                    users.add(user);
                }

                return users;
            }
        }
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String username = resultSet.getString("username");
        Date creationDate = resultSet.getDate("creation_date");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        return new User(id, username, creationDate, email, password);
    }
}
