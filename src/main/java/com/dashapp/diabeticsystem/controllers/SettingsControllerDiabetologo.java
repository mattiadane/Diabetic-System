package com.dashapp.diabeticsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsControllerDiabetologo {

    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private TextField textNewPassword;
    @FXML private TextField textConfirmPassword;

    @FXML private Button buttonUpdateData;

    public void handleUpdateDataDiabetologo(ActionEvent event){
        if(!(this.textNewPassword.getText().equals(this.textConfirmPassword.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Le password inserite non coincidono.");
            alert.showAndWait();
            return;
        }

        // logica quando le password coincidono
        if(!(checkNomeCognomeUsername() && checkEmail())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Dati inseriti non validi.");
            alert.showAndWait();
        }else{
            System.out.println("Nuovi dati validi.");
        }

    }

    /**
     * Funzione per controllare la validità del nome, cognome e username
     * @return oggetto <code>boolean</code> per controllare la validità dei campi
     */
    private boolean checkNomeCognomeUsername(){
        return !(this.textNome.getText().isEmpty()) && !(this.textCognome.getText().isEmpty()) ;
    }

    /**
     * Funzione che permette di validare l'email
     * @return oggetto <code>boolean</code> per controllare la validità dei campi
     */
    private boolean checkEmail(){
        if(textEmail.getText().isEmpty() || textEmail.getText() == null){
            return false;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(textEmail.getText());
        return matcher.matches();
    }





}
