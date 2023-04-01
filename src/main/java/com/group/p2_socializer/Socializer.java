package com.group.p2_socializer;

import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Socializer extends Application {
    @Override
    public void start(Stage mainStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Socializer.class.getResource("login_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Socializer");
        mainStage.setScene(scene);
        mainStage.show();
        double centerX = ScreenUtils.getScreenCenterX() - mainStage.getWidth() / 2;
        double centerY = ScreenUtils.getScreenCenterY() - mainStage.getHeight() / 2;
        mainStage.setX(centerX);
        mainStage.setY(centerY);
    }

    public static void main(String[] args) {
        launch();
    }
}