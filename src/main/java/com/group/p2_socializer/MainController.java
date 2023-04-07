package com.group.p2_socializer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;


import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;

public class MainController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private FlowPane calendar;
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
    private Tab homeTab;
    @FXML
    private Tab calendarTab;
    @FXML
    private Tab trendingTab;
    @FXML
    private Tab groupsTab;
    @FXML
    public Tab myProfileTab;
    @FXML
    public Tab createGatheringTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private void loadPage(String name) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/" + name + ".fxml"));
            AnchorPane newPane = loader.load();
            mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);
            // Reset the anchors
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setTopAnchor(newPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void detectTab(Event event) {
            mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                if(newTab == homeTab){
                    loadPage("choose_gathering");
                }else if(newTab == calendarTab) {
                    loadPage("event_calendar_tab");
                }else if (newTab == trendingTab) {
                    loadPage("choose_gathering");
                }else if (newTab == groupsTab){
                    loadPage("choose_gathering");
                }else if (newTab == myProfileTab){
                    loadPage("choose_gathering");
                }else if (newTab == createGatheringTab)
                    loadPage("choose_gathering");
            });
    }
}