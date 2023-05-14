package com.group.p2_socializer.UserLogIn;

import com.group.p2_socializer.Database.UserDB;
import com.group.p2_socializer.Socializer;
import com.group.p2_socializer.Tabs.TabController;
import com.group.p2_socializer.Users.RegisterFormController;
import com.group.p2_socializer.Utils.ScreenUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginController {
    public Button registerButton;
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
            TabController controller = fxmlLoader.getController();
            controller.setCurrentUser(user);
            //stage.setUserData(user);
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
    protected void onRegisterButtonClick() throws IOException {

        FXMLLoader registerFormFxmlLoader = new FXMLLoader(RegisterFormController.class.getResource("/com/group/p2_socializer/register_form.fxml"));
        Parent registerFormRoot = registerFormFxmlLoader.load();

        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(new Scene(registerFormRoot));
        stage.show();

    }
}

