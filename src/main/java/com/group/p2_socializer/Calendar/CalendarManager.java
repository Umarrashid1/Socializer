package com.group.p2_socializer.Calendar;
import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.activities.Event;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CalendarManager {
    public static Map<Integer, List<Event>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) throws SQLException {
        if (dateFocus == null) {
            throw new IllegalArgumentException("dateFocus cannot be null");
        }
        List<Event> eventList;
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();
        eventList = ActivityDB.getEvent(month, year);
        return createCalendarMap(eventList);
    }

    public static Map<Integer, List<Event>> createCalendarMap(List<Event> calendarActivities) {
        Map<Integer, List<Event>> calendarActivityMap = new HashMap<>();

        for (Event activity: calendarActivities) {
            int activityDate = activity.getLocalDateTime().getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<Event> OldListByDate = calendarActivityMap.get(activityDate);
                List<Event> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return  calendarActivityMap;
    }
}
