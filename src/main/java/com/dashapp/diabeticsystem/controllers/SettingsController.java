package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;

public class SettingsController {

    @FXML private BorderPane borderPane;
    @FXML private PasswordField textNewPassword;
    @FXML private PasswordField textConfirmPassword;
    private static Login persona;

    public static void setLogin(Login persona) {
        SettingsController.persona = persona;
    }

    public void initialize() {


    }

    /**
     * Funzione che permette di modificare la password
     */
    public void updatePassword(){


        if(!Utility.checkObj(textNewPassword.getText())  || !Utility.checkObj(textConfirmPassword.getText())){
            Utility.createAlert(Alert.AlertType.ERROR,"I campi non possono essere vuoti");
            return;

        }
        if(!textNewPassword.getText().equals(textConfirmPassword.getText())){
            Utility.createAlert(Alert.AlertType.ERROR,"Le due password non coincidono");
            return;
        }

        boolean succes = persona.updatePassword(textNewPassword.getText());
        if(!succes){
            Utility.createAlert(Alert.AlertType.ERROR,"Errore nell'aggiornamento della nuova password");
            return ;
        }


        Utility.createAlert(Alert.AlertType.INFORMATION, "Passsword aggiornata correttamente");
        Utility.resetField(borderPane);

    }


}
