package com.dashapp.diabeticsystem.controllers.diabetologo;

import com.dashapp.diabeticsystem.DAO.implementations.DiabetologoDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.InformazionPazienteDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.LoginDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.PazienteDaoImpl;
import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.models.*;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.*;

public class DashboardDiabetologoController {


    private final DiabetologoDaoImpl  diabetologoDao = new DiabetologoDaoImpl();
    private final Diabetologo diabetologo = diabetologoDao.getDiabetologoById(Session.getCurrentUser().getId_diabetologo()) ;
    private final PazienteDaoImpl pazienteDao = new PazienteDaoImpl();
    private final LoginDaoImpl loginDao = new LoginDaoImpl();
    private final InformazionPazienteDaoImpl informazionPazienteDao = new InformazionPazienteDaoImpl();

    // componenti effettivi della dashboard
    @FXML private Pane borderPane;
    @FXML private Label benvenuto ;
    @FXML private ToggleGroup gruppoSesso;

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


        SettingsController.setLogin(Session.getCurrentUser());
        benvenuto.setText("Bentornat" + (diabetologo.getSesso().equals("M") ? "o" : "a") + " " + diabetologo);
        Platform.runLater(() -> {
            Map<Paziente, List<Insulina>> mappa = diabetologo.notifyBloodSugar();

            if (!mappa.isEmpty()) {

                // Usa dei Set per memorizzare i pazienti unici per ogni livello di gravità
                Set<Paziente> pazientiCritici = new HashSet<>();
                Set<Paziente> pazientiLievi = new HashSet<>();

                for (Map.Entry<Paziente, List<Insulina>> entry : mappa.entrySet()) {
                    Paziente paziente = entry.getKey();
                    List<Insulina> glicemieErrate = entry.getValue();

                    for (Insulina insulina : glicemieErrate) {
                        if (insulina.getGravita() == GRAVITA.CRITICA) {
                            pazientiCritici.add(paziente);
                        } else {
                            pazientiLievi.add(paziente);
                        }
                    }
                }

                // Costruisci i messaggi finali solo se i Set non sono vuoti
                if (!pazientiCritici.isEmpty()) {
                    StringBuilder message1 = new StringBuilder("Pazienti con glicemie critiche: \n");
                    for (Paziente p : pazientiCritici) {
                        message1.append(p).append("\n");
                    }
                    Utility.createAlert(Alert.AlertType.ERROR, "" + message1);
                }

                if (!pazientiLievi.isEmpty()) {
                    StringBuilder message2 = new StringBuilder("Pazienti con glicemie lievemente fuori norma: \n");
                    for (Paziente p : pazientiLievi) {
                        message2.append(p).append("\n");
                    }
                    Utility.createAlert(Alert.AlertType.WARNING, "" + message2);
                }
            }



            List<Paziente> pazientiSbadati = diabetologo.notifyPatient();
            if(!pazientiSbadati.isEmpty())
                Utility.createAlert(Alert.AlertType.WARNING,pazientiSbadati + " è 3 giorni che non " + (pazientiSbadati.size() > 1 ? "assumono "  : "assume " ) +  "medicine ");


        });

    }

    /**
     * Funzione per controllare l'evento della registrazione del nuovo paziente assegnato direttamente dal dottore.
     * 
     */
    public void handleNuovoPaziente() throws SQLException, InterruptedException {

        if(!Utility.isEmailValid(this.textEmail.getText()) || !Utility.isCodiceFiscaleValid(this.textCodiceFiscale.getText()) || !Utility.checkObj(gruppoSesso.getSelectedToggle())
            || !Utility.checkDateNascita(this.dataNascitaPicker.getValue())
            || !Utility.checkOnlyLetters(this.textNome.getText()) || !Utility.checkOnlyLetters(this.textCognome.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi");
            return;
        }


        String sesso =  (String) ((RadioButton)gruppoSesso.getSelectedToggle()).getUserData();
        boolean success = false;
        int id_paziente = pazienteDao.insertPatient(
                new Paziente(
                        textNome.getText(),textCognome.getText(),textEmail.getText(),textCodiceFiscale.getText(), dataNascitaPicker.getValue(),sesso,diabetologo
                )
        );
        System.out.println("id_paziente: " + id_paziente);
        if(id_paziente > 0){

            CredentialsGenerator c = new CredentialsGenerator(id_paziente,0,textNome.getText(),textCognome.getText());
            System.out.println(c.createUsername() + " " + " "  +  c.generatePassword());
            success = loginDao.insertLogin(
                    new Login(
                            c.createUsername(),c.generatePassword(),id_paziente,null
                    )
            );


            if(success){

                success = informazionPazienteDao.insertInformation(id_paziente,
                        new InformazioniPaziente(
                                this.textFattoriRischio.getText(), this.textCommorbita.getText(), this.textPatologiePregresse.getText(), this.textPatologieConcomitanza.getText()
                        ));
            }
        }

        if(id_paziente <= 0 || !success){
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nella creazione del paziente");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Nuovo paziente aggiunto ");
        Utility.resetField(borderPane);





    }
}
