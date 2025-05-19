package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AggiungiLivelloInsulinaController {

    @FXML BorderPane borderpane;
    @FXML private ComboBox<String> comboBoxMomento;
    @FXML private TextField textLivello;


    /**
     * Funzione che permette di controllare l'evento di aggiunta del livello di insulina
     *
     */
    public void handleAggiungiLivello(){
        if(!Utility.checkInsulina(textLivello.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Input non valido");
            alert.showAndWait();
            return;
        }


        Utility.resetField(borderpane);
        System.out.println("E' stato registrato il seguente dato:\nLivello: " + this.textLivello.getText() + "\nMomento: " + this.comboBoxMomento.getValue());
    }


}
