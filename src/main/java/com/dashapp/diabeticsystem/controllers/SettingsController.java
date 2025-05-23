package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Persona;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class SettingsController {

    @FXML private BorderPane borderPane;
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private PasswordField textNewPassword;
    @FXML private PasswordField textConfirmPassword;
    private static Persona persona;


    public static void setPersona(Persona p) {
        persona = p;
    }

    public void initialize() {

        textNome.setText(persona.getNome());
        textCognome.setText(persona.getCognome());
        textEmail.setText(persona.getEmail());
    }

    /**
     * Funzione che permette di controllare l'evento di aggiornamento dei dati del diabetologo.
     */
    public void handleUpdateData(){


        if(Utility.checkObj(textNewPassword.getText())){
            if(!textNewPassword.getText().equals(textConfirmPassword.getText())){
                Utility.createAlert(Alert.AlertType.ERROR,"Le due password non coincidono");
                return;
            }

            boolean success =  persona.updatePassword(textNewPassword.getText());
            if(!success){
                Utility.createAlert(Alert.AlertType.ERROR, "Errore nell'aggiornamento della password");
                return;
            }


        } else {
            if( !Utility.isEmailValid(this.textEmail.getText())
                    || !Utility.checkOnlyLetters(textNome.getText()) || !Utility.checkOnlyLetters(textCognome.getText())
                || Utility.checkObj(textConfirmPassword.getText())

            ){
                Utility.createAlert(Alert.AlertType.ERROR, "Dati inseriti non validi.");
                return;
            }else{
                persona.setNome(textNome.getText());
                persona.setCognome(textCognome.getText());
                persona.setEmail(textEmail.getText());
                boolean success = persona.updatePersona(persona);
                if(!success){
                    Utility.createAlert(Alert.AlertType.ERROR, "Errore nell'aggiornamento dei dati");
                    return;
                }
            }
        }
        Utility.createAlert(Alert.AlertType.INFORMATION, "Dati aggiornati correttamente");
        Utility.resetField(borderPane);
        initialize();






    }




}
