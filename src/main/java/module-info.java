module com.example.localized_database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.localized_database to javafx.fxml;
    exports com.example.localized_database;
}