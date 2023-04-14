package com.group.p2_socializer.activities;

import java.time.LocalDateTime;

public class Event extends Activity {
    private String eventVenue;
    private String eventOrganizerEmail;

    public Event(Builder builder) {
        super(builder);
        this.eventVenue = builder.eventVenue;
        this.eventOrganizerEmail = builder.eventOrganizerEmail;
    }
    public static class Builder extends Activity.Builder<Builder>{
        private String eventVenue;
        private String eventOrganizerEmail;

        public Builder() {
            super();
        }
        public Builder eventVenue(String eventVenue) {
            this.eventVenue = eventVenue;
            return this;
        }

        public Builder eventOrganizerEmail(String eventOrganizerEmail) {
            this.eventOrganizerEmail = eventOrganizerEmail;
            return this;
        }

    }
}