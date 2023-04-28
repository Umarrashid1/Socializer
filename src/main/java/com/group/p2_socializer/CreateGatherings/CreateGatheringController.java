package com.group.p2_socializer.CreateGatherings;

import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.Pages.GatheringPageController;
import com.group.p2_socializer.Tabs.ChooseGatheringTabController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Gathering;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Map;
import java.util.ResourceBundle;


public class CreateGatheringController  extends ChooseGatheringTabController implements Initializable {


    @FXML
    private TextField gatheringNameTextField;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private TextField eventTimeTextField;
    @FXML
    private TextField gatheringCityTextField;
    @FXML
    private TextField gatheringOrganiserTextField;
    @FXML
    private TextField gatheringCountryTextField;
    @FXML
    private TextArea gatheringDescriptionTextArea;
    @FXML
    public JFXTextField minimumParticipantsTextField;
    @FXML
    public JFXTextField maximumParticipantsTextField;
    public Map<Tab, Boolean> actualtabUpdateMap;
    private TabPane mainTabPane;

    public void setEventData(Event event){
        gatheringNameTextField.setText(event.getActivityName());
        gatheringDescriptionTextArea.setText(event.getActivityDescription());
        gatheringCityTextField.setText(event.getActivityCity());
        gatheringCountryTextField.setText(event.getActivityCountry());
        gatheringOrganiserTextField.setText(event.getActivityOrganiser());
        //eventTimeTextField.setText();
        //eventDatePicker.setChronology();
    }

    public void handleCreateGathering(ActionEvent actionEvent) throws SQLException, IOException {
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);

        Gathering newGathering = new Gathering.Builder()
                .activityType("EventActivity")
                .activityName(gatheringNameTextField.getText())
                .activityDescription(gatheringDescriptionTextArea.getText())
                .activityCity(gatheringCityTextField.getText())
                .activityCountry(gatheringCountryTextField.getText())
                .activityOrganiser(gatheringOrganiserTextField.getText())
                .activityMinimumParticipants(Integer.parseInt(minimumParticipantsTextField.getText()))
                .activityMaximumParticipants(Integer.parseInt(maximumParticipantsTextField.getText()))
                .localDateTime(localDateTime)
                .timeZone(ZoneId.systemDefault())
                .build();

        GatheringDB.storeGathering(newGathering);

        String createdMessage = "Gathering Created!";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);

        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.loadGatheringPage(newGathering);

        Tab newTab = mainTabPane.getTabs().get(2);
        this.tabUpdateMap.put(newTab, true);
        mainTabPane.getSelectionModel().select(1);
        mainTabPane.getSelectionModel().select(2);
    }

    public void handleAddTagsButton(){


    }


    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
