package com.dashapp.diabeticsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsControllerPaziente {

    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textTelefono;
    @FXML private PasswordField textNewPassword;
    @FXML private PasswordField textConfirmPassword;

    /**
     * Funzione che permette di controllare l'evento di aggiornamento dei dati del paziente all'interno del form
     * delle impostazioni.
     */
    public void handleUpdatePaziente(){

    }
}
