package com.dashapp.diabeticsystem.controllers.dashboards;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DashboardDiabetologoController extends DashboardController {

    private final Diabetologo diabetologo;

    // componenti effettivi della dashboard
    @FXML private Label labelDash;

    // componenti della finestra per creare utenza al paziente
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;
    @FXML private DatePicker dataNascitaPicker;



    public DashboardDiabetologoController() {
        this.diabetologo = new Diabetologo();
    }

    /**
     * Funzione che permette di aprire una finestra per inserire nel database una nuova utenza per il paziente.
     */
    public void handleCreaPaziente() throws IOException {
        Main.getStage(new Stage(), "fxml/views/crea_paziente.fxml", "Crea Paziente");
    }

    /**
     * Funzione che permette di creare una nuova utenza per il paziente a partire dai
     * immessi dal dottore nell'apposito form per la creazione.
     */
    public void handleNuovoPaziente(){

        // controllo della validità dei campi inseriti dal dottore
        if(!isEmailValid() || !isCodiceFiscaleValid() || !checkCredeziali() || !checkDate()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi", ButtonType.OK);
            errorAlert.setTitle("Errore nella creazione del nuovo paziente");
            errorAlert.showAndWait();
            return;
        }

        System.out.println(createUsername() + "\nEmail:" + textEmail.getText() + "\nCodice Fiscale: " + textCodiceFiscale.getText());
        Paziente paziente = new Paziente(textNome.getText(),textCognome.getText(),textEmail.getText(),textCodiceFiscale.getText(), this.dataNascitaPicker.getValue());

        // TODO: creare record in tabella login
        //diabetologo.inserisciPaziente(paziente);


        // TOOD: creare record in tabella pazienti, l'id del dottore lo prendo da quello loggato
    }

    /**
     * Funzione che permette di generare automaticamente lo username dell'utente
     * @return un tipo <code>String</code> per lo username dell'utente
     */
    private String createUsername(){

        return this.textNome.getText() + "." + this.textCognome.getText();
    }


    /**
     * Funzione che permette di controllare la validità dei campi nome e congome del form per aggiungere un nuovo paziente
     * @return
     */
    private boolean checkCredeziali(){
        return !this.textNome.getText().isBlank() && !this.textCognome.getText().isBlank();
    }
    /**
     * Funzione che permette di controllare la validità della email inserita nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità della email.
     */
    private boolean isEmailValid() {
        if(textEmail.getText().isEmpty() || textEmail.getText() == null){
            return false;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(textEmail.getText());
        return matcher.matches();
    }

    private boolean checkDate(){
        return this.dataNascitaPicker.getValue() != null;
    }


    /**
     * Funzione che permette di controllare la validità del codice fiscale inserito nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità del codice fiscale.
     */
    private boolean isCodiceFiscaleValid() {
        String cf = this.textCodiceFiscale.getText();
        if (cf == null || cf.isEmpty()) {
            return false;
        }

        Pattern p = Pattern.compile("^[A-Z]{6}\\d{2}[A-EHLMPRST]\\d{2}[A-Z0-9]{4}[A-Z]$");
        Matcher m = p.matcher(cf.toUpperCase().trim());

        return m.matches();
    }

}
