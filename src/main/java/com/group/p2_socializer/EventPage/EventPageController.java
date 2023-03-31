package com.group.p2_socializer.EventPage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EventPageController {
    @FXML
    public AnchorPane centerPane;
    private Label eventTitle;

    public void loadEventPage(MouseEvent event, String eventName) {
        System.out.println(eventName);
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/event_page.fxml"));
            Parent root = loader.load();

            Label eventTitle = new Label(eventName);
            eventTitle.setLayoutX(30.0);
            eventTitle.setLayoutY(221.0);

            // Get reference to centerPane FXML file
            AnchorPane centerPane = (AnchorPane) root.lookup("#centerPane");

            // Add the label to the center pane
            centerPane.getChildren().add(eventTitle);

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