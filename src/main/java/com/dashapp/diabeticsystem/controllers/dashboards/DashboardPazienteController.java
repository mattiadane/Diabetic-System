package com.dashapp.diabeticsystem.controllers.dashboards;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class DashboardPazienteController extends DashboardController   {

    @FXML private Label benvenuto;

    public DashboardPazienteController() {
        this.benvenuto.setText("Bentornato: " + Session.getCurrentUser());
    }

    public void showSettings(ActionEvent event){
        Main.getStage(new Stage(), "fxml/impostazioniDiabetologo.fxml", "Impostazioni diabetologo");
    }

}
