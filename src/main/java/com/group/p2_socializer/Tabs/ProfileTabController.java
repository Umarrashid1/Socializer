package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.Socializer;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Activities.Gathering;
import com.group.p2_socializer.Utils.ScreenUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProfileTabController extends TabController {
 public JFXButton deleteAccountButton;
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

    public void deleteAccount() throws IOException {
     Node node = deleteAccountButton;
     Stage currentStage = (Stage) node.getScene().getWindow();
     currentStage.close();

     FXMLLoader loginFxmlLoader = new FXMLLoader(Socializer.class.getResource("login_page.fxml"));
     Scene scene = new Scene(loginFxmlLoader.load());

     Stage mainStage = new Stage();
     mainStage.setTitle("Socializer");
     mainStage.setScene(scene);
     mainStage.show();
     double centerX = ScreenUtils.getScreenCenterX() - mainStage.getWidth() / 2;
     double centerY = ScreenUtils.getScreenCenterY() - mainStage.getHeight() / 2;
     mainStage.setX(centerX);
     mainStage.setY(centerY);
    }

    @FXML
    void hoverOverProfilePic(MouseDragEvent event) {
        //TODO: make distinction and tooltip for edit
    }









}