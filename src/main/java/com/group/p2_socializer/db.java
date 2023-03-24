package com.group.p2_socializer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.*;

public class db {
    public static boolean checkUser(String username) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        User user = null;
        if(result.next()){
            connection.close();
            return true;
        }else{
            connection.close();
            return false;
        }
    }
    public static User authLogin(String username, String password) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);

        ResultSet result = statement.executeQuery();
        User user = null;
        if(result.next()){
            user = new User();
            //user.setAccess can set attributes to new user.
        }
        connection.close();
        return user;
    }

    public static boolean registerUser(String username, String password) throws SQLException {

        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";


        if (checkUser(username)){
            return false;

        }else{
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            connection.close();
            return true;
        }
    }

}

