package com.group.p2_socializer.Calendar;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CalendarDB {
    public static void storeEvent(String eventName, String eventDescription, String eventCity, String eventCountry, String eventOrganiser, LocalDateTime localDateTime, String timeZone) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO events(eventname, eventdescription, eventcity, eventcountry, eventorganiser, localdatetime, timezone) VALUES (?, ?, ?, ?, ?, ?, ?)";
        // connect to database
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, eventName);
        statement.setString(2, eventDescription);
        statement.setString(3, eventCity);
        statement.setString(4, eventCountry);
        statement.setString(5, eventOrganiser);
        statement.setObject(6, localDateTime);
        statement.setString(7, timeZone);
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
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        CalendarActivity newActivity = null;
        while (result.next()) {
            newActivity = new CalendarActivity();
            newActivity.localDateTime = (LocalDateTime) result.getObject("localdatetime");
            newActivity.timeZone = ZoneId.of(result.getString("timezone"));
            newActivity.eventName = result.getString("eventname");
            newActivity.eventDescription = result.getString("eventdescription"); //TODO: Fix "Data truncation: Data too long for column 'eventdescription' at row 1"
            newActivity.eventCity = result.getString("eventcity");
            newActivity.eventCountry = result.getString("eventcountry");
            newActivity.eventOrganiser = result.getString("eventorganiser");
            newActivity.date = ((LocalDateTime) result.getObject("localdatetime")).atZone(ZoneId.of(result.getString("timezone")));
            // Convert localdate
            calendarActivities.add(newActivity);
        }

        return calendarActivities;
    }
}


