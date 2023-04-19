package com.group.p2_socializer.activities;

import java.time.LocalDateTime;

public class Event extends Activity{
    public static class Builder extends Activity.Builder<Builder>{
        public Event build(){
            return new Event(this);
        }
    }
    public Event(Builder builder) {
        super(builder);
    }
}
