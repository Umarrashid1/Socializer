package com.group.p2_socializer.Calendar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CalendarActivity {
    public LocalDateTime localDateTime;
    public ZoneId timeZone;
    public ZonedDateTime date;
    public String eventName;
    public String eventDescription;
    public String eventCity;
    public String eventCountry;
    public String eventOrganiser;



    public ZonedDateTime getDate() {
        return date;
    }
    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
    public String getEventName() {
        return eventName;
    }
    public String getEventCity(){
        return eventCity;
    }
    public String getEventCountry(){
        return eventCountry;
    }
    public String getEventOrganiser(){
        return eventOrganiser;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventDescription() {
        return eventDescription;
    }

    //public void setEventDescription(String eventDescription) {
      //  this.eventDescription = eventDescription;
    //}

    @Override
    public String toString() {
        return "CalendarActivity{" +
                "date=" + date +
                ", name='" + eventName + '\'' +
                ", description='" + eventDescription + '\'' +
                ", city='" + eventCity + '\'' +
                ", country='" + eventCountry + '\'' +
                ", organiser='" + eventOrganiser + '\'' +
                '}';
    }
}