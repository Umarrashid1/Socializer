package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.Calendar.CalendarController;
import com.group.p2_socializer.Calendar.CalendarDB;
import com.group.p2_socializer.EventPage.EventPageController;
import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CreateGatheringController {


    @FXML
    private TextField eventNameTextField;
    @FXML
    private DatePicker eventDatePicker;
    @FXML
    private TextField eventTimeTextField;
    @FXML
    private TextField eventCityTextField;
    @FXML
    private TextField eventOrganiserTextField;
    @FXML
    private TextField eventCountryTextField;
    @FXML
    private TextArea eventDescriptionTextArea;
    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab calendarTab;



    @FXML
    public void handleCreateEvent() throws SQLException, IOException {

        String eventName = eventNameTextField.getText();
        LocalDate eventDate = eventDatePicker.getValue();
        LocalTime eventTime = LocalTime.parse(eventTimeTextField.getText());
        String eventCity = eventCityTextField.getText();
        String eventCountry = eventCountryTextField.getText();
        String eventDescription = eventDescriptionTextArea.getText();
        String eventOrganiser = eventOrganiserTextField.getText();
        LocalDateTime localDateTime = LocalDateTime.of(eventDate, eventTime);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'AT' HH:mm", Locale.ENGLISH);
        String formattedDate = zonedDateTime.format(formatter).toUpperCase();
        ZoneId zoneId = ZoneId.systemDefault();
        String timeZone = zoneId.toString();
        CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);

        //Switch to Calendar tab
        mainTabPane.getSelectionModel().select(calendarTab);

        EventPageController controller = new EventPageController();
        controller.loadEventPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);

        String createdMessage = "Event Created!";

        CalendarController calendarController = new CalendarController();

        calendarController.showCreatedPopUp(createdMessage);

    }
}
