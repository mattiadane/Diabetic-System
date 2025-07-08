package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AggiungiLivelloInsulinaController {

    @FXML BorderPane borderpane;
    @FXML private ComboBox<PERIODO> comboBoxMomento;
    @FXML private TextField textLivello;
    @FXML private TextField timeText;
    private Paziente paziente;



    public void initialize(){
        this.paziente = new Paziente();
        comboBoxMomento.getItems().setAll(PERIODO.values());
    }

    /**
     * Funzione che permette di controllare l'evento di aggiunta del livello di insulina.
     */
    public void handleAggiungiLivello(){
        if(paziente.countInsulinaGiornaliero() > 5){
            Utility.createAlert(Alert.AlertType.ERROR, "Hai già inserito tutte le misurazioni giornaliere");
            Utility.resetField(borderpane);
            return;
        }



        if(!Utility.checkInsulina(textLivello.getText()) || !Utility.checkTime(this.timeText.getText()) || !Utility.checkObj( comboBoxMomento.getValue())
        ) {
            Utility.createAlert(Alert.AlertType.ERROR, "Input non valido");
            return;
        }

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(),LocalTime.parse(timeText.getText()));
        boolean success = paziente.aggiungiLivelloInsulina(new Insulina(Integer.parseInt(textLivello.getText()),comboBoxMomento.getValue(),localDateTime));

        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nell'inserimento dell'indice glicemico");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Livello glicemico inserito correttamente");
        Utility.resetField(borderpane);
    }
}
