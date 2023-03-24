package com.group.p2_socializer.Calendar;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateEventController {

    @FXML
    private TextField eventNameTextField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField eventTimeTextField;

    @FXML
    private TextField eventCityTextField;
    @FXML
    private TextField eventOrganiserTextField;

    @FXML
    private TextField eventCountryTextField;

    @FXML
    private TextArea eventDescriptionTextArea;

    private CalendarManager calendarManager;


    private CalendarController calendarController;

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }

    @FXML
    private void initialize() {
        calendarManager = new CalendarManager();

    }

    @FXML
    private void handleCreateEvent() throws SQLException {
        String eventName = eventNameTextField.getText();
        LocalDate eventDate = eventDatePicker.getValue();
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        String eventCity = eventCityTextField.getText();
        String eventCountry = eventCountryTextField.getText();
        String eventDescription = eventDescriptionTextArea.getText();
        String eventOrganiser = eventOrganiserTextField.getText();

        LocalDateTime localDateTime = LocalDateTime.of(eventDate, eventTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());

        ZoneId zoneId = ZoneId.systemDefault();
        String timeZone = zoneId.toString();

        calendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);
        CalendarActivity newEvent = new CalendarActivity(zonedDateTime, eventName, eventDescription, eventCity, eventCountry, eventOrganiser);

        // Get the calendar map from the calendar manager
        Map<Integer, List<CalendarActivity>> calendarMap = CalendarManager.getCalendarActivitiesMonth(ZonedDateTime.now());

        // Get the day of the new event
        int dayOfMonth = newEvent.getDate().getDayOfMonth();

        // Get the list of events for the day from the calendar map
        List<CalendarActivity> eventsForDay = calendarMap.getOrDefault(dayOfMonth, new ArrayList<>());

        // Add the new event to the list of events for the day
        eventsForDay.add(newEvent);

        // Put the updated list back into the calendar map
        calendarMap.put(dayOfMonth, eventsForDay);
        // Print the updated list
        System.out.println("Updated events for day " + dayOfMonth + ": " + eventsForDay);

        // Draw the updated calendar
       // CalendarController calendarController = new CalendarController();
        calendarController.drawCalendar(calendarMap);

        // Set the focus to the date of the new event

        // Close the dialog
        eventNameTextField.getScene().getWindow().hide();
    }




}
