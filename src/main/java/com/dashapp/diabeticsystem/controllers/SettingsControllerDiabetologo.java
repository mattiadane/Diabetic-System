package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class SettingsControllerDiabetologo {

    @FXML private BorderPane borderPane;
    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;
    @FXML private PasswordField textNewPassword;
    @FXML private PasswordField textConfirmPassword;


    public void handleUpdateDataDiabetologo(){
        if(!(this.textNewPassword.getText().equals(this.textConfirmPassword.getText()))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Le password inserite non coincidono.");
            alert.showAndWait();
            return;
        }

        // logica quando le password coincidono
        if(!Utility.checkCredenziali(this.textNome.getText(), this.textCognome.getText()) || !Utility.isEmailValid(this.textEmail.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Dati inseriti non validi.");
            alert.showAndWait();
        }else{
            Utility.resetField(borderPane);
            System.out.println("Nuovi dati validi.");
        }

    }




}
