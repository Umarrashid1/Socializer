package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.Pages.EventPageController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab calendarTab;
    @FXML
    public JFXButton goBack;

    @FXML
    public void handleCreateEvent() throws SQLException, IOException {
        Event newEvent = new Event();
        newEvent.eventName = eventNameTextField.getText();
        newEvent.eventCity = eventCityTextField.getText();
        newEvent.eventCountry = eventCountryTextField.getText();
        newEvent.eventDescription = eventDescriptionTextArea.getText();
        newEvent.eventOrganiser = eventOrganiserTextField.getText();
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        // get time user input
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);
        // combine localDate and localTime into localDateTime
        newEvent.localDateTime = localDateTime;
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        // convert localDateTime into ZonedDateTime
        newEvent.zonedDatetime = zonedDateTime;
        newEvent.timeZone = ZoneId.systemDefault();


        CalendarDB.storeEvent(newEvent);

        //Switch to Calendar tab
        mainTabPane.getSelectionModel().select(calendarTab);

        EventPageController controller = new EventPageController();
        controller.loadEventPage(newEvent);

        String createdMessage = "Event Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);


    }
}
