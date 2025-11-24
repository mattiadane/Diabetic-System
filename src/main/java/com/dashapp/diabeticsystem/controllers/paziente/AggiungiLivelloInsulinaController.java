package com.dashapp.diabeticsystem.controllers.paziente;

import com.dashapp.diabeticsystem.DAO.implementations.InsulinaDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.PazienteDaoImpl;
import com.dashapp.diabeticsystem.DAO.interfaces.InsulinaDao;
import com.dashapp.diabeticsystem.DAO.interfaces.PazienteDao;
import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Session;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AggiungiLivelloInsulinaController {

    private final InsulinaDao insulinaDao = new InsulinaDaoImpl();
    private final PazienteDao pazienteDao =  new PazienteDaoImpl();
    private final Paziente paziente = pazienteDao.getPatientById(Session.getCurrentUser().getId_paziente());

    @FXML BorderPane borderpane;
    @FXML private ComboBox<PERIODO> comboBoxMomento;
    @FXML private TextField textLivello;
    @FXML private TextField timeText;
    @FXML private TextArea sintomiArea;



    public void initialize(){
        comboBoxMomento.getItems().setAll(PERIODO.values());
    }

    /**
     * Funzione che permette di controllare l'evento di aggiunta del livello di insulina.
     */
    public void handleAggiungiLivello(){
        if(insulinaDao.countDailyInsulinaByPatient(paziente) > 5){
            Utility.createAlert(Alert.AlertType.ERROR, "Hai già inserito tutte le misurazioni giornaliere");
            Utility.resetField(borderpane);
            return;
        }



        if(!Utility.checkInsulina(textLivello.getText()) || !Utility.checkTime(this.timeText.getText()) || !Utility.checkObj( comboBoxMomento.getValue())
        ) {
            Utility.createAlert(Alert.AlertType.ERROR, "Input non valido");
            return;
        }


        int c = insulinaDao.coundDailyMomentOfDay(comboBoxMomento.getValue(),paziente);
        if(c == 1){
            Utility.createAlert(Alert.AlertType.ERROR, "Puoi inserire solo 1 misurazione nel periodo : " + comboBoxMomento.getValue().toString());
            return;
        }

        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(),LocalTime.parse(timeText.getText()));
        boolean success = insulinaDao.insertInsulina(
                new Insulina(Integer.parseInt(textLivello.getText()),comboBoxMomento.getValue(),localDateTime,sintomiArea.getText(),paziente)
        );

        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nell'inserimento dell'indice glicemico");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Livello glicemico inserito correttamente");
        Utility.resetField(borderpane);
    }
}
