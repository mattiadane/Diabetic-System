package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardPazienteController   {

    @FXML private Label benvenuto;
    private final Paziente paziente = new Paziente();



    public void initialize() {
        this.benvenuto.setText("Bentornato: " + paziente);
        SettingsController.setPersona(paziente);
    }


}
