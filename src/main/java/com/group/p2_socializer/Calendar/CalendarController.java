package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.EventPage.EventPageController;
import com.group.p2_socializer.Tabs.CreateEventController;
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
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CalendarController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;
    @FXML
    public Tab eventCalendarButton;
    @FXML
    private Text year;
    @FXML
    private Text month;
    @FXML
    private FlowPane calendar;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        Map<Integer, List<CalendarActivity>> calendarData;
        try {
            calendarData = CalendarManager.getCalendarActivitiesMonth(dateFocus);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        drawCalendar(calendarData);
    }
    
    @FXML
    void backOneMonth() throws SQLException {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        Map<Integer, List<CalendarActivity>> calendarData = CalendarManager.getCalendarActivitiesMonth(dateFocus);
        drawCalendar (calendarData);
    }

    @FXML
    void forwardOneMonth(ActionEvent event) throws SQLException {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        Map<Integer, List<CalendarActivity>> calendarData = CalendarManager.getCalendarActivitiesMonth(dateFocus);
        drawCalendar (calendarData);
    }
    @FXML
    public void drawCalendar(Map<Integer, List<CalendarActivity>> calendarData) {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = dateFocus.getMonth().maxLength();
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.GREY);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);
                rectangle.toFront();

                // Add event handlers
                rectangle.setOnMouseEntered((MouseEvent event) -> {
                    rectangle.setFill(Color.LIGHTGRAY);
                    DropShadow dropShadow = new DropShadow(10, Color.LIGHTGREY);
                    rectangle.setEffect(dropShadow);
                    FadeTransition ft = new FadeTransition(Duration.millis(500), rectangle);
                    ft.setToValue(0.5);
                    ft.play();
                });

                rectangle.setOnMouseExited((MouseEvent event) -> {
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setEffect(null);
                    FadeTransition ft = new FadeTransition(Duration.millis(500), rectangle);
                    ft.setToValue(1.0);
                    ft.play();
                });

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<CalendarActivity> activities = calendarData.get(currentDate);
                        if (activities != null) {

                            CreateEventController createEventController = new CreateEventController();
                            createEventController.createCalendarActivity(activities, rectangleHeight, rectangleWidth, stackPane, rectangle);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStrokeWidth(2);
                        rectangle.setStroke(Color.RED);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }


    public void showCreatedPopUp(String createdMessage) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);

        Label message = new Label(createdMessage);
        message.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: Black;");



        StackPane root = new StackPane();
        root.getChildren().add(message);
        root.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 200, 50);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();

        // Get screen dimensions and center point
        // position popup window in center of screen
        double centerX = ScreenUtils.getScreenCenterX() - stage.getWidth() / 2;
        double centerY = ScreenUtils.getScreenCenterY() - stage.getHeight() / 2;
        stage.setX(centerX);
        stage.setY(centerY);

        // Added fade in and out
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setDelay(Duration.seconds(5));

        SequentialTransition sequence = new SequentialTransition(fadeIn, fadeOut);
        sequence.setCycleCount(1);
        sequence.play();

        sequence.setOnFinished(e -> stage.close());
    }

}