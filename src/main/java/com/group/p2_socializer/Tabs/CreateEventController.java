package com.group.p2_socializer.Tabs;

import com.group.p2_socializer.Calendar.CalendarActivity;
import com.group.p2_socializer.Calendar.CalendarController;
import com.group.p2_socializer.Calendar.CalendarDB;
import com.group.p2_socializer.EventPage.EventPageController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CreateEventController {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

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

    boolean isWindowOpen = false;

    //TODO: Too intricate, split into methods
    public void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane, Rectangle rectangle) {
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
        //mainTabPane.getSelectionModel().select(calendarTab);

        EventPageController controller = new EventPageController();
        controller.loadEventPage(eventName, formattedDate, eventOrganiser, eventDescription, eventCity, eventCountry);

        String createdMessage = "Event Created!";
        CalendarController calendarController = new CalendarController();
        calendarController.showCreatedPopUp(createdMessage);

    }
}
