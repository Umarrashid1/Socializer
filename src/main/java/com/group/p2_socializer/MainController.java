package com.group.p2_socializer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;


import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.*;

public class MainController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
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
/* // temporary fix for memory issue
    private Map<String, FXMLLoader> fxmlLoaders = new HashMap<>();

    private FXMLLoader getFXMLLoader(String name) {
        FXMLLoader loader = fxmlLoaders.get(name);
        if (loader == null) {
            loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/" + name + ".fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fxmlLoaders.put(name, loader);
        }
        return loader;
    }



    private void loadPage(String name) {
        if (mainTabPane != null) {
            mainTabPane.getSelectionModel().getSelectedItem().setContent(null);
        }

        FXMLLoader loader = getFXMLLoader(name);
        AnchorPane newPane = loader.getRoot();

        mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);
        // Reset the anchors
        AnchorPane.setBottomAnchor(newPane, 0.0);
        AnchorPane.setLeftAnchor(newPane, 0.0);
        AnchorPane.setRightAnchor(newPane, 0.0);
        AnchorPane.setTopAnchor(newPane, 0.0);
    }

}
 */
    private void loadPage(String name) {

        try {
            if (mainTabPane != null) {
                mainTabPane.getSelectionModel().getSelectedItem().setContent(null); //did not fix performance problem
            }

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
                    loadPage("discover_tab");
                }else if (newTab == myProfileTab){
                    loadPage("choose_gathering");
                }else if (newTab == createGatheringTab)
                    loadPage("choose_gathering");
            });
    }
}
