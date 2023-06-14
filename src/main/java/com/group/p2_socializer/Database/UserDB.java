package com.group.p2_socializer.Database;
import com.group.p2_socializer.UserLogIn.AdminUser;
import com.group.p2_socializer.UserLogIn.SuperUser;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDB {
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
    public static User authLogin(String username, String password) throws SQLException, NoSuchAlgorithmException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String authSQL = "SELECT * FROM users WHERE username = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(authSQL);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();

        if(result.next()){
            String hashedPassword = result.getString("password");
            if(PasswordUtils.verifyPassword(password, hashedPassword)){
                if(Objects.equals(result.getString("usertype"), "user")){
                    User user = new User();
                    user.setUserID(result.getInt("userid"));
                    user.setUsername(result.getString("username"));
                    user.setFirstname(result.getString("firstname"));
                    user.setLastname(result.getString("lastname"));
                    return user;
                }else if(Objects.equals(result.getString("usertype"), "admin")){
                    AdminUser AdminUser = new AdminUser();
                    AdminUser.setUserID(result.getInt("userid"));
                    AdminUser.setUsername(result.getString("username"));
                    AdminUser.setFirstname(result.getString("firstname"));
                    AdminUser.setLastname(result.getString("lastname"));
                    return AdminUser;
                }

            }else{
                return null;
                // Wrong password
            }
        }return null;
        // Wrong username

    }


    public static boolean registerUser(String username, String password, String firstName, String lastName, String country, String city, String email) throws SQLException, NoSuchAlgorithmException {

        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO users(username, password, firstname, lastname) VALUES (?, ?, ?, ?)";
        // connect to database

        if (checkUser(username)){
            return false;

        }else{
            String hashedPassword = PasswordUtils.hashPassword(password);
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.executeUpdate();
            connection.close();
            return true;
        }
    }

    public static ArrayList getTags(int userID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT usertags.tag FROM users " +
                "JOIN usertagmap ON users.userid = usertagmap.userID " +
                "JOIN usertags On usertags.tagID = usertagmap.TagID " +
                "WHERE users.userid = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userID);
        ResultSet result = statement.executeQuery();
        ArrayList<String> tagList = new ArrayList<>();
        while (result.next()) {
            tagList.add(result.getString("tag"));
        }
        connection.close();
        return tagList;
    }

    public static void setTags(List tagList, int userID) {

    }

    public void deleteUser(int userID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE FROM users WHERE userid = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userID);
        statement.executeUpdate();
        connection.close();
    }

    public static void joinGathering(int userID, int gatheringID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO Participations(userID, gatheringID) VALUES (?, ?)";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userID);
        statement.setInt(2, gatheringID);
        statement.executeUpdate();
        connection.close();
    }
    public static void leaveGathering(int userID, int gatheringID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE FROM Participations WHERE userID = ? AND gatheringID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userID);
        statement.setInt(2, gatheringID);
        statement.executeUpdate();
        connection.close();
    }
}

