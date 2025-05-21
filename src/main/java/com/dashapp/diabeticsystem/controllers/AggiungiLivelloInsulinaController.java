package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AggiungiLivelloInsulinaController {

    @FXML BorderPane borderpane;
    @FXML private ComboBox<PERIODO> comboBoxMomento;
    @FXML private TextField textLivello;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeText;
    private Paziente paziente;



    public void initialize(){
        this.paziente = new Paziente();
        comboBoxMomento.getItems().setAll(PERIODO.values());
    }
    /**
     * Funzione che permette di controllare l'evento di aggiunta del livello di insulina
     *
     */
    public void handleAggiungiLivello(){
        if(!Utility.checkInsulina(textLivello.getText()) || !Utility.checkTime(this.timeText.getText()) || !Utility.checkPeriodo((PERIODO) comboBoxMomento.getValue()) || !Utility.checkDate(datePicker.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Input non valido");
            alert.showAndWait();
            return;
        }

        LocalDateTime localDateTime = LocalDateTime.of(datePicker.getValue(),LocalTime.parse(timeText.getText()));
        boolean success = paziente.aggiungiLivelloInsulina(new Insulina(Integer.parseInt(textLivello.getText()),comboBoxMomento.getValue(),localDateTime));

        if(!success){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Errore nell'inserimento dell'indice glicemico");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText("Livello glicemico inserito correttamente");
        alert.showAndWait();


        Utility.resetField(borderpane);
    }


}
