package com.group.p2_socializer.Database;

import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Activities.Gathering;
import com.group.p2_socializer.Activities.Tag;

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
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, newGathering.getActivityName());
        statement.setString(2, newGathering.getActivityDescription());
        statement.setString(3, newGathering.getActivityCity());
        statement.setString(4, newGathering.getActivityCountry());
        statement.setString(5, newGathering.getActivityOrganiser());
        statement.setObject(6, newGathering.getLocalDateTime());
        statement.setString(7, newGathering.getTimeZone().toString());
        statement.setString(8, newGathering.getActivityType());
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
            Gathering newGathering = new Gathering();
            newGathering.setActivityID(result.getInt("activityID"));
            newGathering.setActivityType("activitytype");
            newGathering.setActivityName(result.getString("eventname"));
            newGathering.setActivityDescription(result.getString("eventdescription"));
            newGathering.setActivityCity(result.getString("eventcity"));
            newGathering.setActivityCountry(result.getString("eventcountry"));
            newGathering.setActivityOrganiser(result.getString("eventorganiser"));
            newGathering.setActivityMinimumParticipants(result.getInt("activitymin"));
            newGathering.setActivityMaximumParticipants(result.getInt("activitymax"));
            newGathering.setLocalDateTime((LocalDateTime) result.getObject("localdatetime"));
            newGathering.setTimeZone(ZoneId.systemDefault());
            newGathering.setGatheringID(result.getInt("gatheringID"));
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
        if (result.next()) {
            do {
                userList.add(result.getInt("userid"));
            } while (result.next());
        } else {
            userList = null;
        }

        connection.close();
        return userList;
    }
    public static List<User> getGatheringParticipants(int gatheringID) throws SQLException {
        List<Integer> idList = getGatheringParticipantsID(gatheringID);
        if(idList != null){
            String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
            String dbUser = "root";
            String dbPassword = "password";
            String sql = "SELECT * FROM users WHERE userid IN (" + String.join(",", Collections.nCopies(idList.size(), "?")) + ")";
            List<User> userList = new ArrayList<>();
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < idList.size(); i++) {
                statement.setInt(i + 1, idList.get(i));
            }

            ResultSet result = statement.executeQuery();
                while (result.next()) {
                    User user = new User();
                    user.setUserID(result.getInt("userid"));
                    user.setUsername(result.getString("username"));
                    user.setUserType(result.getString("usertype"));
                    user.setFirstname(result.getString("firstname"));
                    user.setLastname(result.getString("lastname"));
                    userList.add(user);
                }
                return userList;

            }else{
            return null;
        }


    }


    public static void setGatheringTags(List<Tag> tagList, int gatheringID) throws SQLException {
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "INSERT INTO gatheringtagmap (gatheringID, tagID) VALUES (?, ?)";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        if(tagList != null){
            for (Tag tag : tagList) {
                statement.setInt(1, gatheringID);
                statement.setInt(2, tag.getTagID());
                statement.executeUpdate();
            }
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

    public static List getGatheringTags(int gatheringID) throws SQLException {
        // get all activity tags
        String dbUrl = "jdbc:mysql://130.225.39.187:3336/socializer?autoReconnect=true&useSSL=false";
        String dbUser = "root";
        String dbPassword = "password";
        String sql = "SELECT * FROM gathering " +
                "JOIN gatheringtagmap ON gathering.gatheringID = gatheringtagmap.gatheringID " +
                "JOIN gatheringtag On gatheringtag.tagID = gatheringtagmap.TagID " +
                "WHERE gathering.gatheringID = ?";
        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, gatheringID);
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


