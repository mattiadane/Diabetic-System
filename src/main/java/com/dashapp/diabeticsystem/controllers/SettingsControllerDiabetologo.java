package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Diabetologo;
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
    private Diabetologo diabetologo;


    public void initialize() {
        diabetologo = new Diabetologo();
        textNome.setText(diabetologo.getNome());
        textCognome.setText(diabetologo.getCognome());
        textEmail.setText(diabetologo.getEmail());
    }

    /**
     * Funzione che permette di controllare l'evento di aggiornamento dei dati del diabetologo.
     */
    public void handleUpdateDataDiabetologo(){
        if(!Utility.checkPassword(this.textNewPassword.getText()) || !this.textNewPassword.getText().equals(this.textConfirmPassword.getText())){
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
            boolean success = diabetologo.updateCredentials(this.textNome.getText(), this.textCognome.getText(), this.textEmail.getText(),this.textNewPassword.getText());
            if(!success){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore nell'aggiornamento dei dati");
                alert.setHeaderText("Errore nell'aggiornamento dei dati");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dati aggiornati correttamente");
            alert.setHeaderText("Dati aggiornati correttamente");
            alert.showAndWait();

            // reset dei campi di testo
            Utility.resetField(borderPane);
            initialize();
        }

    }




}
