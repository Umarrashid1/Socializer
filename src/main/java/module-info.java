module com.group.p2_socializer{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires java.sql;

    exports com.group.p2_socializer;
    opens com.group.p2_socializer to javafx.fxml;

    exports com.group.p2_socializer.Calendar;
    opens com.group.p2_socializer.Calendar to javafx.fxml;
}
