package com.dashapp.diabeticsystem.controllers;


import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import javax.swing.*;


public class TerapieController {

    @FXML private BorderPane borderPane;
    @FXML private  TextField quantita;
    @FXML private  TextField unita;
    @FXML private  TextField quanto;
    @FXML private  DatePicker data_fine;
    @FXML private  TextArea descrizione;
    @FXML private TextField codice_fiscale;
    @FXML private ComboBox<Farmaco> medicinale;
    @FXML private ComboBox<PERIODICITA> periodicita;
    @FXML private DatePicker data_inizio;


    private Diabetologo diabetologo;

    public void initialize(){
        this.diabetologo = new Diabetologo();
        medicinale.getItems().addAll(Terapia.getAllDrug());
        periodicita.getItems().addAll(PERIODICITA.values());
    }

    public void aggiungiTerapia() {

        if(!Utility.checkObj(periodicita.getValue()) || !Utility.checkObj(medicinale.getValue())
            || !Utility.checkOnlyLetters(unita.getText()) || !Utility.checkOnlyNumbers(quantita.getText()) || !Utility.checkOnlyNumbers(quanto.getText())
                || !Utility.isCodiceFiscaleValid(codice_fiscale.getText()) || !Utility.checkDates(data_inizio.getValue(),data_fine.getValue())
        ) {
            Utility.createAlert(Alert.AlertType.ERROR,"Errore nel inserimento dei dati");
            return;
        }

        boolean success = diabetologo.insersciTerapia(new Terapia(
                Integer.parseInt(quanto.getText()),periodicita.getValue(),Double.parseDouble(quantita.getText()),unita.getText(),
                data_inizio.getValue(),data_fine.getValue(),descrizione.getText()
        ),codice_fiscale.getText().toUpperCase().trim(),medicinale.getValue().toString());

        if(!success){
            System.out.println("errore database");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Terapia aggiunta correttamente");
        Utility.resetField(borderPane);

    }
}
