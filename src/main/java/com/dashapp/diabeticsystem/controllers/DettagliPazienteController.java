package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DettagliPazienteController {

    private final Paziente paziente;

    // componenti FXML
    @FXML private Label main_label;
    @FXML private TableView<Terapia> tabella_terapie;
    @FXML private TableColumn<Farmaco, String> col_nome;
    @FXML private TableColumn<Farmaco, String> col_descricao;

    public DettagliPazienteController(Paziente paziente) {
        if(paziente == null)
            Utility.createAlert(Alert.AlertType.ERROR, "Paziente passato nullo oppure non valido.");
        this.paziente = paziente;
    }

    public void initialize(){
        main_label.setText("Paziente: " + paziente);
    }
}
