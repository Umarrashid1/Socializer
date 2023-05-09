package com.group.p2_socializer.activities;

import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.UserLogIn.User;

import java.sql.SQLException;
import java.util.List;

public class Gathering extends Activity {
    private int gatheringID;

    public List<User> getGatheringParticipants() throws SQLException {
        List<User> userList = GatheringDB.getGatheringParticipants(gatheringID);
        return userList;
    }

    public int getGatheringID() {
        return gatheringID;
    }

    private Gathering(Builder builder) {
        super(builder);
        this.gatheringID = builder.gatheringID;
    }

    public static class Builder extends Activity.Builder<Builder> {
        private int gatheringID;

        public Builder gatheringID(int gatheringID) {
            this.gatheringID = gatheringID;
            return this;
        }

        public Gathering build() {
            return new Gathering(this);
        }
    }
}

