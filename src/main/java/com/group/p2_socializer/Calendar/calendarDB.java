package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class calendarDB {
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
    public static CalendarActivity getEvent(Date date) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM events WHERE DATE(localdatetime) = ? ";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, date);

        // Connect to database and issue command
        ResultSet result = statement.executeQuery();
        CalendarActivity newActivity = null;
        if(result.next()){
            newActivity = new CalendarActivity();
            newActivity.localDateTime = (LocalDateTime) result.getObject("localdatetime");
            newActivity.timeZone = ZoneId.of(result.getString("timezone"));
            newActivity.eventName = result.getString("timezone");
            newActivity.eventName = result.getString("eventname");
            newActivity.eventDescription = result.getString("eventdescription");
            newActivity.eventCity = result.getString("eventcity");
            newActivity.eventCountry = result.getString("eventcountry");
            newActivity.eventOrganiser = result.getString("eventorganiser");
            newActivity.date = ((LocalDateTime) result.getObject("localdatetime")).atZone(ZoneId.of(result.getString("timezone")));
        }
        return newActivity;
    }
}


