package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.CreateGatherings.CreateGatheringController;
import com.group.p2_socializer.Socializer;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Utils.ScreenUtils;
import com.jfoenix.controls.JFXTabPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

public class TabController  {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    @FXML
    public Tab signOutTab;
    @FXML
    public JFXTabPane mainTabPane;
    @FXML
    private Tab homeTab;
    @FXML
    public Tab calendarTab;
    @FXML
    public Tab trendingTab;
    @FXML
    public Tab discoverTab;
    @FXML
    public Tab myProfileTab;
    @FXML
    public Tab createGatheringTab;

    public User currentUser;
    FXMLLoader loader;


    private void loadPage(String name) {
        try {
            loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/" + name + ".fxml"));
            AnchorPane newPane = loader.load();

            TabController controller = loader.getController();
            controller.setMainTabPane(mainTabPane);
            controller.setTabUpdateMap(tabUpdateMap);
            controller.setCurrentUser(currentUser);

            if(name == "profile_page"){
                ProfileTabController profileTabController = loader.getController();
                profileTabController.setUser(currentUser);
                //temporary fix
            }

            if(name == "choose_gathering"){
                ChooseGatheringTabController ChooseGatheringTabController = loader.getController();
                ChooseGatheringTabController.setUser(currentUser);
                //temporary fix
            }

            if(name == "discover_tab"){
                DiscoverTabController discoverTabController = loader.getController();
                discoverTabController.setCurrentUser(currentUser);
                discoverTabController.loadGatheringItems();
                //temporary fix
            }

            if(name == "calendar_tab"){
                EventCalendarTabController eventCalendarTabController = loader.getController();
                eventCalendarTabController.setUser(currentUser);
                eventCalendarTabController.showCreateEventButton();
            }

            // Get the content of the current tab
            Node content = mainTabPane.getSelectionModel().getSelectedItem().getContent();
            if (content != null) {
                // Remove the content of the previous tab
                ((AnchorPane) content).getChildren().clear();
                // Release the controller instance
                loader.setController(null);
            }
            // Set the content of the current tab
            mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public Map<Tab, Boolean> tabUpdateMap = new HashMap<>();

    public void detectTab(Event event) {
        Tab newTab = mainTabPane.getSelectionModel().getSelectedItem();
        if (tabUpdateMap.getOrDefault(newTab, true)) {
            tabUpdateMap.put(newTab, false);
            if(newTab == homeTab){

            } else if(newTab == calendarTab) {
                loadPage("calendar_tab");
                //tabUpdateMap.put(calendarTab, true);
            } else if (newTab == trendingTab) {
                //loadPage("");
            } else if (newTab == discoverTab){
                loadPage("discover_tab");
            } else if (newTab == myProfileTab){
                loadPage("profile_page");
            } else if (newTab == createGatheringTab) {
                loadPage("choose_gathering");
                tabUpdateMap.put(createGatheringTab, true);
            }
        }
    }
    public void setMainTabPane(JFXTabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }
    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public void signOut() throws IOException {
        Node node = mainTabPane;
        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.close();

        FXMLLoader loginFxmlLoader = new FXMLLoader(Socializer.class.getResource("login_page.fxml"));
        Scene scene = new Scene(loginFxmlLoader.load());

        Stage mainStage = new Stage();
        mainStage.setTitle("Socializer");
        mainStage.setScene(scene);
        mainStage.show();
        double centerX = ScreenUtils.getScreenCenterX() - mainStage.getWidth() / 2;
        double centerY = ScreenUtils.getScreenCenterY() - mainStage.getHeight() / 2;
        mainStage.setX(centerX);
        mainStage.setY(centerY);
    }
}

