package com.group.p2_socializer.EventPage;

import com.group.p2_socializer.Calendar.CalendarController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

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
    private VBox vBoxPostNews;
    @FXML
    private JFXButton postNewsButton;
    @FXML
    private VBox postList;


    public void loadEventPage(String eventName, String eventDate, String eventOrganiser, String eventDescription, String eventCity, String eventCountry) {
        try {

            //TODO: Set max size and enable text wrap for every label and text

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/event_page.fxml"));
            Parent root = loader.load();





            // Get reference to centerPane FXML file
            ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPane");
            AnchorPane centerPane = (AnchorPane) scrollPane.getContent();
            VBox eventInfoVBox = (VBox) centerPane.lookup("#eventInfoVBox");
            HBox organiserHBox = (HBox) eventInfoVBox.lookup("#organiserHBox");

            //Label eventDateLabel = new Label(eventDate);
            Label eventDateLabel = (Label) eventInfoVBox.lookup("#eventDateLabel");
            eventDateLabel.setText(eventDate);
            eventDateLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #797878;");


            Label eventTitleLabel = (Label) eventInfoVBox.lookup("#eventTitleLabel" );
            eventTitleLabel.setText(eventName);
            eventTitleLabel.setFont(Font.font("Eras Bold ITC", 30));

            Label eventLocation =  (Label) eventInfoVBox.lookup("#eventLocationLabel");
            eventLocation.setText(eventCity + ", " + eventCountry);
            eventLocation.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");

            Label byLabel = (Label) organiserHBox.lookup("#byLabel");
            byLabel.setText("by ");
            byLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");


            Label eventOrganiserLabel = (Label) organiserHBox.lookup("#eventOrganiserLabel");
            eventOrganiserLabel.setText(eventOrganiser);
            eventOrganiserLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #000000;");

            Label eventDescriptionLabel = new Label(eventDescription);
            eventDescriptionLabel.setLayoutX(36);
            eventDescriptionLabel.setLayoutY(460);
            eventDescriptionLabel.setFont(Font.font("Arial", 13));

            // Add the label to the center pane
            centerPane.getChildren().add(eventInfoVBox);
            centerPane.getChildren().add(eventDescriptionLabel);

            // Create a new stage and set the new scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(eventName);

            // Show the new stage
            newStage.show();

            // Get reference from FXML
            VBox postList = (VBox) root.lookup("#postList");
            Button postNewsButton = (Button) root.lookup("#postNewsButton");

            postNewsButton.setOnMouseClicked((MouseEvent event) -> {
                handlePostNewsButton(postList);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO: Save news in DB and make them load on event page.
    public void handlePostNewsButton(VBox postList){
        Stage newWindow = new Stage();
        newWindow.setTitle("New Post");

        // Create a layout for the new window
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);


        // Add a text field for the user to enter their post
        JFXTextArea JFXPostArea = new JFXTextArea();
        JFXPostArea.setPromptText("new post text goes in here");
        JFXPostArea.setPadding(new Insets(30));

        layout.getChildren().add(JFXPostArea);

        // Add a button to submit the post
        JFXButton submitButton = new JFXButton("Submit");
        submitButton.setPrefWidth(200);
        submitButton.setPrefHeight(50);
        submitButton.setStyle("-fx-background-color: #00ff00;");


        submitButton.setOnAction(e -> {

            // Get the text from the text field and add it to the event page
            String postText = JFXPostArea.getText();
            Label postLabel = new Label(postText);
            postList.getChildren().add(postLabel);
            // Close the new window
            newWindow.close();

            String createdMessage = "Post submitted!";
            CalendarController calendarController = new CalendarController();
            calendarController.showEventCreatedMessage(createdMessage);

        });

        layout.getChildren().add(submitButton);

        // Show the new window
        Scene scene = new Scene(layout, 400, 280);
        newWindow.setScene(scene);
        newWindow.show();

    }

}