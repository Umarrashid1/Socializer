package com.group.p2_socializer.CreateGatherings;

import com.group.p2_socializer.Calendar.CalendarDB;
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

public class CreateCustomGatheringController {


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

        String eventName = eventNameTextField.getText();
        LocalDate eventDate = eventDatePicker.getValue();
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        String eventCity = eventCityTextField.getText();
        String eventCountry = eventCountryTextField.getText();
        String eventDescription = eventDescriptionTextArea.getText();
        String eventOrganiser = eventOrganiserTextField.getText();
        LocalDateTime localDateTime = LocalDateTime.of(eventDate, eventTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'AT' HH:mm", Locale.ENGLISH);
        String formattedDate = zonedDateTime.format(formatter).toUpperCase();
        ZoneId zoneId = ZoneId.systemDefault();
        String timeZone = zoneId.toString();
        CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);

        //Switch to Calendar tab
        mainTabPane.getSelectionModel().select(calendarTab);

        EventPageController controller = new EventPageController();
        controller.loadEventPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);

        String createdMessage = "Event Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);


    }
}
