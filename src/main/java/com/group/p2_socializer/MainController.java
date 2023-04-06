package com.group.p2_socializer;

import com.group.p2_socializer.Calendar.CalendarActivity;
import com.group.p2_socializer.EventPage.EventPageController;
import com.group.p2_socializer.Tabs.EventCalendarTabController;
import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
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