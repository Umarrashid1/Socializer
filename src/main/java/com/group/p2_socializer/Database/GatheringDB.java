package com.group.p2_socializer.Database;

import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Gathering;
import com.group.p2_socializer.activities.Tag;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GatheringDB {
    public static int storeGathering(Gathering newGathering) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO gathering(eventname, eventdescription, eventcity, eventcountry, eventorganiser, localdatetime, timezone, activitytype, activitymin, activitymax) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        statement.setInt(9, newGathering.getActivityMinimumParticipants());
        statement.setInt(10, newGathering.getActivityMaximumParticipants());

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
                    .activityMinimumParticipants(result.getInt("activitymin"))
                    .activityMaximumParticipants(result.getInt("activitymax"))
                    .localDateTime((LocalDateTime) result.getObject("localdatetime"))
                    .timeZone(ZoneId.systemDefault())
                    .gatheringID(result.getInt("gatheringID"))
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
    public static List<Integer> getGatheringParticipantsID(int gatheringID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM Participations WHERE gatheringID = ? ";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, gatheringID);
        ResultSet result = statement.executeQuery();
        List<Integer> userList = new ArrayList<>();
        while (result.next()) {
            userList.add(result.getInt("userid"));
        }
        connection.close();
        return userList;
    }
    public static List<User> getGatheringParticipants(int gatheringID) throws SQLException {
        List <Integer> idList = getGatheringParticipantsID(gatheringID);
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM users WHERE userid IN (" + String.join(",", Collections.nCopies(idList.size(), "?")) + ")";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < idList.size(); i++) {
            statement.setInt(i + 1, idList.get(i));
        }
        ResultSet result = statement.executeQuery();
        List<User> userList = new ArrayList<>();
        while (result.next()) {
            User user = new User();
            user.setUserID(result.getInt("userid"));
            user.setUsername(result.getString("username"));
            user.setUserType(result.getString("usertype"));
            user.setFirstname(result.getString("firstname"));
            user.setLastname(result.getString("lastname"));
            userList.add(user);
        }
        connection.close();
        return userList;

    }

    public static void setGatheringTags(List<Tag> tagList, int activityID) throws SQLException {
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
}


