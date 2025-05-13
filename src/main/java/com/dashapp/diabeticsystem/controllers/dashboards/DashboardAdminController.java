package com.dashapp.diabeticsystem.controllers.dashboards;


import com.dashapp.diabeticsystem.models.Admin;
import com.dashapp.diabeticsystem.models.Diabetologo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DashboardAdminController  extends DashboardController{

    @FXML private TextField textNome;
    @FXML private TextField textCognome;
    @FXML private TextField textEmail;

    /**
     * Funzione che permette di creare un nuovo diabetologo dai dati forniti dal form dell'Admin
     * @param event evento di click sul bottone
     */
    public void createNewDiabetologo(ActionEvent event){
        if(!checkValues()){
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

        if(!success){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Non è stato possibile creare un nuovo diabetologo");
            alert.setContentText("Controlla i dati inseriti");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nuovo diabetologo creato");
            alert.setHeaderText("Il nuovo diabetologo è stato creato con successo");
            alert.showAndWait();
        }

        resetFields();

    }

    /**
     * Funzione che permette di controllare la validità dei valori inseriti nel campo di testo
     * @return oggetto <code>boolean</code> che indica se i valori sono validi o meno
     */
    private boolean checkValues(){
        return !textNome.getText().isEmpty() && !textCognome.getText().isEmpty() && !textEmail.getText().isEmpty();
    }

    private void resetFields(){
        textNome.setText("");
        textCognome.setText("");
        textEmail.setText("");
    }



}
