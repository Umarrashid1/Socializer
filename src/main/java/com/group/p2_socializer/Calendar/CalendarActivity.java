package com.group.p2_socializer.Calendar;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String eventName;
    private String eventDescription;
    private String eventCity;
    private String eventCountry;
    private String eventOrganiser;

    public CalendarActivity(ZonedDateTime date, String eventName, String eventDescription,String eventCity, String eventCountry, String eventOrganiser) {
        this.date = date;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCity = eventCity;
        this.eventCountry = eventCountry;
        this.eventOrganiser = eventOrganiser;
    }

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