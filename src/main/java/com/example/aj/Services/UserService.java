package com.example.aj.Services;

import com.example.aj.DatabaseManager;
import com.example.aj.Entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private Connection cnx;

    public UserService() {
        cnx = DatabaseManager.getInstance().getCnx();
    }

    public User findUserByUsername(String username) {
        try {
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            } else {
                return null; // User with given email not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String login(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                return "false"; // User not found or incorrect credentials
            } else {
                String role = rs.getString("role");
                return role.equalsIgnoreCase("ADMIN") ? "admin" : "user"; // Return role type
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public User getUserById(int userId) {
        try {
            String query = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            } else {
                return null; // User with given ID not found
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(User user) {
        try {
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        try {
            String query = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int userId) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
