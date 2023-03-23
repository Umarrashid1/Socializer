module com.group.p2_socializer {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                            
    opens com.group.p2_socializer to javafx.fxml;
    exports com.group.p2_socializer;
}