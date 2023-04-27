package com.group.p2_socializer.Database;

import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Tag;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ActivityDB {
    public static int storeEvent(Event newEvent) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO Activities(activityname, activitydescription, activitycity, activitycountry, " +
                "activityorganiser, activitydatetime, activitytimezone, activitytype) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        // connect to database
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, newEvent.getActivityName());
        statement.setString(2, newEvent.getActivityDescription());
        statement.setString(3, newEvent.getActivityCity());
        statement.setString(4, newEvent.getActivityCountry());
        statement.setString(5, newEvent.getActivityOrganiser());
        statement.setObject(6, newEvent.getLocalDateTime());
        statement.setString(7, newEvent.getTimeZone().toString());
        statement.setString(8, newEvent.getActivityType());

        //Convert timezone to string for storage in sql database
        statement.executeUpdate();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int activityID = generatedKeys.getInt(1);
            connection.close();
            return activityID;
        } else {
            connection.close();
            throw new SQLException("Insertion failed, no activity ID obtained.");
        }
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
                    .activityMinimumParticipants(result.getInt("activitymin"))
                    .localDateTime((LocalDateTime) result.getObject("activitydatetime"))
                    .timeZone(ZoneId.systemDefault()) //FIX
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
        String sql = "DELETE  FROM Activities " +
                "WHERE activityID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, eventID);
        statement.executeUpdate();
        connection.close();

    }
    //activitytags.tag
    public static List getActivityTags(int activityID) throws SQLException {
        //Get tags for specific activity
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM Activities " +
                "JOIN activitytagmap ON Activities.activityID = activitytagmap.activityID " +
                "JOIN activitytags On activitytags.tagID = activitytagmap.TagID " +
                "WHERE Activities.activityID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, activityID);
        ResultSet result = statement.executeQuery();
        List<Tag> tagList = new ArrayList<>();
        while (result.next()) {
            Tag tag = new Tag();
            tag.setTagID(result.getInt("tagID"));
            tag.setTag(result.getString("tag"));
            tagList.add(tag);
        }
        connection.close();
        return tagList;
    }
    public static List getTags() throws SQLException {
        // get all activity tags
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM activitytags";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        List<Tag> tagList = new ArrayList<>();
        while (result.next()) {
            Tag tag = new Tag();
            tag.setTagID(result.getInt("tagID"));
            tag.setTag(result.getString("tag"));
            tagList.add(tag);
        }
        connection.close();
        return tagList;
    }

    public static void setTags(List<Tag> tagList, int activityID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "I";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO activitytagmap (activityID, tagID) VALUES (?, ?)");

        for (Tag tag : tagList) {
            statement.setInt(1, activityID);
            statement.setInt(2, tag.getTagID());
            statement.executeUpdate();
        }

        statement.close();
        connection.close();
    }
}


