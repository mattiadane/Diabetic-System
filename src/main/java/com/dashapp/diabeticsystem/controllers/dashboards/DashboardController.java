package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Login;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class  DashboardController{

    public  abstract void initData(Login user) ;

    public void logout(ActionEvent event) throws IOException {
        Main.getStage(new Stage(),"fxml/login.fxml","Diabetic System");
        ((Node)(event.getSource())).getScene().getWindow().hide();

    }
}
