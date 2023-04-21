package com.group.p2_socializer.CreateGatherings;

import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.Pages.GatheringPageController;
import com.group.p2_socializer.Tabs.ChooseGatheringTabController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Gathering;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;


public class CreateGatheringController extends ChooseGatheringTabController implements Initializable {


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

    public void setEventData(Event event){
        eventNameTextField.setText(event.getActivityName());
        eventDescriptionTextArea.setText(event.getActivityDescription());
        eventCityTextField.setText(event.getActivityCity());
        eventCountryTextField.setText(event.getActivityCountry());
        eventOrganiserTextField.setText(event.getActivityOrganiser());
        //eventTimeTextField.setText();
        //eventDatePicker.setChronology();
    }

    public void setDistortion(){
        eventNameTextField.setText("distortion");
    }
    public void handleCreateGathering(ActionEvent actionEvent) throws SQLException, IOException {
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
        String createdMessage = "Gathering Created!";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(newGathering);
        tabUpdateMap.put(discoverTab, true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
