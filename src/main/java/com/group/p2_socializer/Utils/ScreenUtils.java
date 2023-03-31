package com.group.p2_socializer.Utils;

import javafx.stage.Screen;

public class ScreenUtils {

    public static double getScreenWidth() {
        return Screen.getPrimary().getBounds().getWidth();
    }

    public static double getScreenHeight() {
        return Screen.getPrimary().getBounds().getHeight();
    }

    public static double getScreenCenterX() {
        return Screen.getPrimary().getBounds().getWidth() / 2;
    }

    public static double getScreenCenterY() {
        return Screen.getPrimary().getBounds().getHeight() / 2;
    }

}