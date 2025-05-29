package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class DashboardPazienteController   {

    @FXML private Label benvenuto;
    @FXML private ListView<Terapia> listaTerapie;

    private final Paziente paziente = new Paziente();


    public void initialize() {
        this.benvenuto.setText("Bentornato: " + paziente);
        SettingsController.setPersona(paziente);
        createTerapieList();
    }

    /**
     * Funzione che permette di popolare la lista delle terapie associate al paziente.
     */
    private void createTerapieList() {
        listaTerapie.setItems(paziente.getAllTerapie());

        listaTerapie.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Terapia item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : " ● " + item.toStringForList());
            }
        });
    }



}
