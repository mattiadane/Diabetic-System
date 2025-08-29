module com.dashapp.diabeticsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires java.compiler;
    requires java.management;
    requires java.desktop;
    requires javafx.graphics;
    requires jdk.compiler;

    opens com.dashapp.diabeticsystem to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers to javafx.fxml;
    opens com.dashapp.diabeticsystem.view to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.components to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.dashboards to javafx.fxml;
    opens com.dashapp.diabeticsystem.models to javafx.fxml;
    opens com.dashapp.diabeticsystem.utility to javafx.fxml;
    opens com.dashapp.diabeticsystem.enums to javafx.fxml;

    exports com.dashapp.diabeticsystem;
    exports com.dashapp.diabeticsystem.controllers;
    exports com.dashapp.diabeticsystem.view;
    exports com.dashapp.diabeticsystem.models;
    exports com.dashapp.diabeticsystem.utility;
    exports com.dashapp.diabeticsystem.enums;
}