package com.group.p2_socializer.activities;

import com.group.p2_socializer.Database.EventDB;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

public class Activity {
    private int activityID;
    private String activityType;
    private String activityName;
    private String activityDescription;
    private String activityCity;
    private String activityCountry;
    private String activityOrganiser;
    private LocalDateTime localDateTime;
    private ZoneId timeZone;
    private ZonedDateTime zonedDatetime;

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
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public ZoneId getTimeZone() {
        return timeZone;
    }
    public ZonedDateTime getZonedDatetime() {
        return zonedDatetime;
    }

    public Activity(Builder builder) {
        this.activityID = builder.activityID;
        this.activityType = builder.activityType;
        this.activityName = builder.activityName;
        this.activityDescription = builder.activityDescription;
        this.activityCity = builder.activityCity;
        this.activityCountry = builder.activityCountry;
        this.activityOrganiser = builder.activityOrganiser;
        this.localDateTime = builder.localDateTime;
        this.timeZone = builder.timeZone;
        this.zonedDatetime = builder.zonedDatetime;
    }

    public static class Builder<T extends Builder<T>> {
        private int activityID;
        private String activityType;
        private String activityName;
        private String activityDescription;
        private String activityCity;
        private String activityCountry;
        private String activityOrganiser;
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
