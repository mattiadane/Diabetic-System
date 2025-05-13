package com.dashapp.diabeticsystem.controllers.dashboards;


import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class DashboardDiabetologoController extends DashboardController {

    // componenti effettivi della dashboard
    @FXML private Label labelDash;

    // componenti della finestra per creare utenza al paziente
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;

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
        String email = textEmail.getText();
        String codiceFiscale = textCodiceFiscale.getText();

        System.out.println(createUsername() + "\nEmail:" + email + "\nCodice Fiscale: " + codiceFiscale);

        // creare record in tabella login

        // creare record in tabella pazienti, l'id del dottore lo prendo da quello loggato
        System.out.println(Session.getCurrentUser());
        int idDottore = Session.getCurrentUser().getId_diabetologo();
    }

    /**
     * Funzione che permette di generare automaticamente lo username dell'utente
     * @return un tipo <code>String</code> per lo username dell'utente
     */
    private String createUsername(){
        return this.textNome.getText() + "." + this.textCognome.getText();
    }



}
