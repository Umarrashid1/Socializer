package com.group.p2_socializer.UserLogIn;

import com.group.p2_socializer.Database.UserDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int userID;
    private String username;
    private String password;
    private String userType;
    private List<String> userTagList = new ArrayList<>();

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }





    public void joinGathering(int gatheringID) throws SQLException {
        UserDB.joinGathering(userID, gatheringID);
    }
    public void leaveGathering(int gatheringID) throws SQLException{
        UserDB.leaveGathering(userID, gatheringID);
    }
    public ArrayList getTags(int userID) throws SQLException {
        ArrayList tagList = UserDB.getTags(userID);
        return tagList;
    }
    public void setTags(List tagList){
        UserDB.setTags(tagList, userID);
    }

}
