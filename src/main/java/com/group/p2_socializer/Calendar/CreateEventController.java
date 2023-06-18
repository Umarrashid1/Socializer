package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.Database.ActivityDB;
import com.group.p2_socializer.Pages.EventPageController;
import com.group.p2_socializer.Tabs.EventCalendarTabController;
import com.group.p2_socializer.Tabs.TabController;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.Activities.Event;
import com.group.p2_socializer.Activities.Tag;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateEventController {

    @FXML
    private JFXButton backButton;
    @FXML
    public JFXButton createNewEventButton;
    @FXML
    public JFXButton addTagsButton;
    @FXML
    public JFXTextField minimumParticipantsTextField;
    @FXML
    public JFXTextField maximumParticipantsTextField;
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

    private JFXTabPane mainTabPane;

    public Map<Tab, Boolean> tabUpdateMap;

    @FXML
    private Tab calendarTab;
    @FXML
    public JFXButton goBack;

    public List<Tag> selectedTagList;
    private User currentUser;

    @FXML
    public void handleBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/" + "calendar_tab" + ".fxml"));
        AnchorPane newPane = loader.load();
        EventCalendarTabController controller;
        controller = loader.getController();
        controller.setUser(currentUser);
        controller.setMainTabPane(mainTabPane);
        controller.setTabUpdateMap(tabUpdateMap);
        controller.showCreateEventButton();


        Node content = mainTabPane.getSelectionModel().getSelectedItem().getContent();
        if (content != null) {
            // Remove the content of the previous tab
            ((AnchorPane) content).getChildren().clear();
            // Release the controller instance
            loader.setController(null);
        }
        // Set the content of the current tab
        mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);

    }

    @FXML
    public void handleCreateEvent() throws SQLException, IOException {
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        // get time user input
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);
        // combine localDate and localTime into localDateTime

        // Create the Event using the Builder pattern
        Event event = new Event();
        event.setActivityType("EventActivity");
        event.setActivityName(eventNameTextField.getText());
        event.setActivityDescription(eventDescriptionTextArea.getText());
        event.setActivityCity(eventCityTextField.getText());
        event.setActivityCountry(eventCountryTextField.getText());
        event.setActivityOrganiser(eventOrganiserTextField.getText());
        event.setLocalDateTime(localDateTime);
        event.setTimeZone(ZoneId.systemDefault());
        event.setActivityID(ActivityDB.storeEvent(event));

        ActivityDB.setTags(selectedTagList, event.getActivityID());

        //Switch to Calendar tab
        Tab newTab = mainTabPane.getTabs().get(3);
        tabUpdateMap.put(newTab, true);
        mainTabPane.getSelectionModel().select(1);
        mainTabPane.getSelectionModel().select(3);

        EventPageController controller = new EventPageController();

        controller.setUser(currentUser);
        controller.loadEventPage(event);

        String createdMessage = "Event Created!";

        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
    }

    public void handleAddTagsButton() {
        Stage stage = new Stage();
        stage.setTitle("Add tags");

        try {
            List<Tag> tagList = ActivityDB.getTags();
            ListView<Label> listView = new ListView<>();
            Map<Label, Tag> labelTagMap = new HashMap<>();
            for (Tag tag : tagList) {
                Label label = new Label(tag.getTag());
                listView.getItems().add(label);
                labelTagMap.put(label, tag);            }
            listView.setPrefHeight(Region.USE_COMPUTED_SIZE);

            JFXButton addTagsButton = new JFXButton("Add tags");
            addTagsButton.setStyle("-fx-background-color: #7FFF5B;");


            HBox rowOfTagsHBox = new HBox();
            rowOfTagsHBox.setSpacing(10);

            VBox tagsVBox = new VBox();
            tagsVBox.setSpacing(10);
            tagsVBox.setAlignment(Pos.CENTER);

            int col = 0;

            for (Label tagLabel : listView.getItems()) {
                tagLabel.setStyle("-fx-background-color: #ccbfbf; -fx-background-radius: 15; -fx-padding: 5;");
                tagLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Label clickedLabel = (Label) event.getSource();
                    if (clickedLabel.getStyle().contains("-fx-background-color: #ccbfbf;")) {
                        clickedLabel.setStyle("-fx-background-color: lightblue; -fx-background-radius: 15; -fx-padding: 5;");
                    } else {
                        clickedLabel.setStyle("-fx-background-color: #ccbfbf; -fx-background-radius: 15; -fx-padding: 5;");
                    }

                });
                rowOfTagsHBox.getChildren().add(tagLabel);
                col++;
                if (col == 3) {
                    tagsVBox.getChildren().add(rowOfTagsHBox);
                    rowOfTagsHBox = new HBox();
                    rowOfTagsHBox.setSpacing(10);
                    col = 0;
                }
            }

            addTagsButton.setOnAction(event -> {
                ObservableList<Label> selectedItems = listView.getItems()
                        .filtered(label -> label.getStyle().contains("-fx-background-color: lightblue;"));

                selectedTagList = new ArrayList<>();

                for (Label label : selectedItems) {
                    Tag tag = labelTagMap.get(label);
                    selectedTagList.add(tag);
                    System.out.println("Selected item: " + label.getText());
                }
                //Stage stage = (Stage) addTagsButton.getScene().getWindow();
                stage.close();

            });

            VBox bottomVBox = new VBox(addTagsButton);
            VBox.setMargin(addTagsButton, new Insets(10, 0, 0, 0));
            bottomVBox.setAlignment(Pos.CENTER);

            BorderPane borderPane = new BorderPane();
            borderPane.setPadding(new Insets(10, 10, 10, 10));
            borderPane.setCenter(tagsVBox);
            borderPane.setBottom(bottomVBox);


            Scene scene = new Scene(borderPane);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(JFXTabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }
    public void setUser(User currentUser){
        this.currentUser = currentUser;
    }
}
