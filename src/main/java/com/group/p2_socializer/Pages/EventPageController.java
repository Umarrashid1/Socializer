package com.group.p2_socializer.Pages;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Utils.ManagerBarController;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;

public class EventPageController {
    @FXML
    public AnchorPane centerPane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox eventInfoVBox;
    @FXML
    public Label eventDateLabel;
    @FXML
    public Label eventTitleLabel;
    @FXML
    public Label eventLocationLabel;
    @FXML HBox organiserHBox;
    @FXML
    public Label byLabel;
    @FXML
    public Label eventOrganiserLabel;
    @FXML
    public VBox postList;
    public Map<Tab, Boolean> tabUpdateMap;

    private TabPane mainTabPane;

    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }



    public void loadEventPage(Event newEvent) {
        try {
            //TODO: Set max size and enable text wrap for every label and text
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'AT' HH:mm", Locale.ENGLISH);
            ZonedDateTime zonedDateTime = newEvent.getLocalDateTime().atZone(newEvent.getTimeZone());
            String dateString = zonedDateTime.format(formatter).toUpperCase();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/event_page.fxml"));
            Parent root = loader.load();
            // Get reference to centerPane FXML file
            ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPane");
            AnchorPane centerPane = (AnchorPane) scrollPane.getContent();
            VBox eventInfoVBox = (VBox) centerPane.lookup("#eventInfoVBox");
            HBox organiserHBox = (HBox) eventInfoVBox.lookup("#organiserHBox");
            organiserHBox.setMaxWidth(250);

            //Label eventDateLabel = new Label(eventDate);
            Label eventDateLabel = (Label) eventInfoVBox.lookup("#eventDateLabel");
            eventDateLabel.setText(dateString);
            eventDateLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #797878;");


            Label eventTitleLabel = (Label) eventInfoVBox.lookup("#eventTitleLabel" );
            eventTitleLabel.setText(newEvent.getActivityName());
            eventTitleLabel.setFont(Font.font("Eras Bold ITC", 30));

            Label eventLocation =  (Label) eventInfoVBox.lookup("#eventLocationLabel");
            eventLocation.setText(newEvent.getActivityCity() + ", " + newEvent.getActivityCountry());
            eventLocation.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");

            Label byLabel = (Label) organiserHBox.lookup("#byLabel");
            byLabel.setText("by ");
            byLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");


            Label eventOrganiserLabel = (Label) organiserHBox.lookup("#eventOrganiserLabel");
            eventOrganiserLabel.setText(newEvent.getActivityOrganiser());
            eventOrganiserLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #000000;");

            Label eventDescriptionLabel = new Label(newEvent.getActivityDescription());
            eventDescriptionLabel.setLayoutX(36);
            eventDescriptionLabel.setLayoutY(460);
            eventDescriptionLabel.setFont(Font.font("Arial", 13));

            // Add the label to the center pane
            centerPane.getChildren().add(eventInfoVBox);
            centerPane.getChildren().add(eventDescriptionLabel);


            // Load the manager_bar.fxml file
            FXMLLoader loader1 = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/manager_bar.fxml"));
            ManagerBarController controller = loader1.getController();
            Parent managerBarRoot = loader1.load();

            ManagerBarController managerBarController = loader1.getController(); // Get reference to actual instance of ManagerBarController
            managerBarController.setMainTabPane(mainTabPane);
            managerBarController.setTabUpdateMap(tabUpdateMap);
            managerBarController.setNewEvent(newEvent);
            managerBarController.setCancelEventButton();

            centerPane.getChildren().add(managerBarRoot);


            // Create a new stage and set the new scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(newEvent.getActivityName());

            // Show the new stage
            newStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        isWindowOpen = true;

    }
    //TODO: Save news in DB and make them load on event page.
    private boolean isWindowOpen = false;

    //wakckck




}