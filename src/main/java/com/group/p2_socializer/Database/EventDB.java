package com.group.p2_socializer.Database;

import com.group.p2_socializer.Calendar.Event;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EventDB {
    public static void storeEvent(Event newEvent) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO events(eventname, eventdescription, eventcity, eventcountry, eventorganiser, localdatetime, timezone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // connect to database
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, newEvent.eventName);
        statement.setString(2, newEvent.eventDescription);
        statement.setString(3, newEvent.eventCity);
        statement.setString(4, newEvent.eventCountry);
        statement.setString(5, newEvent.eventOrganiser);
        statement.setObject(6, newEvent.localDateTime);
        statement.setString(7, newEvent.timeZone.toString());
        //Convert timezone to string for storage in sql database
        statement.executeUpdate();
        connection.close();
    }

    public static List getEvent(int month, int year) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM events WHERE EXTRACT(YEAR FROM localdatetime) = ? AND EXTRACT(MONTH FROM localdatetime) = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, year);
        statement.setInt(2, month);

        // Connect to database and issue command
        ResultSet result = statement.executeQuery();
        List<Event> calendarActivities = new ArrayList<>();
        Event newEvent = null;
        while (result.next()) {
            newEvent = new Event();
            newEvent.localDateTime = (LocalDateTime) result.getObject("localdatetime");
            newEvent.timeZone = ZoneId.of(result.getString("timezone"));
            newEvent.eventName = result.getString("eventname");
            newEvent.eventDescription = result.getString("eventdescription"); //TODO: Fix "Data truncation: Data too long for column 'eventdescription' at row 1"
            newEvent.eventCity = result.getString("eventcity");
            newEvent.eventCountry = result.getString("eventcountry");
            newEvent.eventOrganiser = result.getString("eventorganiser");
            newEvent.zonedDatetime = ((LocalDateTime) result.getObject("localdatetime")).atZone(ZoneId.of(result.getString("timezone")));
            newEvent.id = result.getInt("id");
            // Convert localdate
            calendarActivities.add(newEvent);
        }
        connection.close();
        return calendarActivities;
    }
    public static void deleteEvent(int id) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "DELETE  FROM events WHERE id = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        connection.close();

    }
}


