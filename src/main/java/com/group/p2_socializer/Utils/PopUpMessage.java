package com.group.p2_socializer.Utils;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PopUpMessage {


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
