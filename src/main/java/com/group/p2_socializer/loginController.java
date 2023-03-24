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
    protected void onLoginButtonClick() throws SQLException, ClassNotFoundException, IOException {
        String currentUser = username.getText();
        String currentPass = password.getText();

        User user = db.authLogin(currentUser,currentPass);

        if(user != null){
            Scene scene = result.getScene();
            Window window = scene.getWindow();
            Stage stage = (Stage) window;
            FXMLLoader fxmlLoader = new FXMLLoader(Socializer.class.getResource("calendar-view.fxml"));
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Socializer");
            stage.setScene(scene);
            stage.show();
        }else{
            result.setText("Wrong username or password");
        }
    }
    @FXML
    protected void onRegisterButtonClick() throws SQLException {
        String currentUser = username.getText();
        String currentPass = password.getText();

        if( db.registerUser(currentUser,currentPass)){
            result.setText("User registered");
        }else{
            result.setText("Username already taken");
        }



    }
}