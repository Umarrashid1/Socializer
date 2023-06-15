package com.group.p2_socializer.Utils;

import com.group.p2_socializer.UserLogIn.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;


public class ProfileItemController {

    @FXML
    public Image image;
    @FXML
    Label profileNameLabel;


    public void setProfileNameLabel(User profileUser){
        String initials = profileUser.getFirstname().charAt(0) + "" + profileUser.getLastname().charAt(0);
        profileNameLabel.setText(initials);
    }

}
