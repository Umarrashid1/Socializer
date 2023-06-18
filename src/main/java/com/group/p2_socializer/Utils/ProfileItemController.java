package com.group.p2_socializer.Utils;

import com.group.p2_socializer.UserLogIn.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ProfileItemController {
    @FXML
    private ImageView profilePic;
    @FXML
    Label profileNameLabel;


    public void setProfileNameLabel(User profileUser) {
        String initials = profileUser.getFirstname().charAt(0) + "" + profileUser.getLastname().charAt(0);
        profileNameLabel.setText(initials);

        String username = profileUser.getUsername();
        Image image;

        switch (username) {
            case "r":
                image = new Image(getClass().getResource("/com/group/p2_socializer/pictures/Benedict-Cumberbatch.png").toExternalForm());
                break;
            case "Emma":
                image = new Image(getClass().getResource("/com/group/p2_socializer/pictures/blondgal.png").toExternalForm());
                break;
            case "Melissa":
                image = new Image(getClass().getResource("/com/group/p2_socializer/pictures/yas.jpg").toExternalForm());
                break;
            case "James":
                image = new Image(getClass().getResource("/com/group/p2_socializer/pictures/lad.png").toExternalForm());
                break;
            case "Emily":
                image = new Image(getClass().getResource("/com/group/p2_socializer/pictures/ladya.png").toExternalForm());
                break;
            default:
                image = new Image(getClass().getResource("/com/group/p2_socializer/pictures/Poster-sized_portrait_of_Barack_Obama-modified.png").toExternalForm());
                break;
        }

        profilePic.setImage(image);
    }
}
