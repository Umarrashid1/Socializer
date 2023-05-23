package com.group.p2_socializer.activities;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Event extends Activity {
    protected LocalDateTime localDateTime;
    protected ZoneId timeZone;


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public ZoneId getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(ZoneId timeZone) {
        this.timeZone = timeZone;
    }


}
