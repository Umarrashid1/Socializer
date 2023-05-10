module com.group.p2_socializer {
    requires javafx.controls;
    requires javafx.fxml;

    // requires org.controlsfx.controls; har slettet dependency, den skal måske tilbage igen
    requires com.jfoenix;
    requires java.sql;
    requires transitive javafx.graphics;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.group.p2_socializer.Tabs to javafx.fxml;
    opens com.group.p2_socializer to javafx.fxml;
    opens com.group.p2_socializer.Pages to javafx.fxml;
    opens com.group.p2_socializer.CreateGatherings to javafx.fxml;
    opens com.group.p2_socializer.Calendar to javafx.fxml;
    opens com.group.p2_socializer.UserLogIn to javafx.fxml;
    opens com.group.p2_socializer.Users to javafx.fxml;
    opens com.group.p2_socializer.Utils;


    exports com.group.p2_socializer.Tabs;
    exports com.group.p2_socializer;
    exports com.group.p2_socializer.Calendar;
    exports com.group.p2_socializer.UserLogIn;
    exports com.group.p2_socializer.CreateGatherings;
    exports com.group.p2_socializer.Database;
    exports com.group.p2_socializer.Users;

    opens com.group.p2_socializer.Database to javafx.fxml;
    exports com.group.p2_socializer.activities;
    opens com.group.p2_socializer.activities to javafx.fxml;
    exports com.group.p2_socializer.Utils;

}
