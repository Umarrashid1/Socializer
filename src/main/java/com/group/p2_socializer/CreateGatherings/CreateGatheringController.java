package com.group.p2_socializer.CreateGatherings;
import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.Pages.GatheringPageController;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.group.p2_socializer.Activities.Event;
import com.group.p2_socializer.Activities.Gathering;
import com.group.p2_socializer.Activities.Tag;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.util.*;


public class CreateGatheringController {

    public List<Tag> selectedTagList;
    public HBox createGatheringButtonHBox;
    @FXML
    private TextField gatheringNameTextField;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private TextField eventTimeTextField;
    @FXML
    private TextField gatheringCityTextField;
    @FXML
    private TextField gatheringOrganiserTextField;
    @FXML
    private TextField gatheringCountryTextField;
    @FXML
    private TextArea gatheringDescriptionTextArea;
    @FXML
    public JFXTextField minimumParticipantsTextField;
    @FXML
    public JFXTextField maximumParticipantsTextField;
    public Map<Tab, Boolean> tabUpdateMap;
    private JFXTabPane mainTabPane;
    private User currentUser;
    private  String eventName;

    public void setEventData(Event event){
        //gatheringNameTextField.setText(event.getActivityName());
        gatheringDescriptionTextArea.setText(event.getActivityDescription());
        gatheringCityTextField.setText(event.getActivityCity());
        gatheringCountryTextField.setText(event.getActivityCountry());
        //gatheringOrganiserTextField.setText(event.getActivityOrganiser());
        eventTimeTextField.setText(event.getLocalDateTime().toLocalTime().toString());
        eventDatePicker.setValue(event.getLocalDateTime().toLocalDate());

        eventName = event.getActivityName();

    }


    public void handleCustomCreation() throws SQLException, IOException {
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        LocalDateTime localDateTime = LocalDateTime.of(eventDatePicker.getValue(), eventTime);

        Gathering newGathering = new Gathering();
        newGathering.setActivityType("EventActivity");


        if(eventName == null){
            newGathering.setActivityName(gatheringNameTextField.getText());
        }
        else { newGathering.setActivityName(eventName + "//" + gatheringNameTextField.getText());}

        newGathering.setActivityDescription(gatheringDescriptionTextArea.getText());
        newGathering.setActivityCity(gatheringCityTextField.getText());
        newGathering.setActivityCountry(gatheringCountryTextField.getText());
        newGathering.setActivityOrganiser(currentUser.getUsername());
        newGathering.setActivityMinimumParticipants(Integer.parseInt(minimumParticipantsTextField.getText()));
        newGathering.setActivityMaximumParticipants(Integer.parseInt(maximumParticipantsTextField.getText()));
        newGathering.setLocalDateTime(localDateTime);
        newGathering.setTimeZone(ZoneId.systemDefault());
        newGathering.setGatheringID(GatheringDB.storeGathering(newGathering));
        // gatheringID is generated by the database therefore, we need to store the gathering in the database,
        // and then fetch and set the id of the gathering
        newGathering.setGatheringTags(selectedTagList);
        handleCreateGathering(newGathering);
    }




    public void handleCreateGathering(Gathering newGathering) throws SQLException, IOException {
        currentUser.joinGathering(newGathering.getGatheringID());
        String createdMessage = "Gathering Created!";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(createdMessage);
        GatheringPageController gatheringPageController = new GatheringPageController();
        gatheringPageController.setMainTabPane(mainTabPane);
        gatheringPageController.setCurrentUser(currentUser);
        gatheringPageController.setTabUpdateMap(tabUpdateMap);
        gatheringPageController.loadGatheringPage(newGathering);
        Tab newTab = mainTabPane.getTabs().get(2);
        this.tabUpdateMap.put(newTab, true);
        mainTabPane.getSelectionModel().select(1);
        mainTabPane.getSelectionModel().select(2);
        eventName = null;
    }

    public void handleAddTagsButton() {

        Stage stage = new Stage();
        stage.setTitle("Add tags!");

        try {
            List<Tag> tagList = GatheringDB.getTags();
            ListView<Label> listView = new ListView<>();
            Map<Label, Tag> labelTagMap = new HashMap<>();
            for (Tag tag : tagList) {
                Label label = new Label(tag.getTag());
                listView.getItems().add(label);
                labelTagMap.put(label, tag);
            }
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
    public void setCurrentUser(User currentUser){this.currentUser = currentUser;}
    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(JFXTabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }


}
