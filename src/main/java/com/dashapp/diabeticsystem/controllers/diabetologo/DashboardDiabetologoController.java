package com.dashapp.diabeticsystem.controllers.diabetologo;

import com.dashapp.diabeticsystem.DAO.implementations.*;
import com.dashapp.diabeticsystem.DAO.interfaces.AssunzioneFarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfaces.InsulinaDao;
import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.models.*;
import com.dashapp.diabeticsystem.utility.CredentialsGenerator;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardDiabetologoController {


    private final DiabetologoDaoImpl diabetologoDao = new DiabetologoDaoImpl();
    private final AssunzioneFarmacoDao assunzioneFarmacoDao = new AssunzioneFarmacoDaoImpl();
    private final Diabetologo diabetologo = diabetologoDao.getDiabetologistById(Session.getCurrentUser().getId_diabetologo());
    private final PazienteDaoImpl pazienteDao = new PazienteDaoImpl();
    private final InsulinaDao insulinaDao = new InsulinaDaoImpl();
    private final LoginDaoImpl loginDao = new LoginDaoImpl();
    private final InformazionPazienteDaoImpl informazionPazienteDao = new InformazionPazienteDaoImpl();

    // componenti effettivi della dashboard
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;

    @FXML
    private Label benvenuto;
    @FXML
    private ToggleGroup gruppoSesso;

    // componenti per il form per aggiungere un paziente
    @FXML
    private TextField textNome;
    @FXML
    private TextField textCognome;
    @FXML
    private TextField textEmail;
    @FXML
    private TextField textCodiceFiscaleCreazione;
    @FXML
    private DatePicker dataNascitaPicker;

    @FXML
    private TextField textCodiceFiscaleInformazioni;
    @FXML
    private TextField textFattoriRischio;
    @FXML
    private TextField textComorbita;
    @FXML
    private TextField textPatologiePregresse;

    public void initialize() {


        SettingsController.setLogin(Session.getCurrentUser());
        benvenuto.setText("Bentornat" + (diabetologo.getSesso().equals("M") ? "o" : "a") + " " + diabetologo);


        Platform.runLater(() -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

            Set<Insulina> nuoveCritiche = new HashSet<>();
            Set<Insulina> nuoveLieve = new HashSet<>();


            for (Paziente paziente : pazienteDao.getAllPatientsByDiabetologist(diabetologo.getId_diabetologo())) {

                List<Insulina> glicemieNonNotificate = insulinaDao.getNonNotifiedByDateAndPatient(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(23, 59, 59), paziente);


                for (Insulina insulina : glicemieNonNotificate) {
                    if (insulina.getGravita() == GRAVITA.CRITICA) {
                        nuoveCritiche.add(insulina);
                    }
                    if (insulina.getGravita() == GRAVITA.LIEVE) {
                        nuoveLieve.add(insulina);
                    }
                    insulinaDao.markAsNotified(insulina.getId_glicemia());
                }

            }

            if (!nuoveCritiche.isEmpty()) {
                StringBuilder msg = new StringBuilder("Nuove glicemie critiche:\n");

                for (Insulina i : nuoveCritiche) {
                    msg.append(i.getPaziente())
                            .append(" - valore: ").append(i.getLivello_insulina()).append(" mg/dL")
                            .append(" - data: ").append(i.getOrario().format(formatter))
                            .append("\n");
                }

                Utility.createAlert(Alert.AlertType.ERROR, msg.toString());
            }


            if (!nuoveLieve.isEmpty()) {
                StringBuilder msg = new StringBuilder("Nuove glicemie critiche:\n");

                for (Insulina i : nuoveLieve) {
                    msg.append(i.getPaziente())
                            .append(" - valore: ").append(i.getLivello_insulina()).append(" mg/dL")
                            .append(" - data: ").append(i.getOrario().format(formatter))
                            .append("\n");
                }

                Utility.createAlert(Alert.AlertType.WARNING, msg.toString());
            }


            if(!Utility.alertDiabetologo2) {
                Map<Paziente, List<Terapia>> mappa = assunzioneFarmacoDao.listPatientNoTakingDrugForThreeDaysConsecutiv(diabetologo);

                if (!mappa.isEmpty()) {

                    StringBuilder msg = new StringBuilder("Pazienti e relativi farmaci che non vengono assunti da 3 giorni\n");

                    for (Map.Entry<Paziente, List<Terapia>> entry : mappa.entrySet()) {
                        Paziente key = entry.getKey();
                        List<Terapia> value = entry.getValue();
                        msg.append(key.toString()).append("  -> [ ");
                        value.forEach(t -> msg.append(t.getFarmaco().getNome()).append(", "));
                        msg.setLength(msg.length() - 2);

                        msg.append(" ]\n");
                    }
                    Utility.createAlert(Alert.AlertType.WARNING, String.valueOf(msg));


                    Utility.alertDiabetologo2 = true;
                }

            }


        });

    }

    /**
     * Funzione per controllare l'evento della registrazione del nuovo paziente assegnato direttamente dal dottore.
     *
     */
    public void handleNuovoPaziente() {

        if (!Utility.isEmailValid(this.textEmail.getText()) || !Utility.isCodiceFiscaleValid(this.textCodiceFiscaleCreazione.getText()) || !Utility.checkObj(gruppoSesso.getSelectedToggle())
                || !Utility.checkDateNascita(this.dataNascitaPicker.getValue())
                || !Utility.checkOnlyLetters(this.textNome.getText()) || !Utility.checkOnlyLetters(this.textCognome.getText())
        ) {
            Utility.createAlert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi");
            return;
        }

        boolean success = false;
        String sesso = (String) ((RadioButton) gruppoSesso.getSelectedToggle()).getUserData();

        int id_paziente = pazienteDao.insertPatient(
                new Paziente(
                        textNome.getText(), textCognome.getText(), textEmail.getText(), textCodiceFiscaleCreazione.getText(), dataNascitaPicker.getValue(), sesso, diabetologo
                )
        );

        if (id_paziente > 0) {
            CredentialsGenerator c = new CredentialsGenerator(id_paziente, 0, textNome.getText(), textCognome.getText());
            success = loginDao.insertLogin(
                    new Login(
                            c.createUsername(), c.generatePassword(), id_paziente, null
                    )
            );
        }


        if (!success) {
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nella creazione del paziente");
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Nuovo paziente aggiunto ");
        Utility.resetField(pane1);


    }

    public void handleInformazioni() {
        FattoreRischio fr = null;
        Comorbità c = null;
        PatologiaPregressa pg = null;
        if (!Utility.isCodiceFiscaleValid(this.textCodiceFiscaleInformazioni.getText())) {
            Utility.createAlert(Alert.AlertType.ERROR, "Codice fiscale non valido o non inserito");
            return;
        }

        Paziente p = pazienteDao.getPatientByCf(textCodiceFiscaleInformazioni.getText().trim());
        if (p == null) {
            Utility.createAlert(Alert.AlertType.ERROR, "Paziente non trovato");
            return;
        }

        if (Utility.checkOnlyLetters(this.textFattoriRischio.getText().trim())) {
            fr = new FattoreRischio(textFattoriRischio.getText());
        }

        if (Utility.checkOnlyLetters(this.textComorbita.getText().trim())) {
            c = new Comorbità(textComorbita.getText());
        }

        if (Utility.checkOnlyLetters(this.textPatologiePregresse.getText().trim())) {
            pg = new PatologiaPregressa(textPatologiePregresse.getText());
        }

        InformazioniPaziente info = new InformazioniPaziente(p, fr, c, pg);

        int id = informazionPazienteDao.insertInformation(info);

        if (id <= 0) {
            Utility.createAlert(Alert.AlertType.ERROR, "Errore nel inseirmento  dell informazioni del paziente : " + p);
            return;
        }

        Utility.createAlert(Alert.AlertType.INFORMATION, "Informazioni aggiunte correttamente per il paziente : " + p);
        Utility.resetField(pane2);


    }
}
