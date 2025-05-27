package com.dashapp.diabeticsystem.controllers;


import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;


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

    /**
     * Funzione che permette di gestire l'evento generato dal bottone per aggiungere una terapia.
     */
    public void aggiungiTerapia() {
        if(!Utility.checkObj(periodicita.getValue()) || !Utility.checkObj(medicinale.getValue())
            || !Utility.checkOnlyLetters(unita.getText()) || !Utility.checkOnlyNumbers(quantita.getText()) || !Utility.checkOnlyNumbers(quanto.getText())
                || !Utility.isCodiceFiscaleValid(codice_fiscale.getText()) || !Utility.checkDates(data_inizio.getValue(),data_fine.getValue())
        ) {
            Utility.createAlert(Alert.AlertType.ERROR,"Errore nel inserimento dei dati");
            return;
        }
        Terapia terapia = new Terapia(
                Integer.parseInt(quanto.getText()), periodicita.getValue(), Double.parseDouble(quantita.getText()), unita.getText(),
                data_inizio.getValue(), data_fine.getValue(), descrizione.getText()
        );
        Farmaco farmaco = terapia.getFarmacoByName(medicinale.getValue().toString());
        Paziente paziente = diabetologo.getPazienteByCf(codice_fiscale.getText().toUpperCase().trim());


        boolean success = diabetologo.insersciTerapia(terapia, paziente, farmaco);

        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR,"Errore");
            return;
        }

        paziente.inserisciTerapia(terapia);
        Utility.createAlert(Alert.AlertType.INFORMATION, "Terapia aggiunta correttamente");
        Utility.resetField(borderPane);

    }
}
