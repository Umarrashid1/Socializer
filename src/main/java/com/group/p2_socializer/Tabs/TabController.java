package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.CreateGatherings.CreateGatheringController;
import com.group.p2_socializer.UserLogIn.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.*;

public class TabController implements Initializable  {
    ZonedDateTime dateFocus;
    ZonedDateTime today;
    @FXML
    TabPane mainTabPane;
    @FXML
    private Tab homeTab;
    @FXML
    Tab calendarTab;
    @FXML
    private Tab trendingTab;
    @FXML
    private Tab groupsTab;
    @FXML
    public Tab myProfileTab;
    @FXML
    public Tab createGatheringTab;
    private Parent fxmlParent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    private void loadPage(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/" + name + ".fxml"));
            AnchorPane newPane = loader.load();
            if(name == "profile_page"){
                ProfileTabController controller = loader.getController();
                // Set data in the controller
                User user = (User) mainTabPane.getScene().getWindow().getUserData();
                controller.setUser(user);
            }
            if(name == "event_calendar_tab"){
                EventCalendarTabController controller = loader.getController();
                controller.setOuterController(this);
            }
            mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);
            // Reset the anchors
            AnchorPane.setBottomAnchor(newPane, 0.0);
            AnchorPane.setLeftAnchor(newPane, 0.0);
            AnchorPane.setRightAnchor(newPane, 0.0);
            AnchorPane.setTopAnchor(newPane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<Tab, Boolean> tabVisitedMap = new HashMap<>();

    public void detectTab(Event event) {
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (!tabVisitedMap.getOrDefault(newTab, false)) {
                tabVisitedMap.put(newTab, true);
                if(newTab == homeTab){
                    loadPage("choose_gathering");
                } else if(newTab == calendarTab) {
                    loadPage("event_calendar_tab");
                } else if (newTab == trendingTab) {
                    loadPage("choose_gathering");
                } else if (newTab == groupsTab){
                    loadPage("discover_tab");
                } else if (newTab == myProfileTab){
                    loadPage("profile_page");
                } else if (newTab == createGatheringTab) {
                    loadPage("choose_gathering");
                }
            }
        });
    }


    public Stage getStage(){
        Stage stage = (Stage) mainTabPane.getScene().getWindow();
        return stage;
    }

    public void clearedChooseGatheringTab(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/create_custom_gathering.fxml"));
        AnchorPane newPane = null;
        try {
            newPane = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CreateGatheringController controller = loader.getController();
        mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);
        // Reset the anchors
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);
        AnchorPane.setTopAnchor(newPane, 0.0);

    }
}

