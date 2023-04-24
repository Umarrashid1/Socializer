package com.group.p2_socializer.Utils;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Pages.EventPageController;
import com.group.p2_socializer.Tabs.TabController;
import com.group.p2_socializer.activities.Event;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerBarController extends EventPageController implements Initializable {
    @FXML
    private JFXButton editEventButton;
    @FXML
    private JFXButton postNewsButton;
    @FXML
    private JFXButton cancelEventButton;
    @FXML
    private HBox manageEventBar;
    private Event newEvent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Access the newEvent parameter within the initialize method
        //postList.setMaxWidth(Double.MAX_VALUE);
        //postList.setMaxHeight(Double.MAX_VALUE);
        //JFXButton postNewsButton = (JFXButton) manageEventBar.lookup("#postNewsButton");
        /*postNewsButton.setOnMouseClicked((MouseEvent event) -> {
            handlePostNewsButton(postList);
        });*/

        //JFXButton cancelEventButton = (JFXButton) manageEventBar.lookup("#cancelEventButton");

        cancelEventButton.setOnMouseClicked((MouseEvent event) -> {

            try {
                handleCancelEventButton(event, newEvent);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setNewEvent(Event newEvent) {
        this.newEvent = newEvent;
    }
    public void handleCancelEventButton(MouseEvent event, Event newEvent) throws SQLException {
        ActivityDB.deleteEvent(newEvent.getActivityID());
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    public void handlePostNewsButton(VBox postList) {
        //if (!isWindowOpen) {
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
                    postList.setSpacing(30);

                    postList.getChildren().add(postLabel);

                }


                // Close the new window
                newWindow.close();
                String createdMessage = "Post submitted!";
                PopUpMessage popUpMessage = new PopUpMessage();
                popUpMessage.showCreatedPopUp(createdMessage);

                //isWindowOpen = false;
            });

            postVBox.getChildren().add(submitButton);

            // Show the new window
            Scene scene = new Scene(postVBox, 400, 280);
            newWindow.setScene(scene);
            newWindow.setAlwaysOnTop(true);
            newWindow.show();
            //isWindowOpen = true;

       // }
    }


}
