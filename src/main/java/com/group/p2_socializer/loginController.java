package com.group.p2_socializer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class loginController {
    @FXML
    private Label result;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    protected void onLoginButtonClick() throws SQLException, IOException {
        String currentUser = username.getText();
        String currentPass = password.getText();
        //Get user input from login form
        User user = loginDB.authLogin(currentUser,currentPass);
        // Call database to authenticate login, returns null value if auth fails.
        if(user != null){
            Scene scene = result.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader fxmlLoader = new FXMLLoader(Socializer.class.getResource("calendar-view.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Socializer");
            stage.setScene(scene);
            stage.show();
            //change scene if method returns non-null value
        }else{
            result.setText("Wrong username or password");
        }
    }
    @FXML
    protected void onRegisterButtonClick() throws SQLException {
        String currentUser = username.getText();
        String currentPass = password.getText();

        if( loginDB.registerUser(currentUser,currentPass)){
            result.setText("User registered");
        }else{
            result.setText("Username already taken");
        }
        // Call registerUser method, returns null if username taken


    }
}