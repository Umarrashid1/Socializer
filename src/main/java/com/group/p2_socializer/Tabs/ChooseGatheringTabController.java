package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.CreateGatherings.CreateGatheringController;
import com.group.p2_socializer.Utils.PopUpMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChooseGatheringTabController extends TabController {


    @FXML
    private AnchorPane ChooseGatheringAnchorPane;

    @FXML
    private VBox eventGatheringVBox;
    @FXML
    private VBox customGatheringVBox;

    @FXML
    private VBox preDefinedGatheringVBox;


        public void handleCustomGatheringCreation() {
        try {
            super.loader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/create_gathering.fxml"));
            AnchorPane newPane = loader.load();
            CreateGatheringController createGatheringController = loader.getController();
            createGatheringController.setTabUpdateMap(tabUpdateMap);
            createGatheringController.setMainTabPane(mainTabPane);
            super.mainTabPane.getSelectionModel().getSelectedItem().setContent(newPane);

        } catch (IOException iex) {
            System.out.println("file not found");
        }

        }



    public void handlePremadeGatheringCreation() throws IOException {
        VBox container = new VBox(); // Use VBox as the container for the GridPane
        container.setSpacing(10); // Set spacing between items in the container

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(container); // Set the container as the content of the ScrollPane
        scrollPane.setFitToWidth(true); // Enable horizontal scrolling for the ScrollPane
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Set vertical scrollbar policy to ALWAYS


        scrollPane.setMaxHeight(780); // DOES NOT WORK

        GridPane gridPane = new GridPane();
        int columnCount = 0;
        int rowCount = 0;

        // Load three "premade_gathering_item" items per row, and repeat for multiple rows
        for (int i = 0; i < 12; i++) { // Update the loop iteration count to load more rows
            FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/premade_gathering_item.fxml"));
            VBox premadeGatheringItem = itemLoader.load();
            gridPane.add(premadeGatheringItem, columnCount, rowCount); // Add the item to the gridPane
            columnCount++;

            // When three items are added to the row, move to the next row
            if (columnCount == 3) {
                columnCount = 0;
                rowCount++;
            }
        }

        // Set spacing between grid items
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        container.getChildren().add(gridPane);
        ChooseGatheringAnchorPane.getChildren().setAll(scrollPane);

    }





    public void openCalendarTab(MouseEvent mouseEvent) {
        super.mainTabPane.getSelectionModel().select(3);
        String clickEventMessage = "Click an event to create a gathering";
        PopUpMessage popUpMessage = new PopUpMessage();
        popUpMessage.showCreatedPopUp(clickEventMessage);
    }
}
