package com.dashapp.diabeticsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreaPaziente {

    @FXML private Label labelNome;
    @FXML private Label labelCognome;
    @FXML private Label labelEmail;
    @FXML private Label labelCodiceFiscale;

    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textCodiceFiscale;

    /**
     * Funzione che permette di creare una nuova utenza per il paziente a partire dai
     * immessi dal dottore nell'apposito form per la creazione.
     */
    public void handleCreaPaziente(){
        String nome = textNome.getText();
        String cognome = textCognome.getText();
        String email = textEmail.getText();
        String codiceFiscale = textCodiceFiscale.getText();

        System.out.println(nome + " " + cognome + "\nEmail:" + email + "\nCodice Fiscale: " + codiceFiscale);
    }
}
