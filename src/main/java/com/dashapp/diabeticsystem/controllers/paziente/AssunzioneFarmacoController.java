package com.dashapp.diabeticsystem.controllers.paziente;

import com.dashapp.diabeticsystem.DAO.implementations.AssunzioneFarmacoDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.FarmacoDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.PazienteDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.TerapiaDaoImpl;
import com.dashapp.diabeticsystem.DAO.interfcaes.AssunzioneFarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.FarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.PazienteDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.TerapiaDao;
import com.dashapp.diabeticsystem.models.*;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssunzioneFarmacoController {

    private final AssunzioneFarmacoDao assunzioneFarmacoDao = new AssunzioneFarmacoDaoImpl();
    private final FarmacoDao farmacoDao = new FarmacoDaoImpl();
    private final TerapiaDao terapiaDao = new TerapiaDaoImpl();
    private final PazienteDao pazienteDao = new PazienteDaoImpl();
    private final Paziente paziente = pazienteDao.getPatientById(Session.getCurrentUser().getId_paziente());


    @FXML private BorderPane pane;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Farmaco> medicinali;
    @FXML private TextField dosaggioText;
    @FXML private TextField unitaText;
    @FXML private TextArea sintomiArea;
    @FXML private TextField oraText;




    public void initialize(){
        medicinali.getItems().addAll(farmacoDao.getAllDrugsByPaziente(paziente));
    }


    public void handleAssunzione() {



        if(!Utility.checkObj((medicinali.getValue())) || !Utility.checkOnlyNumbers(dosaggioText.getText()) || !Utility.checkOnlyLetters(unitaText.getText())
                || !Utility.checkObj(datePicker.getValue())|| !Utility.checkTime(oraText.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Input non valido");
            return;
        }




        LocalDateTime date = LocalDateTime.of(datePicker.getValue(), LocalTime.parse(oraText.getText()));
        Terapia t = terapiaDao.getTherapyByFarmacoIdAndPatient(paziente, medicinali.getValue());


        LocalDate inizio = t.getData_inizio(),fine = t.getData_fine();

        if(Utility.checkDataIsCompresa(inizio,fine,datePicker.getValue()) && datePicker.getValue().isBefore(LocalDate.now().plusDays(1))) {
            if(assunzioneFarmacoDao.totalDailyDosageTakingDrug(paziente,medicinali.getValue(),date) + Double.parseDouble(dosaggioText.getText()) <= (t.getQuanto() * t.getDosaggio_quantita())) {
                boolean success = assunzioneFarmacoDao.insertTakingDrug(
                        new AssunzioneFarmaco(
                                medicinali.getValue(),unitaText.getText(),Double.parseDouble(dosaggioText.getText()),sintomiArea.getText(),date,paziente
                        )
                );


                if(!success){
                    Utility.createAlert(Alert.AlertType.ERROR, "Errore nell'inserimento dell'assunzione farmaco");
                    return;
                }

                Utility.createAlert(Alert.AlertType.INFORMATION, "Assunzione farmaco inserita correttamente");

            } else {
                Utility.createAlert(Alert.AlertType.ERROR, "Non puoi assumere un dosaggio maggiore di " + (t.getQuanto() * t.getDosaggio_quantita()) + " " + unitaText.getText()
                        + " nella terapia di " + medicinali.getValue().getNome() + " durante il " + t.getPeriodicita().toString()  );
                return ;
            }

        } else {
            Utility.createAlert(Alert.AlertType.ERROR, "Non puoi assumere " + t.getFarmaco().toString() + " fuori dal periodo della terapia ( "+ t.getData_inizio().toString() + " - " + t.getData_fine().toString() + " ) o aggiungere assunzioni nei giorni successivi ad oggi" );
            return;
        }



        Utility.resetField(pane);





    }



}
