package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardDiabetologoController extends DashboardController {



    public void initData(Login user) {

        System.out.println(user);
    }


}
