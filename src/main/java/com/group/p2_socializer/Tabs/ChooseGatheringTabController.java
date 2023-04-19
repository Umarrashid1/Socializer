package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.CreateGatherings.CreateGatheringController;
import com.group.p2_socializer.UserLogIn.User;
import com.group.p2_socializer.activities.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChooseGatheringTabController {


    @FXML
    private AnchorPane ChooseGatheringAnchorPane;

    @FXML
    private VBox eventGatheringVBox;
    @FXML
    private VBox customGatheringVBox;

    @FXML
    private VBox preDefinedGatheringVBox;


        public void handleCustomGatheringCreation() {


        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane createGatheringAnchorPane = loader.load(getClass().getResource("/com/group/p2_socializer/create_custom_gathering.fxml"));
            ChooseGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPane);

        } catch (IOException iex) {
            System.out.println("file not found");
        }

        }


        public void handlePremadeGatheringCreation() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/create_custom_gathering.fxml"));
                AnchorPane createGatheringAnchorPane = loader.load();
                CreateGatheringController controller = loader.getController();
                controller.setDistortion();
                ChooseGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPane);

            } catch (IOException iex) {
            System.out.println("file not found");
            }

        }


        public void handleEventGatheringCreation(Event event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/create_custom_gathering.fxml"));
                AnchorPane createGatheringAnchorPane = loader.load();
                CreateGatheringController controller = loader.getController();
                controller.setDistortion();
                ChooseGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPane);

            } catch (IOException iex) {
                System.out.println("file not found");
            }

        }

    public void openCalendarTab(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/event_calendar_tab.fxml"));
            AnchorPane createGatheringAnchorPane = loader.load();
            EventCalendarTabController controller = loader.getController();
            ChooseGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
