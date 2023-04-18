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
    //TODO: !!! pass the user in all instances of navigation to profile !!!
        /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/" + name + ".fxml"));
            AnchorPane newPane = loader.load();
         */

    //TODO convert users tags to viewable tokens. possibly add highlighting for shared tags
    ObservableList<Activity> listGatheringsPrior = FXCollections.observableArrayList();
    ObservableList<Activity> listGatheringInterest = FXCollections.observableArrayList();
    ObservableList<Activity> listGatheringOrganized = FXCollections.observableArrayList();
    private User selectedUser; //TODO: method for getting correct User object
    private User systemUser; //
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

    public void initializeProfileFromUser(User userOfProfile) {
        selectedUser = userOfProfile;
        //TODO:
    }
    @FXML
    void goToSelectedGathering(MouseEvent event) {
        //TODO: make click go to selected gathering
    }

    @FXML
    void hoverOverProfilePic(MouseDragEvent event) {
        //TODO: make distinction and tooltip for edit
    }

    public void revealProfile(ObservableList<Activity> list) {
        for(Activity i: list) {
            if((i.getAttendeeList).contains(systemUser.getUserID()) == true) {
                //do something to set ImageViewer opacity to zero
                // show name?
            }
        }
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
        //TODO: fucing initialize something man.
    }
}

/*
  private User coolUserGuy;
    @FXML
    public Tab myProfileTab;
    Profile thisProfile = profile.getUserID();  //get the required user info to fill the profile page



    boolean isUsersProfile = doesUserOwnProfile();
    public bolean doesUserOwnProfile(User user, ) {
        if (user.getUserID() == profile.getUserID) {
            return true;
        } else {
            return false;
        }
    }
 */