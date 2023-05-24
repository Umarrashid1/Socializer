package com.group.p2_socializer.Users;

import com.group.p2_socializer.Database.UserDB;
import com.group.p2_socializer.Utils.PopUpMessage;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class RegisterFormController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField rePasswordTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private DatePicker dateOfBirthTextField;

    @FXML
    private JFXButton registerButton;

    @FXML
    private Label registerMessageLabel;

    @FXML
    void registerButtonHandler() throws SQLException, NoSuchAlgorithmException {

        String currentUser = usernameTextField.getText();
        String currentPass = passwordTextField.getText();

        String rePassword = rePasswordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName =  lastNameTextField.getText();
        String country = countryTextField.getText();
        String city = cityTextField.getText();
        String email = emailTextField.getText();
        //dateOfBirthTextField.get

        //Check if password contains at least 8 characters and 1 digit
        if (currentPass.equals(rePassword) && currentPass.length() >= 8 && currentPass.matches(".*\\d.*")) {

            if (UserDB.registerUser(currentUser, currentPass, firstName, lastName, country, city, email)) {
                String message = "User registered";
                PopUpMessage popUpMessage = new PopUpMessage();
                popUpMessage.showCreatedPopUp(message);

                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();

            } else {
                registerMessageLabel.setText("Username already taken");

            }
        } else {
            registerMessageLabel.setText("Password needs to contain at least 8 characters and 1 number");
        }
    }


}
