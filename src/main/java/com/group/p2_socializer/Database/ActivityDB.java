package com.group.p2_socializer.Database;

import com.group.p2_socializer.activities.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ActivityDB {
    public static void storeEvent(Event newEvent) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO Activities(activityname, activitydescription, activitycity, activitycountry, " +
                "activityorganiser, activitydatetime, activitytimezone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // connect to database
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newEvent.getActivityName());
        statement.setString(2, newEvent.getActivityDescription());
        statement.setString(3, newEvent.getActivityCity());
        statement.setString(4, newEvent.getActivityCountry());
        statement.setString(5, newEvent.getActivityOrganiser());
        statement.setObject(6, newEvent.getLocalDateTime());
        statement.setString(7, newEvent.getTimeZone().toString());
        //Convert timezone to string for storage in sql database
        statement.executeUpdate();
        connection.close();
    }

    public static List getEvent(int month, int year) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM Activities " +
                "WHERE EXTRACT(YEAR FROM activitydatetime) = ? " +
                "AND EXTRACT(MONTH FROM activitydatetime) = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, year);
        statement.setInt(2, month);

        // Connect to database and issue command
        ResultSet result = statement.executeQuery();
        List<Event> eventList = new ArrayList<>();
        while (result.next()) {
            Event newEvent = (Event) new Event.Builder()
                    .activityID(result.getInt("activityID"))
                    .activityType("activitytype")
                    .activityName(result.getString("activityname"))
                    .activityDescription(result.getString("activitydescription"))
                    .activityCity(result.getString("activitycity"))
                    .activityCountry(result.getString("activitycountry"))
                    .activityOrganiser(result.getString("activityorganiser"))
                    .localDateTime((LocalDateTime) result.getObject("activitydatetime"))
                    .timeZone(ZoneId.systemDefault()) //FIX
                    //.eventVenue("Madison Square Garden")
                    //.eventOrganizerEmail("info@rockfest.com")
                    .build();
            eventList.add(newEvent);
        }
        connection.close();
        return eventList;
    }
    public static void deleteEvent(int eventID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE  FROM events " +
                "WHERE eventID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, eventID);
        statement.executeUpdate();
        connection.close();

    }

    public static List getTags(int activityID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT activitytags.tag FROM Activities " +
                "JOIN activitytagmap ON Activities.activityID = activitytagmap.activityID " +
                "JOIN activitytags On activitytags.tagID = activitytagmap.TagID " +
                "WHERE Activities.activityID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, activityID);
        ResultSet result = statement.executeQuery();
        List<String> tagList = new ArrayList<>();
        while (result.next()) {
            tagList.add(result.getString("tag"));
        }
        connection.close();
        return tagList;
    }

    public static void setTags(List tagList, int userID) {

    }
}


