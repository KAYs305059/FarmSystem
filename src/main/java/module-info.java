module org.khadsdev {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.khadsdev to javafx.fxml;
    exports org.khadsdev;
}