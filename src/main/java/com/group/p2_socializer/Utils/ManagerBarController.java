package com.group.p2_socializer.Utils;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Gathering;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class ManagerBarController implements Initializable {
    @FXML
    private JFXButton editEventButton;
    @FXML
    private JFXButton postNewsButton;
    @FXML
    private JFXButton cancelEventButton;
    @FXML
    private HBox manageEventBar;
    private Event newEvent;
    private Gathering newGathering;
    public Map<Tab, Boolean> tabUpdateMap;

    private TabPane mainTabPane;

    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(TabPane mainTabPane){this.mainTabPane = mainTabPane;}

    public void setCancelButton(boolean isGathering) throws SQLException {

        if (!isGathering){
            cancelEventButton.setOnMouseClicked((MouseEvent event) -> {

                try {
                    handleCancelEventButton(event, newEvent);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            cancelEventButton.setOnMouseClicked((MouseEvent event) -> {
                try {
                    handleCancelGatheringButton(event, newGathering);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    public void setNewEvent(Event newEvent) {
        this.newEvent = newEvent;
    }
    public void setNewGathering(Gathering newGathering) {
        this.newGathering = newGathering;
    }

    public void handleCancelEventButton(MouseEvent event, Event newEvent) throws SQLException {
        ActivityDB.deleteEvent(newEvent.getActivityID());
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
        Tab newTab = mainTabPane.getTabs().get(3);
        tabUpdateMap.put(newTab, true);
        mainTabPane.getSelectionModel().select(1);
        mainTabPane.getSelectionModel().select(3);
    }
    public void handleCancelGatheringButton(MouseEvent event, Gathering newGathering) throws SQLException {
        GatheringDB.deleteGathering(newGathering.getGatheringID());
        Node node = (Node) event.getSource();
        Scene scene = node.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
        Tab newTab = mainTabPane.getTabs().get(2);
        tabUpdateMap.put(newTab, true);
        mainTabPane.getSelectionModel().select(1);
        mainTabPane.getSelectionModel().select(2);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
