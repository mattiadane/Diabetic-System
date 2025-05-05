module com.dashapp.diabeticsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.dashapp.diabeticsystem to javafx.fxml;
    exports com.dashapp.diabeticsystem;
    exports com.dashapp.diabeticsystem.controllers;
    opens com.dashapp.diabeticsystem.controllers to javafx.fxml;
}