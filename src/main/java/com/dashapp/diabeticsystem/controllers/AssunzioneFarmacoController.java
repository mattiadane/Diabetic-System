package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.AssunzioneFarmaco;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssunzioneFarmacoController {


    @FXML private BorderPane pane;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<Farmaco> medicinali;
    @FXML private TextField dosaggioText;
    @FXML private TextField unitaText;
    @FXML private TextArea sintomiArea;
    @FXML private TextField oraText;
    private Paziente paziente;



    public void initialize(){
        this.paziente = new Paziente();
        medicinali.getItems().addAll(paziente.loadFarmaciByPaziente());
    }


    public void handleAssunzione() {



        if(!Utility.checkObj((medicinali.getValue())) || !Utility.checkOnlyNumbers(dosaggioText.getText()) || !Utility.checkOnlyLetters(unitaText.getText())
                || !Utility.checkObj(datePicker.getValue())|| !Utility.checkTime(oraText.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Input non valido");
            return;
        }




        LocalDateTime date = LocalDateTime.of(datePicker.getValue(), LocalTime.parse(oraText.getText()));

        Terapia t = paziente.loadTeriapiaByFarmaco(medicinali.getValue());

        LocalDate inizio = t.getData_inizio(),fine = t.getData_fine();

        if(Utility.checkDataIsCompresa(inizio,fine,datePicker.getValue())){
            if(paziente.sommaDosaggioAssunzioneFarmaco(medicinali.getValue(),date) + Double.parseDouble(dosaggioText.getText()) <= paziente.sommaDosaggioTerapia(medicinali.getValue())) {
                boolean success = paziente.inserisciAssunzioneFarmaco(
                        new AssunzioneFarmaco(
                                medicinali.getValue(),unitaText.getText(),Double.parseDouble(dosaggioText.getText()),sintomiArea.getText(),date
                        )
                );


                if(!success){
                    Utility.createAlert(Alert.AlertType.ERROR, "Errore nell'inserimento dell'assunzione farmaco");
                    return;
                }

                Utility.createAlert(Alert.AlertType.INFORMATION, "Assunzione farmaco inserita correttamente");
            } else {
                Utility.createAlert(Alert.AlertType.ERROR, "Non puoi assumere un dosaggio maggiore di " + paziente.sommaDosaggioTerapia(medicinali.getValue()) + unitaText.getText()
                        + " nella terapia di " + medicinali.getValue().getNome() + " durante " + (t.getPeriodicita() == PERIODICITA.SETTIMANA ? " la " : " il ")  + t.getPeriodicita().toString()  );
            }

        } else {
            Utility.createAlert(Alert.AlertType.ERROR, "Non puoi assumere " + t.getFarmaco().toString() + " fuori dal periodo della terapia ( "+ t.getData_inizio().toString() + " - " + t.getData_fine().toString() + " )" );

        }



        Utility.resetField(pane);





    }



}
