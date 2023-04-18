package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.activities.Activity;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ProfileTabController implements Initializable{
   // ObservableList<Activity> listGatheringsPrior = FXCollections.observableArrayList();
    // ObservableList<Activity> listGatheringInterest = FXCollections.observableArrayList();
    //ObservableList<Activity> listGatheringOrganized = FXCollections.observableArrayList();

    @FXML
    private AnchorPane ChooseGatheringAnchorPane;

    @FXML
    private ImageView profilePic;

    @FXML
    private Label profileUsername;

    @FXML
    private Label profileLocation;

    @FXML
    private Button addUserAsFriend;

    @FXML
    private Button applaudUser;

    @FXML
    private Button inviteToGathering;

    @FXML
    private Button reportUser;

    @FXML
    private TextField profileTag2;

    @FXML
    private TextField profileTag1;

    @FXML
    private JFXListView<String> listGatheringInterest;

    @FXML
    private JFXListView<String> listGatheringPrior;

    @FXML
    private JFXListView<String> listGatheringOrganized;
    @FXML
    void goToSelectedGathering(MouseEvent event) {
        //TODO: make click go to selected gathering

    }

    @FXML
    void hoverOverProfilePic(MouseDragEvent event) {
        //TODO: make distinction and tooltip for edit
    }



    private void loadData(ObservableList<Activity> list) {
       list.removeAll(list);
       //TODO: add functionality to load all gatherings from user object.'
        /*
        for(String i: gatheringAttendingList) {
            list.add(userOfProfile.getGatheringAttending(i));
         */
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void setUser(User user) {
        profileUsername.setText(user.getUsername());
    }
}