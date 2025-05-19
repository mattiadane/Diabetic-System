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
    @FXML private TextField textEmail;

    /**
     * Funzione che permette di creare un nuovo diabetologo dai dati forniti dal form dell'Admin
     * 
     */
    public void createNewDiabetologo(){
        if(!Utility.checkCredenziali(textNome.getText(),textCognome.getText()) || !Utility.isEmailValid(textEmail.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("I dati inseriti non sono validi per il nuovo diabetologo");
            alert.setContentText("Controlla i dati inseriti");
            alert.showAndWait();
            return;
        }

        boolean success = new Admin().inserisciDiabetologo(
                new Diabetologo(
                        textNome.getText(), textCognome.getText(), textEmail.getText()
                )
        );

        Alert alert;
        if(!success){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile creare un nuovo diabetologo");
            alert.setContentText("Controlla i dati inseriti");
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nuovo diabetologo creato");
            alert.setHeaderText("Il nuovo diabetologo è stato creato con successo");
        }
        alert.showAndWait();

        Utility.resetField(borderpane);

    }

}
