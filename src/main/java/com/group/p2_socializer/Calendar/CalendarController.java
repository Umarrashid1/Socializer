package com.group.p2_socializer.Calendar;

import com.group.p2_socializer.EventPage.EventPageController;
import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    public Tab createGatheringButton;

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

        /*FXMLLoader loader = new FXMLLoader();
        try {
            //TODO: Fix naming here wtf
            AnchorPane createGatheringAnchorPaneNew = loader.load(getClass().getResource("/com/group/p2_socializer/create_gathering.fxml"));
            createGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPaneNew);
        }
        catch (IOException iex) {
                System.out.println("file not found");
        }
*/
    }



    @FXML
    private VBox customGatheringVBox;
    @FXML
    private AnchorPane ChooseGatheringAnchorPane;
    @FXML
    private AnchorPane createGatheringAnchorPane;
    @FXML
    private AnchorPane outerAnchorPane;



    public void handleCustomGatheringCreation() {
        AnchorPane originalAnchorPane = ChooseGatheringAnchorPane;

        customGatheringVBox.setOnMouseClicked((MouseEvent event) -> {

            FXMLLoader loader = new FXMLLoader();
            try {
                ChooseGatheringAnchorPane.getChildren().remove(ChooseGatheringAnchorPane);
                AnchorPane createGatheringAnchorPane = loader.load(getClass().getResource("/com/group/p2_socializer/create_event_page.fxml"));
                ChooseGatheringAnchorPane.getChildren().setAll(createGatheringAnchorPane);
            } catch (IOException iex) {
                System.out.println("file not found");
            }
        });

        /*ChooseGatheringAnchorPane.setOnMouseClicked((MouseEvent event) -> {
            if (!event.getTarget().equals(customGatheringVBox)) {
                ChooseGatheringAnchorPane.getChildren().setAll(originalAnchorPane.getChildren());
            }
        });*/

        mainTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (!newTab.equals(createGatheringButton)) {
                ChooseGatheringAnchorPane.getChildren().remove(ChooseGatheringAnchorPane);

                // switch back to original anchor pane
                ChooseGatheringAnchorPane.getChildren().setAll(originalAnchorPane);
            }
        });
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
    private boolean isWindowOpen = false;

    //TODO: Too intricate, split into methods
    void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane, Rectangle rectangle) {
        VBox calendarActivityBox = new VBox();

        //TODO: NEEDS A REDO

        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 3) {
                Text moreActivities = new Text("and more!");
                calendarActivityBox.getChildren().add(moreActivities);
                break;
            }


            // Set max number of characters in event name, displayed in calendar
            Text text = new Text(calendarActivities.get(k).getEventName().substring(0, Math.min(calendarActivities.get(k).getEventName().length(), 20)));
            if (calendarActivities.get(k).getEventName().length() > 20) {
                text.setText(text.getText() + "...");
            }
            TextFlow textFlow = new TextFlow(text);
            //textFlow.setMaxWidth(80);

            text.setFont(Font.font("Eras Light ITC", FontPosture.REGULAR, 11));
            text.setUnderline(true);
            calendarActivityBox.getChildren().add(textFlow);


        }
        //TODO: Fix isWindowOpen

        if (!isWindowOpen) {
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
                            eventDescriptionLabel.setWrapText(true);
                            eventDescriptionLabel.setMaxWidth(550);

                            Label eventLocationTitle = new Label("Location:");
                            Label eventLocationLabel = new Label(item.getEventCountry() + ", " + item.getEventCity());

                            Label eventOrganiserTitle = new Label("Organiser:");
                            Label eventOrganiserLabel = new Label(item.getEventOrganiser());

                            Label[] labels = {eventNameTitle, eventNameLabel, eventDateTitle, eventDateLabel,
                                    eventDescriptionTitle, eventDescriptionLabel, eventLocationTitle,
                                    eventLocationLabel, eventOrganiserTitle, eventOrganiserLabel};

                            for (int i = 0; i < labels.length; i++) {
                                // Check if the index is even, which means every other label becomes bold
                                if (i % 2 == 0) {
                                    labels[i].setFont(Font.font("Eras Bold ITC", 17));
                                } else {
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

                                // Open the event page of the created event
                                controller.loadEventPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);

                                // Close the list window
                                Scene scene = vbox.getScene();
                                Window window = scene.getWindow();
                                window.hide();
                                isWindowOpen = false;
                            });

                        }
                    }
                });
//TODO: IF OPEN DONT OPEN
                Scene scene = new Scene(listView, 600, 700);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                isWindowOpen = true;

            });
        }

        Stop[] stops = new Stop[] {
            new Stop(0, Color.web("#ECFF79")),
            new Stop(1, Color.web("#ECFF79", 0.5))
        };

        // Create the RadialGradient and set it as the background fill for the VBox
        RadialGradient gradient = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, stops);
        calendarActivityBox.setBackground(new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY)));

        // Set other properties for the VBox as needed
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.99);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.70);
        stackPane.getChildren().add(calendarActivityBox);
        calendarActivityBox.toBack();

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