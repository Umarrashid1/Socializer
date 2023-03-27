package com.group.p2_socializer.Calendar;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarManager {
    public static Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) throws SQLException {
        if (dateFocus == null) {
            throw new IllegalArgumentException("dateFocus cannot be null");
        }
        List<CalendarActivity> calendarActivities = new ArrayList<>();
        int year = dateFocus.getYear();
        int month = dateFocus.getMonth().getValue();
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int i = 1; i < daysInMonth; i++ ){
            LocalDate date = LocalDate.of(year, month, i);
            CalendarActivity calendarActivity = calendarDB.getEvent(Date.valueOf(date));
            if (calendarActivity != null){
                calendarActivities.add(calendarActivity);
            }
        }

        return createCalendarMap(calendarActivities);
    }

    public static Map<Integer, List<CalendarActivity>> createCalendarMap(List<CalendarActivity> calendarActivities) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        for (CalendarActivity activity: calendarActivities) {
            int activityDate = activity.getDate().getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);
                List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return  calendarActivityMap;
    }
}
