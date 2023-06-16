package com.group.p2_socializer.Activities;

import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.UserLogIn.User;

import java.sql.SQLException;
import java.util.List;

public class Gathering extends Event {
    protected int gatheringID;
    protected int activityMinimumParticipants;
    protected int activityMaximumParticipants;
    public int getGatheringID() {
        return gatheringID;
    }


    public void setGatheringID(int gatheringID) {
        this.gatheringID = gatheringID;
    }

    public int getActivityMinimumParticipants() {
        return activityMinimumParticipants;
    }

    public void setActivityMinimumParticipants(int activityMinimumParticipants) {
        this.activityMinimumParticipants = activityMinimumParticipants;
    }

    public int getActivityMaximumParticipants() {
        return activityMaximumParticipants;
    }

    public void setActivityMaximumParticipants(int activityMaximumParticipants) {
        this.activityMaximumParticipants = activityMaximumParticipants;
    }
    public void setGatheringTags(List<Tag> selectedTagList) throws SQLException {
        GatheringDB.setGatheringTags(selectedTagList, gatheringID);
    }


    public List<User> getGatheringParticipants() throws SQLException {
        List<User> userList= GatheringDB.getGatheringParticipants(gatheringID);
        return userList;
    }
    public int getGatheringParticipantAmount() throws SQLException {
        List<Integer> userList = GatheringDB.getGatheringParticipantsID(gatheringID);
        int size = 0;
        if (userList != null) {
            size = userList.size();
        }
        return size;
    }
}
