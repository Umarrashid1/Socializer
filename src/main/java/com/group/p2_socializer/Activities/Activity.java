package com.group.p2_socializer.Activities;

import com.group.p2_socializer.Database.ActivityDB;

import java.sql.SQLException;
import java.util.List;

public class Activity {


    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityCity() {
        return activityCity;
    }

    public void setActivityCity(String activityCity) {
        this.activityCity = activityCity;
    }

    public String getActivityCountry() {
        return activityCountry;
    }

    public void setActivityCountry(String activityCountry) {
        this.activityCountry = activityCountry;
    }

    public String getActivityOrganiser() {
        return activityOrganiser;
    }

    public void setActivityOrganiser(String activityOrganiser) {
        this.activityOrganiser = activityOrganiser;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    protected int activityID;
    protected String activityType;
    protected String activityName;
    protected String activityDescription;
    protected String activityCity;
    protected String activityCountry;
    protected String activityOrganiser;


    public List getTags() throws SQLException {
        List tagList = ActivityDB.getActivityTags(activityID);

        return tagList;
    }

    public void setTags(List<Tag> tagList) throws SQLException {
        ActivityDB.setTags(tagList, activityID);
    }
}
