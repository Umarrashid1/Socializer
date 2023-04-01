package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.EventPage.EventPageController;
import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
                break;
            }
            Text text = new Text(calendarActivities.get(k).getEventName());
            text.setFont(Font.font("Eras Light ITC", 12));
            calendarActivityBox.getChildren().add(text);
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
                        Label windowTitleLabel = new Label();
                        windowTitleLabel.setText("Click event to enter event page");
                        windowTitleLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 11; -fx-text-fill: #797878;");


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

                        Label[] labels = {eventNameTitle, eventNameLabel, eventDateTitle, eventDateLabel,
                                eventDescriptionTitle, eventDescriptionLabel, eventLocationTitle,
                                eventLocationLabel, eventOrganiserTitle, eventOrganiserLabel};

                        for (int i = 0; i < labels.length; i++) {
                            // Check if the index is even, which means every other label becomes bold
                            if (i % 2 == 0) {
                                labels[i].setFont(Font.font("Eras Bold ITC", 17));
                            }
                            else {
                                //labels[i].setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-border-color: black;");
                                labels[i].setFont(Font.font("Eras Medium ITC", 12));
                            }
                        }

                        // Add the text boxes to the VBox
                        vbox.getChildren().addAll(windowTitleLabel, eventNameTitle, eventNameLabel, eventDateTitle, eventDateLabel, eventDescriptionTitle, eventDescriptionLabel, eventLocationTitle, eventLocationLabel, eventOrganiserTitle, eventOrganiserLabel);
                        BorderPane borderPane = new BorderPane();

                        // Set the VBox as the center of the BorderPane
                        borderPane.setCenter(vbox);
                        borderPane.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5px;");

                        // Set the BorderPane as the cell's graphic
                        setGraphic(borderPane);



                        vbox.setOnMouseClicked((MouseEvent event) -> {

                            EventPageController controller = new EventPageController();

                            String eventName = item.getEventName();


                            ZonedDateTime zonedDateTime = item.getDate();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'AT' HH:mm", Locale.ENGLISH);

                            String formattedDate = zonedDateTime.format(formatter).toUpperCase();

                            String eventOrganiser = item.getEventOrganiser();

                            String eventCity = item.getEventCity();
                            String eventCountry = item.getEventCountry();

                            String eventDescription = item.getEventDescription();

                            controller.loadEventPage(event, eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);
                        });

                    }
                }
            });

            Scene scene = new Scene(listView, 600, 700);
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
        ZoneId zoneId = ZoneId.systemDefault();
        String timeZone = zoneId.toString();
        CalendarDB.storeEvent(eventName, eventDescription, eventCity, eventCountry, eventOrganiser, localDateTime, timeZone);

        //Switch to Calendar tab
        mainTabPane.getSelectionModel().select(calendarTab);
        showEventCreatedMessage();
    }

    public void showEventCreatedMessage() {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);

        Label message = new Label("Event created!");
        message.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: Black;");

        StackPane root = new StackPane();
        root.getChildren().add(message);

        Scene scene = new Scene(root, 200, 50);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        // Get screen dimensions and center point
        // position popup window in center of screen
        double centerX = ScreenUtils.getScreenCenterX() - stage.getWidth() / 2;
        double centerY = ScreenUtils.getScreenCenterY() - stage.getHeight() / 2;
        stage.setX(centerX);
        stage.setY(centerY);

// Set stage position to the center of the screen

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