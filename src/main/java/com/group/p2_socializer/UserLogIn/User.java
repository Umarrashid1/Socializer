package com.group.p2_socializer.UserLogIn;

import com.group.p2_socializer.Database.UserDB;

import java.sql.SQLException;

public class User {
    public int userID;
    public String username;
    public String password;

    public void joinGathering(int gatheringID) throws SQLException {
        UserDB.joinGathering(userID, gatheringID);
    }
}
