package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreaPaziente {

    private final Diabetologo diabetologo = new Diabetologo();

    // componenti della finestra per creare utenza al paziente
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;
    @FXML private DatePicker dataNascitaPicker;

    /**
     * Funzione che permette di creare una nuova utenza per il paziente a partire dai
     * dati immessi dal dottore nell'apposito form per la creazione.
     */
    public void handleNuovoPaziente(ActionEvent event){

        // controllo della validità dei campi inseriti dal dottore
        if(!isEmailValid() || !isCodiceFiscaleValid() || !checkCredenziali() || !checkDate()){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Uno o più dati inseriti non sono validi", ButtonType.OK);
            errorAlert.setTitle("Errore nella creazione del nuovo paziente");
            errorAlert.showAndWait();
            return;
        }

        // eseguo la query di inserimento del nuovo paziente a database
        boolean success = diabetologo.inserisciPaziente(
                new Paziente(
                        textNome.getText(),textCognome.getText(),textEmail.getText(),textCodiceFiscale.getText().toUpperCase().trim(), dataNascitaPicker.getValue()
                )
        );

        // controllo dell'esito dell'inserimento del nuovo paziente a database e mostro un Alert dedicato
        if(!success){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Errore nella creazione del paziente", ButtonType.OK);
            errorAlert.setTitle("Errore nella creazione del nuovo paziente");
            errorAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Paziente aggiunto ", ButtonType.OK);
            alert.setTitle("Nuovo paziente aggiunto");
            alert.showAndWait();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        }


    }



    /**
     * Funzione che permette di controllare la validità dei campi nome e congome del form per aggiungere un nuovo paziente
     * @return un valore di tipo <code>boolean</code> per la validità dei campi nome e cognome
     */
    private boolean checkCredenziali(){
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

        return this.dataNascitaPicker.getValue() != null && !dataNascitaPicker.getValue().isAfter(LocalDate.now());
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
