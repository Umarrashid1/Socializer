package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Pages.EventPageController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.activities.Event;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;

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
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        // get time user input
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);
        // combine localDate and localTime into localDateTime

        Event event = (Event) new Event.Builder()
                .activityType("EventActivity")
                .activityName(eventNameTextField.getText())
                .activityDescription(eventDescriptionTextArea.getText())
                .activityCity(eventCityTextField.getText())
                .activityCountry(eventCountryTextField.getText())
                .activityOrganiser(eventOrganiserTextField.getText())
                .localDateTime(localDateTime)
                .timeZone(ZoneId.systemDefault())
                .activityType("Event")
                .build();

        ActivityDB.storeEvent(event);

        //Switch to Calendar tab
        mainTabPane.getSelectionModel().select(calendarTab);

        EventPageController controller = new EventPageController();
        controller.loadEventPage(event);

        String createdMessage = "Event Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);


    }
}
