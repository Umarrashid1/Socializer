package com.group.p2_socializer.UserLogIn;

import com.group.p2_socializer.Database.UserDB;

import java.sql.SQLException;
import java.util.List;

public class User {
    public int userID;
    public String username;
    public String password;

    public void joinGathering(int gatheringID) throws SQLException {
        UserDB.joinGathering(userID, gatheringID);
    }
    public void leaveGathering(int gatheringID) throws SQLException{
        UserDB.leaveGathering(userID, gatheringID);
    }
    public List getTags(int userID) throws SQLException {
        List tagList = UserDB.getTags(userID);
        return tagList;
    }

    public void setTags(List tagList){
        UserDB.setTags(tagList, userID);
    }
}
