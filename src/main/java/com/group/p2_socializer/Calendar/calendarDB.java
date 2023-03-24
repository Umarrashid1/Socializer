package com.group.p2_socializer.Calendar;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class calendarDB {
    public static boolean storeEvent(String eventName, String eventDescription, String eventCity, String eventCountry, String eventOrganiser, LocalDateTime localDateTime, String timeZone) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/events?autoReconnect=true&useSSL=false";
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
        statement.setString(6, timeZone);

        statement.executeUpdate();
        connection.close();
        return true;
    }
}
