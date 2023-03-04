module com.example.lab4final {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.lab4final to javafx.fxml;
    exports com.example.lab4final;
    exports com.example.lab4final.controllers;
    exports com.example.lab4final.domain;
    opens com.example.lab4final.controllers to javafx.fxml;
}