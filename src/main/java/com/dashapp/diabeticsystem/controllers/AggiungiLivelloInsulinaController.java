package com.dashapp.diabeticsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AggiungiLivelloInsulinaController {

    @FXML private ComboBox<String> comboBoxMomento;
    @FXML private TextField textLivello;


    /**
     * Funzione che permette di controllare l'evento di aggiunta del livello di insulina
     * @param event oggetto <code>ActionEvent</code> per rappresentare l'evento generato.
     */
    public void handleAggiungiLivello(ActionEvent event){
        System.out.println("E' stato registrato il seguente dato:\nLivello: " + this.textLivello.getText() + "\nMomento: " + this.comboBoxMomento.getValue());
    }

}
