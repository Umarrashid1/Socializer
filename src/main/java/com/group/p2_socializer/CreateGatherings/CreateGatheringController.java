package com.group.p2_socializer.CreateGatherings;

import com.group.p2_socializer.Calendar.Event;
import com.group.p2_socializer.Database.EventDB;
import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.Pages.GatheringPageController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateGatheringController {


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
    public JFXButton goBack;

    private String eventName;
    private String formattedDate;
    private String eventOrganiser;
    private String eventDescription;
    private String eventCity;
    private String eventCountry;
    private LocalDateTime localDateTime;
    private String timeZone;

    //  Hmmmm bad name for method fix
    public void getDataFromUserForm() throws SQLException {
        Gathering newGathering = new Gathering();
        newGathering.eventName = eventNameTextField.getText();
        newGathering.eventCity = eventCityTextField.getText();
        newGathering.eventCountry = eventCountryTextField.getText();
        newGathering.eventDescription = eventDescriptionTextArea.getText();
        newGathering.eventOrganiser = eventOrganiserTextField.getText();
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        // get time user input
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);
        // combine localDate and localTime into localDateTime
        newGathering.localDateTime = localDateTime;
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        // convert localDateTime into ZonedDateTime
        newGathering.zonedDatetime = zonedDateTime;
        newGathering.timeZone = ZoneId.systemDefault();
        GatheringDB.storeGathering(newGathering);
        //TODO: fix
        //TODO: Fix
        //CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);

        //Switch to Calendar tab
        //mainTabPane.getSelectionModel().select(calendarTab);
    }


    @FXML
    public void handleCreateCustomGathering() throws SQLException {
        getDataFromUserForm();

        String createdMessage = "Gathering Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
        //CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);
    }


    @FXML
    public void handleCreateEventGathering() throws SQLException, IOException {
        getDataFromUserForm();
        String createdMessage = "Gathering Created!";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
        CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);
    }

    @FXML
    public void handleCreatePredefinedGathering() throws SQLException, IOException {

        getDataFromUserForm();

        String createdMessage = "Gathering Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);

        //CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);


        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);


    }
}
