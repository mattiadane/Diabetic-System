package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.InformazioniPaziente;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class DashboardDiabetologoController {

    private final Diabetologo diabetologo = new Diabetologo();


    // componenti effettivi della dashboard
    @FXML private Pane borderPane;
    @FXML private Label benvenuto ;
    @FXML
    private ToggleGroup gruppoSesso;

    // componenti per il form per aggiungere un paziente
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;
    @FXML private TextField textFattoriRischio;
    @FXML private TextField textCommorbita;
    @FXML private TextField textPatologiePregresse;
    @FXML private TextField textPatologieConcomitanza;
    @FXML private DatePicker dataNascitaPicker;


    public void initialize() {
        SettingsController.setPersona(diabetologo);
        benvenuto.setText("Bentornat" + (diabetologo.getSesso().equals("M") ? "o" : "a") + " " + diabetologo);

    }

    /**
     * Funzione per controllare l'evento della registrazione del nuovo paziente assegnato direttamente dal dottore.
     * 
     */
    public void handleNuovoPaziente(){
        // controllo della validità dei campi inseriti dal dottore




        if(!Utility.isEmailValid(this.textEmail.getText()) || !Utility.isCodiceFiscaleValid(this.textCodiceFiscale.getText()) || !Utility.checkObj(gruppoSesso.getSelectedToggle())
            || !Utility.checkDateNascita(this.dataNascitaPicker.getValue())
            || !Utility.checkOnlyLetters(this.textNome.getText()) || !Utility.checkOnlyLetters(this.textCognome.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi");
            return;
        }


        String sesso =  (String) ((RadioButton)gruppoSesso.getSelectedToggle()).getUserData();
        InformazioniPaziente info = new InformazioniPaziente(this.textFattoriRischio.getText(), this.textCommorbita.getText(), this.textPatologiePregresse.getText(), this.textPatologieConcomitanza.getText());

        // eseguo la query di inserimento del nuovo paziente a database
        boolean success = diabetologo.inserisciPaziente(
                new Paziente(
                        textNome.getText(),textCognome.getText(),textEmail.getText(),textCodiceFiscale.getText(), dataNascitaPicker.getValue(),sesso
                ),
                info
        );

        // controllo dell'esito dell'inserimento del nuovo paziente a database e mostro un Alert dedicato
        if(!success){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nella creazione del paziente");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Nuovo paziente aggiunto ");
        Utility.resetField(borderPane);
    }
}
