package com.group.p2_socializer.CreateGatherings;

import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.Pages.GatheringPageController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Gathering;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;

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
    public Gathering getDataFromUserForm() throws SQLException {
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);

        Gathering newGathering = (Gathering) new Gathering.Builder()
                .activityType("EventActivity")
                .activityName(eventNameTextField.getText())
                .activityDescription(eventDescriptionTextArea.getText())
                .activityCity(eventCityTextField.getText())
                .activityCountry(eventCountryTextField.getText())
                .activityOrganiser(eventOrganiserTextField.getText())
                .localDateTime(localDateTime)
                .timeZone(ZoneId.systemDefault())
                .build();

        GatheringDB.storeGathering(newGathering);
        //Switch to Calendar tab
        //mainTabPane.getSelectionModel().select(calendarTab);
        return newGathering;
    }


    @FXML
    public void handleCreateCustomGathering() throws SQLException {
        Gathering newGathering = getDataFromUserForm();
        String createdMessage = "Gathering Created!";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
        GatheringDB.storeGathering(newGathering);
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(newGathering);
    }


    @FXML
    public void handleCreateEventGathering() throws SQLException, IOException {
        Gathering newGathering = getDataFromUserForm();
        String createdMessage = "Gathering Created!";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
        GatheringDB.storeGathering(newGathering);
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(newGathering);
    }

    @FXML
    public void handleCreatePredefinedGathering() throws SQLException, IOException {

        Gathering newGathering = getDataFromUserForm();

        String createdMessage = "Gathering Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);

        //CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone)
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(newGathering);


    }
}
