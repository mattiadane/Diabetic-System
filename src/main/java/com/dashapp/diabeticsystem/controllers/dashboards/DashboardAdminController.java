package com.dashapp.diabeticsystem.controllers.dashboards;


import com.dashapp.diabeticsystem.models.Admin;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class DashboardAdminController  {

    @FXML BorderPane borderpane;

    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textCf;
    @FXML private TextField textEmail;

    /**
     * Funzione che permette di creare un nuovo diabetologo dai dati forniti dal form dell'Admin
     * 
     */
    public void createNewDiabetologo(){
        if( !Utility.isEmailValid(textEmail.getText())
            || !Utility.checkOnlyLetters(textNome.getText()) || !Utility.checkOnlyLetters(textCognome.getText()) || !Utility.isCodiceFiscaleValid(textCf.getText())
        ){
            Utility.createAlert(Alert.AlertType.ERROR, "Controlla i dati inseriti");
            return;
        }

        boolean success = new Admin().inserisciDiabetologo(
                new Diabetologo(
                        textNome.getText(), textCognome.getText(),textEmail.getText(),textCf.getText()
                )
        );

        if(!success) {
            Utility.createAlert(Alert.AlertType.ERROR, "Non è stato possibile creare un nuovo diabetologo");
            return;
        }
        Utility.createAlert(Alert.AlertType.INFORMATION, "Nuovo diabetologo creato\nIl nuovo diabetologo è stato creato con successo");

        Utility.resetField(borderpane);

    }

}
