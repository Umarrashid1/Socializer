package com.group.p2_socializer.Pages;

import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.Utils.ManagerBarController;
import com.group.p2_socializer.Utils.ProfileItemController;
import com.group.p2_socializer.Activities.Gathering;
import com.group.p2_socializer.Activities.Tag;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

public class GatheringPageController {
    @FXML
    public AnchorPane centerPane;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public VBox gatheringInfoVBox;
    @FXML
    public Label gatheringDateLabel;
    @FXML
    public Label gatheringTitleLabel;
    @FXML
    public Label gatheringLocationLabel;
    @FXML
    public HBox organiserHBox;
    @FXML
    public Label gatheringCityLabel;
    @FXML
    public Label gatheringOrganiserLabel;
    @FXML
    public Label gatheringParticipationLabel;
    @FXML
    public JFXButton attendButton;
    @FXML
    public JFXButton notGoingButton;
    @FXML
    private VBox vBoxPostNews;
    @FXML
    private VBox postList;
    public Map<Tab, Boolean> tabUpdateMap;
    private JFXTabPane mainTabPane;
    @FXML
    private VBox participantsVBox;
    private User currentUser;


    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap) {
        this.tabUpdateMap = tabUpdateMap;
    }

    public void setMainTabPane(JFXTabPane mainTabPane) {
        this.mainTabPane = mainTabPane;
    }


    public void loadGatheringPage(Gathering newGathering) throws SQLException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'AT' HH:mm", Locale.ENGLISH);
        ZonedDateTime zonedDateTime = newGathering.getLocalDateTime().atZone(newGathering.getTimeZone());
        String dateString = zonedDateTime.format(formatter).toUpperCase();

        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(GatheringPageController.class.getResource("/com/group/p2_socializer/gathering_page.fxml"));
        Parent root = fxmlLoader.load();

        // Get reference to centerPane FXML file
        ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPane");
        AnchorPane centerPane = (AnchorPane) scrollPane.getContent();
        VBox gatheringInfoVBox = (VBox) centerPane.lookup("#gatheringInfoVBox");
        HBox organiserHBox = (HBox) gatheringInfoVBox.lookup("#organiserHBox");
        VBox participantsVBox = (VBox) centerPane.lookup("#participantsVBox");
        organiserHBox.setMaxWidth(250);


        Label gatheringDateLabel = (Label) gatheringInfoVBox.lookup("#gatheringDateLabel");
        gatheringDateLabel.setText(dateString);
        gatheringDateLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 13; -fx-text-fill: #797878;");

        Label gatheringTitleLabel = (Label) gatheringInfoVBox.lookup("#gatheringTitleLabel");
        gatheringTitleLabel.setText(newGathering.getActivityName());
        gatheringTitleLabel.setFont(Font.font("Eras Bold ITC", 30));

        Label gatheringLocation = (Label) gatheringInfoVBox.lookup("#gatheringLocationLabel");
        gatheringLocation.setText(newGathering.getActivityCity() + ", " + newGathering.getActivityCountry());
        gatheringLocation.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");

        Label gatheringCityLabel = (Label) organiserHBox.lookup("#gatheringCityLabel");
        gatheringCityLabel.setText("by ");
        gatheringCityLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");


        Label gatheringOrganiserLabel = (Label) organiserHBox.lookup("#gatheringOrganiserLabel");
        gatheringOrganiserLabel.setText(newGathering.getActivityOrganiser());
        gatheringOrganiserLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-style: italic; -fx-font-weight: bold; -fx-font-size: 12; -fx-text-fill: #000000;");

        Label gatheringParticipationLabel = (Label) participantsVBox.lookup("#gatheringParticipationLabel");
        gatheringParticipationLabel.setText("x" + "/" + newGathering.getActivityMaximumParticipants());


        Label gatheringDescriptionLabel = new Label(newGathering.getActivityDescription());
        gatheringDescriptionLabel.setMaxWidth(300);
        gatheringDescriptionLabel.setWrapText(true);
        gatheringDescriptionLabel.setFont(Font.font("Arial", 13));
        gatheringDescriptionLabel.setPadding(new Insets(0, 0, 15, 0));

        VBox descriptionVBox = new VBox(gatheringDescriptionLabel);
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

        // Add the Line to the VBox
        descriptionVBox.getChildren().add(line);


        JFXButton attendGatheringButton = new JFXButton();
        attendGatheringButton.setText("Attend Gathering");
        attendGatheringButton.setId("attendGatheringButton");
        attendGatheringButton.setPrefSize(116.0, 29.0);
        attendGatheringButton.setLayoutX(37.0);
        attendGatheringButton.setLayoutY(412.0);
        attendGatheringButton.setStyle("-fx-background-color: #71FF60;");
        attendGatheringButton.setRipplerFill(javafx.scene.paint.Color.valueOf("#7bce3c"));

        JFXButton notGoingButton = new JFXButton();
        notGoingButton.setText("Not going");
        notGoingButton.setId("notGoingButton");
        notGoingButton.setPrefSize(116.0,29.0);
        notGoingButton.setLayoutX(164.0);
        notGoingButton.setLayoutY(412.0);
        notGoingButton.setStyle("-fx-background-color: FFA3A3FF;");
        notGoingButton.setRipplerFill(javafx.scene.paint.Color.valueOf("#e80027"));


        HBox outerHBox = new HBox();

        attendGatheringButton.setOnAction((actionEvent ) -> {

            try {
                attendGatheringButton(newGathering);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            outerHBox.getChildren().clear();
            //Refresh profile items
            try {
                addProfileItems(newGathering, descriptionVBox, outerHBox);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }

        });

        notGoingButton.setOnMouseClicked((MouseEvent event) -> {

            try {
                leaveGatheringButton(event, newGathering);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //Refresh profile items
            try {
                addProfileItems(newGathering, descriptionVBox, outerHBox);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }

        });

        //-----------------------------------------------------------------------
        List<Tag> taglist = newGathering.getTags();


        CornerRadii cornerRadii = new CornerRadii(30);
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, cornerRadii, null);
        Background background = new Background(backgroundFill);

        VBox tagsVBox = new VBox();
        tagsVBox.setSpacing(10);
        tagsVBox.setPadding(new Insets(10,10,10,0));

        // Create a new HBox for the labels
        int count = 0;
        HBox rowOfTagsHBox = new HBox();
        rowOfTagsHBox.setSpacing(10);

        for (Tag tag : taglist) {
            Label tagLabel = new Label(tag.getTag());
            tagLabel.setBackground(background);
            tagLabel.setPadding(new Insets(3,5,3,5));
            tagLabel.setMaxWidth(Double.MAX_VALUE);
            VBox.setVgrow(tagLabel, Priority.ALWAYS);

            rowOfTagsHBox.getChildren().add(tagLabel);
            count++;

            // Max 3 labels per HBox, add the current HBox to the VBox and create a new HBox
            if (count == 3) {

                count = 0;
                tagsVBox.getChildren().add(rowOfTagsHBox);
                rowOfTagsHBox = new HBox();
                rowOfTagsHBox.setSpacing(10);
            }
        }

        // Add the last row of HBoxes to the VBox, if it contains any labels
        if (rowOfTagsHBox.getChildren().size() > 0) {
            tagsVBox.getChildren().add(rowOfTagsHBox);
        }
        descriptionVBox.getChildren().add(tagsVBox);


        //-------------------------------------------------------------------------

        addProfileItems(newGathering, descriptionVBox, outerHBox);
        //--------------------------------------------------------------------------



        // Load the manager_bar.fxml file
        if(currentUser.getUserType().equals("admin") || (currentUser.getUsername().equals(newGathering.getActivityOrganiser()))){
            FXMLLoader managerBarFxmlLoader = new FXMLLoader(GatheringPageController.class.getResource("/com/group/p2_socializer/manager_bar.fxml"));
            Parent managerBarRoot = managerBarFxmlLoader.load();
            ManagerBarController managerBarController = managerBarFxmlLoader.getController(); // Get reference to actual instance of ManagerBarController
            managerBarController.setMainTabPane(mainTabPane);
            managerBarController.setTabUpdateMap(tabUpdateMap);
            managerBarController.setNewGathering(newGathering);
            boolean isGathering;
            isGathering = true;
            managerBarController.setDeleteButton(isGathering);
            centerPane.getChildren().add(managerBarRoot);
        }

        centerPane.getChildren().add(descriptionVBox);
        centerPane.getChildren().add(attendGatheringButton);
        centerPane.getChildren().add(notGoingButton);


        // Create a new stage and set the new scene
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.setTitle(newGathering.getActivityName());


        newStage.show();

    }

    public void addProfileItems(Gathering newGathering, VBox descriptionVBox, HBox outerHBox) throws IOException, SQLException {
        outerHBox.getChildren().clear();

        outerHBox.setPrefHeight(90.0);
        outerHBox.setPrefWidth(300.0);

        ScrollPane profileScrollPane = new ScrollPane();
        profileScrollPane.setBackground(
                new Background(new BackgroundFill(Color.TRANSPARENT, null, null))
        );
        profileScrollPane.setMinHeight(50.0);
        //profileScrollPane.setMinWidth(.0);
        profileScrollPane.setPrefWidth(300);
        profileScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane profileAnchorPane = new AnchorPane();
        //profileAnchorPane.setPrefWidth(378.0);

        HBox innerHbox = new HBox();
        innerHbox.setPrefHeight(0.0);
        innerHbox.setPrefWidth(0.0);

        profileAnchorPane.getChildren().add(innerHbox);
        profileScrollPane.setContent(profileAnchorPane);

        List<User> userList = newGathering.getGatheringParticipants();

        for (User user : userList) {
            FXMLLoader profileItemFxmlLoader = new FXMLLoader(GatheringPageController.class.getResource("/com/group/p2_socializer/profile_item.fxml"));
            VBox profileItemVBox = profileItemFxmlLoader.load();
            ProfileItemController profileItemController = profileItemFxmlLoader.getController();
            profileItemController.setProfileNameLabel(user);
            innerHbox.getChildren().add(profileItemVBox);
        }

        outerHBox.getChildren().addAll(profileScrollPane);

        // Add profilesVBox
        descriptionVBox.getChildren().add(outerHBox);

    }


    private void attendGatheringButton(Gathering newGathering) throws SQLException {
        currentUser.joinGathering(newGathering.getGatheringID());
    }

    private void leaveGatheringButton(MouseEvent event, Gathering newGathering) throws SQLException {
        currentUser.leaveGathering(newGathering.getGatheringID());
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}


