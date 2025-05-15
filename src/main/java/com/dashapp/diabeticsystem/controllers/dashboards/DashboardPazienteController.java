package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardPazienteController extends DashboardController   {

    @FXML private Label benvenuto;



    public void initialize() {
        this.benvenuto.setText("Bentornato: " + Session.getCurrentUser());
    }


}
