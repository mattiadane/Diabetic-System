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
    requires javafx.base;


    opens com.dashapp.diabeticsystem to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers to javafx.fxml;
    opens com.dashapp.diabeticsystem.view to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.components to javafx.fxml;
    opens com.dashapp.diabeticsystem.models to javafx.fxml;
    opens com.dashapp.diabeticsystem.utility to javafx.fxml;
    opens com.dashapp.diabeticsystem.enums to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.admin to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.paziente to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.diabetologo to javafx.fxml;

    exports com.dashapp.diabeticsystem;
    exports com.dashapp.diabeticsystem.controllers;
    exports com.dashapp.diabeticsystem.view;
    exports com.dashapp.diabeticsystem.models;
    exports com.dashapp.diabeticsystem.utility;
    exports com.dashapp.diabeticsystem.enums;
    exports com.dashapp.diabeticsystem.controllers.admin;
    exports com.dashapp.diabeticsystem.controllers.paziente;
    exports com.dashapp.diabeticsystem.controllers.diabetologo;
    exports com.dashapp.diabeticsystem.controllers.components;
    exports com.dashapp.diabeticsystem.DAO.implementations;
}