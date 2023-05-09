package com.group.p2_socializer.Utils;

import com.group.p2_socializer.UserLogIn.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class ProfileItemController {
    @FXML
    Label ProfileNameLabel;
    User profileUser;


    public void setProfileNameLabel(){
        int first = profileUser.getFirstname().charAt(0);
        int last = profileUser.getLastname().charAt(0);
        ProfileNameLabel.setText(String.valueOf(first+last));
    }
    public void setProfileUser(User profileUser){
        this.profileUser = profileUser;
    }

}
