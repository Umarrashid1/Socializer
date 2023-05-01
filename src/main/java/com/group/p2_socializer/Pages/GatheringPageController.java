package com.group.p2_socializer.Pages;

import com.group.p2_socializer.Utils.ManagerBarController;
import com.group.p2_socializer.activities.Gathering;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
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
    public JFXButton interestedButton;
    @FXML
    public JFXButton attendGatheringButton;
    @FXML
    private VBox vBoxPostNews;
    @FXML
    private VBox postList;
    public Map<Tab, Boolean> tabUpdateMap;
    private JFXTabPane mainTabPane;
    @FXML
    private VBox participantsVBox;


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

        //-----------------------------------------------------------------------

        List<String> words = new ArrayList<>();
        words.add("Apple");
        words.add("Banana");
        words.add("Cherry");
        words.add("Date");
        words.add("Fig");
        words.add("Grape");
        words.add("Lemon");
        words.add("Mango");
        words.add("Orange");
        words.add("Peach");
        words.add("Quince");
        words.add("Raspberry");
        words.add("Strawberry");
        words.add("Tangerine");
        words.add("Watermelon");
        Collections.shuffle(words);

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
        for (String word : words) {
            Label tagLabel = new Label(word);
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
        FXMLLoader fxmlLoader1 = new FXMLLoader(GatheringPageController.class.getResource("/com/group/p2_socializer/manager_bar.fxml"));


        Parent managerBarRoot = fxmlLoader1.load();

        ManagerBarController managerBarController = fxmlLoader1.getController(); // Get reference to actual instance of ManagerBarController
        managerBarController.setMainTabPane(mainTabPane);
        managerBarController.setTabUpdateMap(tabUpdateMap);
        managerBarController.setNewGathering(newGathering);


        boolean isGathering;
        isGathering = true;

        managerBarController.setCancelButton(isGathering);

        centerPane.getChildren().add(managerBarRoot);
        centerPane.getChildren().add(descriptionVBox);

        // Create a new stage and set the new scene
        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.setTitle(newGathering.getActivityName());

        //VBox postList = (VBox) root.lookup("#postList");
        //postList.setMaxWidth(Double.MAX_VALUE);
        //postList.setMaxHeight(Double.MAX_VALUE);

        newStage.show();

    }
}

