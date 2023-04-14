package com.group.p2_socializer.UserLogIn;

import com.group.p2_socializer.Database.UserDB;
import com.group.p2_socializer.Socializer;
import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private Label loginMessage;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    protected void onLoginButtonClick() throws SQLException, IOException, NoSuchAlgorithmException {
        String currentUser = username.getText();
        String currentPass = password.getText();
        //Get user input from login form
        // Call database to authenticate login, returns null value if auth fails.

        User user = UserDB.authLogin(currentUser,currentPass);

        if(user != null){
            Scene scene = username.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader fxmlLoader = new FXMLLoader(Socializer.class.getResource("main_window.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Socializer");
            stage.setScene(scene);
            stage.show();

            double centerX = ScreenUtils.getScreenCenterX() - stage.getWidth() / 2;
            double centerY = ScreenUtils.getScreenCenterY() - stage.getHeight() / 2;
            stage.setX(centerX);
            stage.setY(centerY);

            //change scene if method returns non-null value
        }else{
            loginMessage.setText("Wrong username or password");
        }
    }
    @FXML
    protected void onRegisterButtonClick() throws SQLException, NoSuchAlgorithmException {
        String currentUser = username.getText();
        String currentPass = password.getText();

        if( UserDB.registerUser(currentUser,currentPass)){
            loginMessage.setText("User registered");
        }else{
            loginMessage.setText("Username already taken");
        }
        // Call registerUser method, returns null if username taken


    }
}