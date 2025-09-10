package com.dashapp.diabeticsystem.controllers.diabetologo;


import com.dashapp.diabeticsystem.DAO.implementations.FarmacoDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.PazienteDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.TerapiaDaoImpl;
import com.dashapp.diabeticsystem.DAO.interfcaes.FarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.PazienteDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.TerapiaDao;
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


    private final FarmacoDao farmacoDao = new FarmacoDaoImpl();
    private final PazienteDao pazienteDao = new PazienteDaoImpl();
    private final TerapiaDao terapiaDao = new TerapiaDaoImpl();

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


    public void initialize(){

        medicinale.getItems().addAll(farmacoDao.getAllDrugs());
        periodicita.getItems().addAll(PERIODICITA.values());
        periodicita.getSelectionModel().selectFirst();

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

        boolean success = false;
        Farmaco farmaco = farmacoDao.getDrugByName(medicinale.getValue().toString());
        if(farmaco != null){
            Paziente paziente = pazienteDao.getPatientByCf(codice_fiscale.getText().toUpperCase().trim());
            if(paziente != null){
                Diabetologo diabetologo = paziente.getDiabetologo();
                if(diabetologo != null){
                    success = terapiaDao.insertTherapy(
                            new Terapia(
                                    Integer.parseInt(quanto.getText()), periodicita.getValue(), Double.parseDouble(quantita.getText()), unita.getText(),
                                    data_inizio.getValue(), data_fine.getValue(), descrizione.getText(),farmaco,diabetologo,paziente)
                    );
                }
            }
        }



        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR,"Errore");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Terapia aggiunta correttamente");
        Utility.resetField(borderPane);

    }
}
