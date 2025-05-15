module com.dashapp.diabeticsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires java.compiler;

    opens com.dashapp.diabeticsystem to javafx.fxml;
    exports com.dashapp.diabeticsystem;
    exports com.dashapp.diabeticsystem.controllers;
    exports com.dashapp.diabeticsystem.View;

    opens com.dashapp.diabeticsystem.controllers to javafx.fxml;
    opens com.dashapp.diabeticsystem.View  to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.components to javafx.fxml;
    opens com.dashapp.diabeticsystem.controllers.dashboards to javafx.fxml;


}