package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.CreateGatherings.GatheringItemController;
import com.group.p2_socializer.Database.GatheringDB;
import com.group.p2_socializer.activities.Gathering;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class DiscoverTabController extends TabController implements Initializable {
    @FXML
    private AnchorPane discoverAnchorPane;
    @FXML
    private ScrollPane discoverScrollPane;
    @FXML
    private VBox gatheringItemVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void loadGatheringItems() {
        try {
            List<Gathering> gatheringList = GatheringDB.getGatheringsDate(2023);
            for (Gathering gathering : gatheringList) {
                FXMLLoader itemFxmlLoader = new FXMLLoader(getClass().getResource("/com/group/p2_socializer/gathering_item.fxml"));
                try {
                    VBox gatheringItem = itemFxmlLoader.load();
                    GatheringItemController gatheringItemController = itemFxmlLoader.getController();
                    gatheringItemController.setGathering(gathering);
                    gatheringItemController.setTabUpdateMap(tabUpdateMap);
                    gatheringItemController.setMainTabPane(mainTabPane);
                    gatheringItemVBox.getChildren().add(gatheringItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        discoverScrollPane.setContent(gatheringItemVBox);
        gatheringItemVBox = null;
    }

}
