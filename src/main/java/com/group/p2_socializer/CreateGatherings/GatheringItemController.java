package com.group.p2_socializer.CreateGatherings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class GatheringItemController {
    public void setGatheringItemAnchorPane(AnchorPane gatheringItemAnchorPane) {
        this.gatheringItemAnchorPane = gatheringItemAnchorPane;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel.setText(titleLabel);
    }

    public void setOrganiserLabel(String organiserLabel) {
        this.organiserLabel.setText(organiserLabel);
    }

    public void setAttendingLabel(String attendingLabel) {
        this.attendingLabel.setText(attendingLabel);
    }

    public void setDayOfMonthLabel(String day) {
        this.dayOfMonthLabel.setText(day);


    }

    public void setMonthLabel(String monthLabel) {
        this.monthLabel.setText(monthLabel);
    }

    public void setTimeLabel(String timeLabel) {
        this.timeLabel.setText(timeLabel);
    }

    @FXML
    private AnchorPane gatheringItemAnchorPane;
    @FXML
    private Label titleLabel;

    @FXML
    private Label organiserLabel;

    @FXML
    private Label attendingLabel;

    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label timeLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


