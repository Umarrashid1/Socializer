package com.group.p2_socializer.Pages;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Tabs.EventCalendarTabController;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventPageController{
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
    private JFXButton cancelEventButton;
    @FXML
    private VBox postList;


    public void handleCancelEventButton(MouseEvent event, Event newEvent) throws SQLException {
        ActivityDB.deleteEvent(newEvent.getActivityID());
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
        //tabUpdateMap.put(calendarTab, true);


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


            // Create a new stage and set the new scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(newEvent.getActivityName());

            //addManageEventBar();

            // Show the new stage
            newStage.show();

            // Get reference from FXML
            VBox postList = (VBox) root.lookup("#postList");
            postList.setMaxWidth(Double.MAX_VALUE);
            postList.setMaxHeight(Double.MAX_VALUE);
            JFXButton postNewsButton = (JFXButton) root.lookup("#postNewsButton");
            postNewsButton.setOnMouseClicked((MouseEvent event) -> {
                handlePostNewsButton(postList);
            });

            JFXButton cancelEventButton = (JFXButton) root.lookup("#cancelEventButton");
            cancelEventButton.setOnMouseClicked((MouseEvent event) -> {

                try {
                    handleCancelEventButton(event,newEvent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addManageEventBar() {
        // Create HBox
        HBox manageEventBar = new HBox();
        manageEventBar.setLayoutX(28.0);
        manageEventBar.setLayoutY(-1.0);

        // Create FontAwesomeIconView
        FontAwesomeIconView wrenchIcon = new FontAwesomeIconView();
        wrenchIcon.setGlyphName("WRENCH");
        wrenchIcon.setSize("20");
        HBox.setMargin(wrenchIcon, new Insets(0, 0, 5, 30)); // Set margin using Insets

        // Create Label
        Label manageEventLabel = new Label("MANAGE EVENT:");
        manageEventLabel.setPrefHeight(29.0);
        manageEventLabel.setPrefWidth(101.0);
        manageEventLabel.setUnderline(true);
        manageEventLabel.setFont(new Font("System Bold", 12.0));

        // Create JFXButtons
        JFXButton editEventButton = new JFXButton("Edit event");
        editEventButton.setPrefHeight(29.0);
        editEventButton.setPrefWidth(78.0);

        JFXButton postNewsButton = new JFXButton("Post news");
        postNewsButton.setPrefHeight(29.0);
        postNewsButton.setPrefWidth(78.0);
        postNewsButton.setId("postNewsButton"); // Set fx:id for the button


        JFXButton manageGroupsButton = new JFXButton("Manage groups");
        manageGroupsButton.setPrefHeight(29.0);
        manageGroupsButton.setPrefWidth(104.0);

        JFXButton cancelEventButton = new JFXButton("Cancel event");
        cancelEventButton.setPrefHeight(29.0);
        cancelEventButton.setPrefWidth(91.0);
        cancelEventButton.setId("cancelEventButton"); // Set fx:id for the button

        // Add children to HBox
        manageEventBar.getChildren().addAll(wrenchIcon, manageEventLabel, editEventButton, postNewsButton, manageGroupsButton, cancelEventButton);

        // Set padding for HBox
        manageEventBar.setPadding(new Insets(0, 0, 1, 0));
    }

    //TODO: Save news in DB and make them load on event page.
    private boolean isWindowOpen = false;

    public void handlePostNewsButton(VBox postList) {
        if (!isWindowOpen) {
            Stage newWindow = new Stage();
            newWindow.setTitle("New Post");

            // Create a layout for the new window

            VBox postVBox = new VBox(10);
            postVBox.setAlignment(Pos.CENTER);

            // Add a text field for the user to enter their post
            JFXTextArea JFXPostArea = new JFXTextArea();
            JFXPostArea.setPromptText("new post text goes in here");
            JFXPostArea.setPadding(new Insets(30));

            postVBox.getChildren().add(JFXPostArea);
            // Add a button to submit the post
            JFXButton submitButton = new JFXButton("Submit");
            submitButton.setPrefWidth(200);
            submitButton.setPrefHeight(50);
            submitButton.setStyle("-fx-background-color: #00ff00;");
            submitButton.setOnAction(e -> {

                // Get the text from the text field and add it to the event page
                String postText = JFXPostArea.getText();

                for (int i = 0; i < 1; i++){
                    Label postLabel = new Label(postText);
                    postLabel.setWrapText(true);
                    postLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                    postLabel.setMaxWidth(Double.MAX_VALUE);
                    postLabel.setMaxHeight(Double.MAX_VALUE);
                    VBox.setVgrow(postLabel, Priority.ALWAYS);
                    postList.setPadding(new Insets(5, 0, 5, 36));
                    postList.getChildren().add(postLabel);
                    postList.setSpacing(30);

                }


                // Close the new window
                newWindow.close();
                String createdMessage = "Post submitted!";
                PopUpMessage popUpMessage = new PopUpMessage();
                popUpMessage.showCreatedPopUp(createdMessage);

                isWindowOpen = false;
            });

            postVBox.getChildren().add(submitButton);

            // Show the new window
            Scene scene = new Scene(postVBox, 400, 280);
            newWindow.setScene(scene);
            newWindow.setAlwaysOnTop(true);
            newWindow.show();
            isWindowOpen = true;

        }
    }


}