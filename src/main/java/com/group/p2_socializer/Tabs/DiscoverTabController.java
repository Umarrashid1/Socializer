package com.group.p2_socializer.Tabs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DiscoverTabController implements Initializable {

    @FXML
    private AnchorPane discoverAnchorPane;

    @FXML
    private ScrollPane discoverScrollPane;

    @FXML
    private VBox gatheringItemVBox;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < 10; i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/group/p2_socializer/gathering_item.fxml"));

            try {
                VBox vBox = fxmlLoader.load();
                gatheringItemVBox.getChildren().add(vBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Set the gatheringItemVBox as the content of the scroll pane
        discoverScrollPane.setContent(gatheringItemVBox);
    }






}

