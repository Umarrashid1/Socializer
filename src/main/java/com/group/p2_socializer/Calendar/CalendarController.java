package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.Socializer;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.VBox;



import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController implements Initializable {

    public JFXButton eventCalendarButton;
    @FXML
    void handleCreateEventButton(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Socializer.class.getResource("create-event-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Socializer");
        stage.setScene(scene);
        stage.show();
    }

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

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
                            createCalendarActivity(activities, rectangleHeight, rectangleWidth, stackPane, rectangle);
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


    void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane, Rectangle rectangle) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            Text text = new Text(calendarActivities.get(k).getEventName());
            text.setFont(Font.font("Eras Light ITC", 12));
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                //On Text clicked
                System.out.println(text.getText());
            });
        }

        rectangle.setOnMouseClicked((MouseEvent event) -> {
            ListView<CalendarActivity> listView = new ListView<>();
            listView.getItems().addAll(calendarActivities);
            listView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(CalendarActivity item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        // Create a VBox to hold the text boxes
                        VBox vbox = new VBox();

                        // Create a text box for each property of the CalendarActivity
                        Label eventNameTitle = new Label("Event:");
                        Label eventNameLabel = new Label(item.getEventName());

                        Label eventDateTitle = new Label("Date:");
                        Label eventDateLabel = new Label(item.getDate().toString());

                        Label eventDescriptionTitle = new Label("Description:");
                        Label eventDescriptionLabel = new Label(item.getEventDescription());

                        Label eventLocationTitle = new Label("Location:");
                        Label eventLocationLabel = new Label(item.getEventCountry()+ ", " + item.getEventCity());

                        Label eventOrganiserTitle = new Label("Organiser:");
                        Label eventOrganiserLabel = new Label(item.getEventOrganiser());

                        Label[] labels = {eventNameTitle, eventNameLabel, eventDateTitle, eventDateLabel, eventDescriptionTitle, eventDescriptionLabel, eventLocationTitle, eventLocationLabel, eventOrganiserTitle, eventOrganiserLabel};

                        for (int i = 0; i < labels.length; i++) {
                            // Check if the index is even, which means it's every other label
                            if (i % 2 == 0) {
                                labels[i].setFont(Font.font("Eras Bold ITC", 17));
                            }
                            else {
                                //labels[i].setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;");
                                labels[i].setFont(Font.font("Eras Medium ITC", 12));
                            }
                        }

                        // Add the text boxes to the VBox
                        vbox.getChildren().addAll(eventNameTitle,eventNameLabel, eventDateTitle, eventDateLabel, eventDescriptionTitle, eventDescriptionLabel, eventLocationTitle, eventLocationLabel,eventOrganiserTitle,eventOrganiserLabel);
                        BorderPane borderPane = new BorderPane();

// Set the VBox as the center of the BorderPane
                        borderPane.setCenter(vbox);
                        borderPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5px;");


// Set the BorderPane as the cell's graphic
                        setGraphic(borderPane);

                    }
                }
            });

            Scene scene = new Scene(listView, 400, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        });


    Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#ECFF79")),
                new Stop(1, Color.web("#ECFF79", 0.5))
        };

// Create the RadialGradient and set it as the background fill for the VBox
        RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        calendarActivityBox.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

// Set other properties for the VBox as needed
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.9);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.70);
        stackPane.getChildren().add(calendarActivityBox);
        calendarActivityBox.toBack();

    }
}