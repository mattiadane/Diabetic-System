module com.dashapp.diabeticsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.dashapp.diabeticsystem to javafx.fxml;
    exports com.dashapp.diabeticsystem;
    exports com.dashapp.diabeticsystem.controllers;
    exports com.dashapp.diabeticsystem.controllers.sidebars;

    opens com.dashapp.diabeticsystem.controllers to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.sidebars to javafx.fxml;
}