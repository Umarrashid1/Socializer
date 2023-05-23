package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.Database.UserDB;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.activities.Gathering;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProfileTabController extends TabController implements Initializable{
   // ObservableList<Activity> listGatheringsPrior = FXCollections.observableArrayList();
    // ObservableList<Activity> listGatheringInterest = FXCollections.observableArrayList();
    //ObservableList<Activity> listGatheringOrganized = FXCollections.observableArrayList();
   ArrayList<String> tagList = new ArrayList<>();

    @FXML
    private AnchorPane ChooseGatheringAnchorPane;

    @FXML
    private ImageView profilePic;

    @FXML
    private Text tagListText;

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

    public void setUser(User user) throws SQLException {
     profileUsername.setText(user.getUsername());
     tagList =  user.getTags();
     String tagListAsString =tagList.toString();
     //tagListText.setText(tagListAsString);
     // Temporary solution
    }

    void goToSelectedGathering(MouseEvent event) {
        //TODO: make click go to selected gathering

    }

    @FXML
    void hoverOverProfilePic(MouseDragEvent event) {
        //TODO: make distinction and tooltip for edit
    }



    private void loadData(ObservableList<Gathering> list) {
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




}