package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.CreateGatherings.CreateGatheringController;
import com.group.p2_socializer.UserLogIn.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    public TabPane mainTabPane;
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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

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

            if(name == "discoverTab"){
                DiscoverTabController discoverTabController = loader.getController();
                discoverTabController.loadGatheringItems();
                //temporary fix
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
        if(newTab == homeTab) {
            tabUpdateMap.put(discoverTab, true);
        }
        if (tabUpdateMap.getOrDefault(newTab, true)) {
            tabUpdateMap.put(newTab, false);
            if(newTab == homeTab){
                loadPage("choose_gathering");
                tabUpdateMap.put(discoverTab, true);
            } else if(newTab == calendarTab) {
                loadPage("calendarTab");
            } else if (newTab == trendingTab) {
                loadPage("choose_gathering");
            } else if (newTab == discoverTab){
                loadPage("discoverTab");
            } else if (newTab == myProfileTab){
                loadPage("profile_page");
            } else if (newTab == createGatheringTab) {
                loadPage("choose_gathering");
            }
        }
    }
    public void setMainTabPane(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }
    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setCurrentUser(User user){
        this.currentUser = user;
    }
}

