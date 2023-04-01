package com.group.p2_socializer.EventPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class EventPageController {
    @FXML
    public AnchorPane centerPane;
    private Label eventTitle;
    private Label eventDate;

    public void loadEventPage(MouseEvent event, String eventName, String eventDate, String eventOrganiser, String eventDescription, String eventCity, String eventCountry) {
        try {

            //TODO: Set max size and enable text wrap for every label and text

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/event_page.fxml"));
            Parent root = loader.load();

            Label eventDateLabel = new Label(eventDate);
            eventDateLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #797878;");
            eventDateLabel.setLayoutX(30);
            eventDateLabel.setLayoutY(210);

            Label eventTitleLabel = new Label(eventName + ": ");
            eventTitleLabel.setLayoutX(30.0);
            eventTitleLabel.setLayoutY(225);
            eventTitleLabel.setFont(Font.font("Eras Bold ITC", 30));

            Label eventLocation = new Label( eventCity + ", " + eventCountry);
            eventLocation.setLayoutX(30);
            eventLocation.setLayoutY(260);
            eventLocation.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");


            Label eventOrganiserLabel = new Label("by " + eventOrganiser);
            eventOrganiserLabel.setLayoutX(30);
            eventOrganiserLabel.setLayoutY(275);
            eventOrganiserLabel.setStyle("-fx-font-family: 'Eras ITC'; -fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 15; -fx-text-fill: #67f3ff;");



            Label eventDescriptionLabel = new Label(eventDescription);
            eventDescriptionLabel.setLayoutX(32);
            eventDescriptionLabel.setLayoutY(295);
            eventDescriptionLabel.setFont(Font.font("Arial", 13));

            // Get reference to centerPane FXML file
            AnchorPane centerPane = (AnchorPane) root.lookup("#centerPane");

            // Add the label to the center pane
            centerPane.getChildren().add(eventDateLabel);
            centerPane.getChildren().add(eventTitleLabel);
            centerPane.getChildren().add(eventOrganiserLabel);
            centerPane.getChildren().add(eventLocation);
            centerPane.getChildren().add(eventDescriptionLabel);



            // Get the current window's stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new stage and set the new scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));

            // Show the new stage, close the current stage
            newStage.show();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}