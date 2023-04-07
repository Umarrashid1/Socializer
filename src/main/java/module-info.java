module com.group.p2_socializer {
    requires javafx.controls;
    requires javafx.fxml;

    // requires org.controlsfx.controls; har slettet dependency, den skal måske tilbage igen
    requires com.jfoenix;
    requires java.sql;
    requires transitive javafx.graphics;

    opens com.group.p2_socializer to javafx.fxml;
    opens com.group.p2_socializer.Pages to javafx.fxml;
    exports com.group.p2_socializer;
    exports com.group.p2_socializer.Calendar;
    opens com.group.p2_socializer.Calendar to javafx.fxml;
    exports com.group.p2_socializer.UserLogIn;
    opens com.group.p2_socializer.UserLogIn to javafx.fxml;
    exports com.group.p2_socializer.Tabs;
    opens com.group.p2_socializer.Tabs to javafx.fxml;
    exports com.group.p2_socializer.CreateGatherings;
    opens com.group.p2_socializer.CreateGatherings to javafx.fxml;
}
