package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.Socializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ChooseGatheringTabController {

        @FXML
        private AnchorPane ChooseGatheringAnchorPane;

        @FXML
        private VBox customGatheringVBox;


        public void handleCustomGatheringCreation() {

                AnchorPane originalAnchorPane = ChooseGatheringAnchorPane;

                customGatheringVBox.setOnMouseClicked((MouseEvent event) -> {

                        FXMLLoader loader = new FXMLLoader();
                        try {
                                //ChooseGatheringAnchorPane.getChildren().remove(ChooseGatheringAnchorPane);
                                AnchorPane createGatheringAnchorPane = loader.load(getClass().getResource("/com/group/p2_socializer/create_event_page.fxml"));
                                ChooseGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPane);
                        } catch (IOException iex) {
                                System.out.println("file not found");
                        }
                });



        }
}
