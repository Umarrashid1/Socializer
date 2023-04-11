package com.group.p2_socializer.Database;

import com.group.p2_socializer.Calendar.Event;
import com.group.p2_socializer.CreateGatherings.Gathering;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class GatheringDB {
    public static void storeGathering(Gathering newGathering) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO gathering(eventname, eventdescription, eventcity, eventcountry, eventorganiser, localdatetime, timezone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // connect to database
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newGathering.eventName);
        statement.setString(2, newGathering.eventDescription);
        statement.setString(3, newGathering.eventCity);
        statement.setString(4, newGathering.eventCountry);
        statement.setString(5, newGathering.eventOrganiser);
        statement.setObject(6, newGathering.localDateTime);
        statement.setString(7, newGathering.timeZone.toString());
        //Convert timezone to string for storage in sql database
        statement.executeUpdate();
        connection.close();
    }

    public static void getGatherings() throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";

    }
    public static void deleteGathering(int id) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE  FROM gathering WHERE id = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        connection.close();
    }
}


