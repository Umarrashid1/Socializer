package com.group.p2_socializer.Pages;

import com.group.p2_socializer.Utils.ManagerBarController;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.activities.Tag;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @FXML
    public HBox manageEventBar;

    @FXML HBox organiserHBox;
    @FXML
    public Label byLabel;
    @FXML
    public Label eventOrganiserLabel;
    @FXML
    public VBox postList;
    @FXML
    public JFXButton interestedButton;
    @FXML
    public JFXButton attendEventButton;
    public Map<Tab, Boolean> tabUpdateMap;

    private TabPane mainTabPane;

    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }



    //TODO: Split into additional methods
    public void loadEventPage(Event newEvent) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'AT' HH:mm", Locale.ENGLISH);
            ZonedDateTime zonedDateTime = newEvent.getLocalDateTime().atZone(newEvent.getTimeZone());
            String dateString = zonedDateTime.format(formatter).toUpperCase();

            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/event_page.fxml"));
            Parent root = fxmlLoader.load();

            // Get reference to centerPane FXML file
            ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPane");
            AnchorPane centerPane = (AnchorPane) scrollPane.getContent();
            VBox eventInfoVBox = (VBox) centerPane.lookup("#eventInfoVBox");
            HBox organiserHBox = (HBox) eventInfoVBox.lookup("#organiserHBox");
            organiserHBox.setMaxWidth(250);

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
            eventDescriptionLabel.setMaxWidth(300);
            eventDescriptionLabel.setWrapText(true);
            eventDescriptionLabel.setFont(Font.font("Arial", 13));
            eventDescriptionLabel.setPadding(new Insets(0,0,15,0));

            VBox descriptionVBox = new VBox(eventDescriptionLabel);
            descriptionVBox.setLayoutX(36);
            descriptionVBox.setLayoutY(460);

            Line line = new Line();
            double lineLength = 300;
            line.setStrokeWidth(2);
            line.setStroke(Color.GRAY);

            // Bind the start and end points of the Line to VBox properties
            line.startXProperty().bind(descriptionVBox.layoutXProperty().add(descriptionVBox.widthProperty().divide(2).subtract(lineLength / 2)));
            line.startYProperty().bind(descriptionVBox.layoutYProperty().add(descriptionVBox.heightProperty().divide(2)));
            line.endXProperty().bind(line.startXProperty().add(lineLength));
            line.endYProperty().bind(line.startYProperty());
            descriptionVBox.getChildren().add(line);

            //-----------------------------------------------------------------------
            newEvent.getTags();
            List<Tag> taglist = newEvent.getTags();

            //-------------------------------------------------------------------------------

            // Create a GridPane to hold the labels for the words
            GridPane wordsGridPane = new GridPane();
            wordsGridPane.setHgap(10);
            wordsGridPane.setVgap(10);
            wordsGridPane.setPadding(new Insets(10));

            // Create a CornerRadii object to specify the corner radii of the rounded box
            CornerRadii cornerRadii = new CornerRadii(10);

            // Create a BackgroundFill object to specify the background fill of the rounded box
            BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTGRAY, cornerRadii, null);

            // Create a Background object with the BackgroundFill
            Background background = new Background(backgroundFill);

            int column = 0;
            int row = 0;
            for (Tag tag : taglist) {
                Label tagLabel = new Label(tag.getTag());
                tagLabel.setBackground(background); // Set the background of the label to the rounded box
                wordsGridPane.add(tagLabel, column, row); // Add each label to the GridPane

                // Increment column and row counters
                column++;
                if (column == 3) {
                    // Max 3 labels per row, move to next row
                    column = 0;
                    row++;
                }
            }

            // Add the GridPane with the labels to the descriptionHBox
            descriptionVBox.getChildren().add(wordsGridPane);


            // Load the manager_bar.fxml file
            FXMLLoader fxmlLoader1 = new FXMLLoader(EventPageController.class.getResource("/com/group/p2_socializer/manager_bar.fxml"));
            Parent managerBarRoot = fxmlLoader1.load();

            ManagerBarController managerBarController = fxmlLoader1.getController(); // Get reference to actual instance of ManagerBarController
            managerBarController.setMainTabPane(mainTabPane);
            managerBarController.setTabUpdateMap(tabUpdateMap);
            managerBarController.setNewEvent(newEvent);

            boolean isGathering;
            isGathering = false;
            managerBarController.setCancelButton(isGathering);

            centerPane.getChildren().add(managerBarRoot);
            centerPane.getChildren().add(descriptionVBox);

            // Create a new stage and set the new scene
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.setTitle(newEvent.getActivityName());

            //VBox postList = (VBox) root.lookup("#postList");
            //postList.setMaxWidth(Double.MAX_VALUE);
            //postList.setMaxHeight(Double.MAX_VALUE);


            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static class LabelListCell extends ListCell<Label> {
        @Override
        protected void updateItem(Label item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(item);
            }
        }
    }

}