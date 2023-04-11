package com.group.p2_socializer.UserLogIn;
import java.sql.*;
public class LoginDB {
    public static boolean checkUser(String username) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();
        // connect to database and issue command
        if(result.next()){
            connection.close();
            return true;
        }else{
            connection.close();
            return false;
        }
        // return true if username exists
    }
    public static User authLogin(String username, String password) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String authSQL = "SELECT * FROM users WHERE username = ? and password = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(authSQL);
        statement.setString(1, username);
        statement.setString(2, password);
        // Connect to database and issue command


        ResultSet result = statement.executeQuery();
        User user = null;
        if(result.next()){
            user = new User();
            user.id = result.getInt("id");
            user.username = result.getString("username");
            user.password = result.getString("password");
        // assign database values to current user
        }
        connection.close();
        return user;
        // if login is successful then return non-null user variable
    }

    public static boolean registerUser(String username, String password) throws SQLException {

        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
        // connect to database

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
    public static void deleteUser(int id) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE FROM users WHERE id = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        connection.close();



    }

}

