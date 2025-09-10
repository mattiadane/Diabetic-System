package com.dashapp.diabeticsystem.controllers.diabetologo;

import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.*;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.scene.control.*;
import javafx.fxml.FXML;

public class ModificaTerapiaController {

    private Terapia terapia;
    private Diabetologo diabetologo;
    private Paziente paziente;


    @FXML private ComboBox<Farmaco> medicinale;
    @FXML private TextField quantita;
    @FXML private TextField unita;
    @FXML private TextField quanto;
    @FXML private ComboBox<PERIODICITA> periodicita;
    @FXML private DatePicker data_inizio;
    @FXML private DatePicker data_fine;
    @FXML private TextArea descrizione;

    public void initialize(){
        diabetologo = new Diabetologo();
        medicinale.setItems(Terapia.getAllDrug());
        periodicita.getItems().addAll(PERIODICITA.values());
        periodicita.getSelectionModel().selectFirst();

    }


    /**
     * Funzione che permette di caricare i dati quando viene aperta la finestra dei dettagli della terapia
     * @param t terapia da controllare
     */
    public void loadData(Terapia t,Paziente p){
        this.terapia = t;
        this.paziente = p;


        medicinale.setValue(terapia.getFarmaco());
        quantita.setText(String.valueOf(terapia.getDosaggio_quantita()));
        unita.setText(terapia.getDosaggio_unita());
        quanto.setText(String.valueOf(terapia.getQuanto()));
        periodicita.setValue(terapia.getPeriodicita());
        data_inizio.setValue(terapia.getData_inizio());
        data_fine.setValue(terapia.getData_fine());
        descrizione.setText(terapia.getDescrizione());

    }

    /**
     * Funzione che permette di gestire l'evento generato dal bottone per modificare la terapia con nuovi valori.
     */
    public void modificaTerapia(){
        if(!Utility.checkObj(periodicita.getValue()) || !Utility.checkObj(medicinale.getValue())
                || !Utility.checkOnlyLetters(unita.getText()) || !Utility.checkOnlyNumbers(quantita.getText()) || !Utility.checkOnlyNumbers(quanto.getText())
                || !Utility.checkDates(data_inizio.getValue(),data_fine.getValue())){
            Utility.createAlert(Alert.AlertType.ERROR,"Errore nel inserimento dei dati");
            return;
        }

        Terapia newTerapia = new Terapia(
                Integer.parseInt(quanto.getText()), periodicita.getValue(), Double.parseDouble(quantita.getText()), unita.getText(),
                data_inizio.getValue(), data_fine.getValue(), descrizione.getText()
        );

        newTerapia.setId_terapia(this.terapia.getId_terapia());
        newTerapia.setFarmaco(medicinale.getValue());

        if(!this.diabetologo.updateTerapia(newTerapia)){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nella modifica dei dati della terapia.");
            return;
        }
        Utility.createAlert(Alert.AlertType.INFORMATION, "Dati della terapia modificati correttamente.");
        paziente.updateTerapia(newTerapia);

    }

}
