module com.example.aj {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.aj to javafx.fxml;
    opens com.example.aj.Controllers to javafx.base, javafx.fxml;
    opens com.example.aj.Entities to javafx.base, javafx.fxml;
    opens com.example.aj.Services to javafx.base, javafx.fxml;
    exports com.example.aj;
    exports com.example.aj.Controllers;
}