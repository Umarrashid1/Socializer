package com.group.p2_socializer.activities;

import com.group.p2_socializer.Database.ActivityDB;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    public int activityID;
    private String activityType;
    private String activityName;
    private String activityDescription;
    private String activityCity;
    private String activityCountry;
    private String activityOrganiser;
    private String activityMinimumParticipants;
    private String activityMaximumParticipants;
    private LocalDateTime localDateTime;
    private ZoneId timeZone;


    private ArrayList attendeeList = new ArrayList<>();
    //todo: use attendeeList for collecting the attendees of a gathering
    /*
    public ArrayList setAttendeeList(ArrayList attendeelist, User user) {
        attendeeList.add(user.getUserID()); //should be nickname or use first and last to get initials
    }

    public ArrayList getAttendeeList() {
        return attendeList;
    }
     */

    public int getActivityID() {
        return activityID;
    }
    public String getActivityType() {
        return activityType;
    }
    public String getActivityName() {
        return activityName;
    }
    public String getActivityDescription() {
        return activityDescription;
    }
    public String getActivityCity() {
        return activityCity;
    }
    public String getActivityCountry() {
        return activityCountry;
    }
    public String getActivityOrganiser() {
        return activityOrganiser;
    }
    public String getActivityMinimumParticipants() {
        return activityMinimumParticipants;
    }
    public String getActivityMaximumParticipants() {
        return activityMaximumParticipants;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public ZoneId getTimeZone() {
        return timeZone;
    }
    public List getTags() throws SQLException {
        List tagList = ActivityDB.getActivityTags(activityID);

        return tagList;
    }

    public void setTags(List<Tag> tagList) throws SQLException {
        ActivityDB.setTags(tagList, activityID);
    }

    public Activity(Builder builder) {
        this.activityID = builder.activityID;
        this.activityType = builder.activityType;
        this.activityName = builder.activityName;
        this.activityDescription = builder.activityDescription;
        this.activityCity = builder.activityCity;
        this.activityCountry = builder.activityCountry;
        this.activityOrganiser = builder.activityOrganiser;
        this.activityMinimumParticipants = builder.activityMinimumParticipants;
        this.activityMaximumParticipants = builder.activityMaximumParticipants;
        this.localDateTime = builder.localDateTime;
        this.timeZone = builder.timeZone;
    }

    public static class Builder<T extends Builder<T>> {
        private int activityID;
        private String activityType;
        private String activityName;
        private String activityDescription;
        private String activityCity;
        private String activityCountry;
        private String activityOrganiser;
        private String activityMinimumParticipants;
        private String activityMaximumParticipants;
        private LocalDateTime localDateTime;
        private ZoneId timeZone;
        private ZonedDateTime zonedDatetime;


        public Builder() {
        }
        public T activityID(int activityID) {
            this.activityID = activityID;
            return self();
        }

        public T activityType(String activityType) {
            this.activityType = activityType;
            return self();
        }

        public T activityName(String activityName) {
            this.activityName = activityName;
            return self();
        }

        public T activityDescription(String activityDescription) {
            this.activityDescription = activityDescription;
            return self();
        }

        public T activityCity(String activityCity) {
            this.activityCity = activityCity;
            return self();
        }

        public T activityCountry(String activityCountry) {
            this.activityCountry = activityCountry;
            return self();
        }

        public T activityOrganiser(String activityOrganiser) {
            this.activityOrganiser = activityOrganiser;
            return self();
        }
        public T activityMinimumParticipants(String activityMinimumParticipants) {
            this.activityMinimumParticipants = activityMinimumParticipants;
            return self();
        }
        public T activityMaximumParticipants(String activityMaximumParticipants) {
            this.activityMaximumParticipants = activityMaximumParticipants;
            return self();
        }

        public T localDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            return self();
        }

        public T timeZone(ZoneId timeZone) {
            this.timeZone = timeZone;
            return self();
        }

        public T zonedDatetime(ZonedDateTime zonedDatetime) {
            this.zonedDatetime = zonedDatetime;
            return self();
        }

        public Activity build() {
            return new Activity(this);
        }

        protected T self() {
            return (T) this;
        }


    }
}
