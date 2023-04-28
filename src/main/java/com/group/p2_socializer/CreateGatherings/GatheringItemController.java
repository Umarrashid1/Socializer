package com.group.p2_socializer.CreateGatherings;
import com.group.p2_socializer.Pages.GatheringPageController;
import com.group.p2_socializer.activities.Gathering;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class GatheringItemController implements Initializable {

    private Gathering currentGathering;

    @FXML
    private AnchorPane gatheringItemAnchorPane;
    @FXML
    private Label titleLabel;

    @FXML
    private Label organiserLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label attendingLabel;

    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label timeLabel;

    public Map<Tab, Boolean> tabUpdateMap;
    private TabPane mainTabPane;

    public void setGatheringItemAnchorPane(AnchorPane gatheringItemAnchorPane) {
        this.gatheringItemAnchorPane = gatheringItemAnchorPane;
    }


    public void setGathering(Gathering gathering){
        currentGathering = gathering;
        titleLabel.setText(gathering.getActivityName());
        dayOfMonthLabel.setText(String.valueOf(gathering.getLocalDateTime().getDayOfMonth()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
        String monthString = gathering.getLocalDateTime().format(dateFormatter).toUpperCase();
        monthLabel.setText(monthString);

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        timeLabel.setText(String.valueOf(gathering.getLocalDateTime().format(timeFormatter)));
        organiserLabel.setText(gathering.getActivityOrganiser());
        attendingLabel.setText("x" + "/" + String.valueOf(gathering.getActivityMaximumParticipants()));
        locationLabel.setText(gathering.getActivityCity() + ", " + gathering.getActivityCountry());
    }
    public void setTabUpdateMap(Map<Tab, Boolean> tabUpdateMap){this.tabUpdateMap = tabUpdateMap;}
    public void setMainTabPane(TabPane mainTabPane){
        this.mainTabPane = mainTabPane;
    }


    public void onGatheringClicked(MouseEvent mouseEvent) {
    GatheringPageController gatheringPageController = new GatheringPageController();
        try {
            gatheringPageController.setTabUpdateMap(tabUpdateMap);
            gatheringPageController.loadGatheringPage(currentGathering);
            gatheringPageController.setMainTabPane(mainTabPane);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setMonthLabel(String monthLabel) {
        this.monthLabel.setText(monthLabel);
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel.setText(timeLabel);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


