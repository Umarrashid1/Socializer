package com.group.p2_socializer.Database;

import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Gathering;

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
        String sql = "INSERT INTO gathering(eventname, eventdescription, eventcity, eventcountry, eventorganiser, localdatetime, timezone, activitytype) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        // connect to database
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newGathering.getActivityName());
        statement.setString(2, newGathering.getActivityDescription());
        statement.setString(3, newGathering.getActivityCity());
        statement.setString(4, newGathering.getActivityCountry());
        statement.setString(5, newGathering.getActivityOrganiser());
        statement.setObject(6, newGathering.getLocalDateTime());
        statement.setString(7, newGathering.getTimeZone().toString());
        statement.setString(8, newGathering.getActivityType().toString());

        //Convert timezone to string for storage in sql database
        statement.executeUpdate();
        connection.close();
    }

    public static List<Gathering> getGatheringsDate(int year) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM gathering " +
                "WHERE EXTRACT(YEAR FROM localdatetime) = ? ";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, year);
        ResultSet result = statement.executeQuery();
        List<Gathering> gatheringList = new ArrayList<>();
        while (result.next()) {
            Gathering newGathering = (Gathering) new Gathering.Builder()
                    .activityID(result.getInt("activityID"))
                    .activityType("activitytype")
                    .activityName(result.getString("eventname"))
                    .activityDescription(result.getString("eventdescription"))
                    .activityCity(result.getString("eventcity"))
                    .activityCountry(result.getString("eventcountry"))
                    .activityOrganiser(result.getString("eventorganiser"))
                    .localDateTime((LocalDateTime) result.getObject("localdatetime"))
                    .timeZone(ZoneId.systemDefault())
                    .build();
            gatheringList.add(newGathering);
        }
        connection.close();
        return gatheringList;

    }
    public static void deleteGathering(int gatheringID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE  FROM gathering WHERE gatheringID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, gatheringID);
        statement.executeUpdate();
        connection.close();
    }
}


