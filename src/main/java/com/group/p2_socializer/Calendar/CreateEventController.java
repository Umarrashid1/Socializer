package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Pages.EventPageController;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.activities.Event;
import com.group.p2_socializer.activities.Tag;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateEventController {

    @FXML
    public JFXButton createNewEventButton;
    @FXML
    public JFXButton addTagsButton;
    @FXML
    public JFXTextField minimumParticipantsTextField; //---------newly added
    @FXML
    public JFXTextField maximumParticipantsTextField; //---------newly added
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

    private TabPane mainTabPane;

    public Map<Tab, Boolean> tabUpdateMap;

    @FXML
    private Tab calendarTab;
    @FXML
    public JFXButton goBack;
    ObservableList<Tag> selectedItems;

    @FXML
    public void handleCreateEvent() throws SQLException, IOException {
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        // get time user input
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);
        // combine localDate and localTime into localDateTime

        Event event = new Event.Builder()
                .activityType("EventActivity")
                .activityName(eventNameTextField.getText())
                .activityDescription(eventDescriptionTextArea.getText())
                .activityCity(eventCityTextField.getText())
                .activityCountry(eventCountryTextField.getText())
                .activityOrganiser(eventOrganiserTextField.getText())
                .localDateTime(localDateTime)
                .timeZone(ZoneId.systemDefault())
                .activityType("Event")
                .build();

        event.activityID = ActivityDB.storeEvent(event);
        event.setTags(selectedItems);
        //Switch to Calendar tab
        Tab newTab = mainTabPane.getTabs().get(3);
        tabUpdateMap.put(newTab, true);
        mainTabPane.getSelectionModel().select(1);
        mainTabPane.getSelectionModel().select(3);

        EventPageController controller = new EventPageController();
        controller.loadEventPage(event);

        String createdMessage = "Event Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
    }

    public void handleAddTagsButton() throws SQLException {
        Stage stage = new Stage();
        stage.setTitle("ListView Select Example");
        List<Tag> tagList = ActivityDB.getTags();
        ListView<Tag> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for (Tag tag : tagList) {
            listView.getItems().add(tag);
        }
        listView.setCellFactory(param -> new ListCell<Tag>() {
            @Override
            protected void updateItem(Tag tag, boolean empty) {
                super.updateItem(tag, empty);
                if (empty || tag == null) {
                    setText(null);
                } else {
                    setText(tag.getTag());
                }
            }
        });

        listView.setPrefHeight(Region.USE_COMPUTED_SIZE);

        JFXButton addTagsButton = new JFXButton("Add tags");
        addTagsButton.setStyle("-fx-background-color: #7FFF5B;");


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);


        int row = 0;
        int col = 0;

        for (Label label : listView.getItems()) {
            label.setStyle("-fx-background-color: #ccbfbf; -fx-background-radius: 15; -fx-padding: 5;");
            label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                Label clickedLabel = (Label) event.getSource();
                if (clickedLabel.getStyle().contains("-fx-background-color: #ccbfbf;")) {
                    clickedLabel.setStyle("-fx-background-color: lightblue; -fx-background-radius: 15; -fx-padding: 5;");
                } else {
                    clickedLabel.setStyle("-fx-background-color: #ccbfbf; -fx-background-radius: 15; -fx-padding: 5;");
                }

            });
            gridPane.add(label, col, row);
            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        addTagsButton.setOnAction(event -> {
            ObservableList<Label> selectedItems = listView.getItems()
                    .filtered(label -> label.getStyle().contains("-fx-background-color: lightblue;"));

            for (Label label : selectedItems) {
                System.out.println("Selected item: " + label.getText());
            }
        });

        VBox bottomVBox = new VBox(addTagsButton);
        VBox.setMargin(addTagsButton, new Insets(10, 0, 0, 0));
        bottomVBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10,10,10,10));
        borderPane.setCenter(gridPane);
        borderPane.setBottom(bottomVBox);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }







    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }
}
