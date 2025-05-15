package com.dashapp.diabeticsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
        if(!isValidInput()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Input non valido");
            alert.showAndWait();
            return;
        }

        System.out.println("E' stato registrato il seguente dato:\nLivello: " + this.textLivello.getText() + "\nMomento: " + this.comboBoxMomento.getValue());
    }

    /**
     * Funzione che permette di controllare se i valori inseriti dall'utente sono validi ai fini della registrazione nella tabella.
     * @return <code>true</code> se i valori sono validi, <code>false</code> altrimenti.
     */
    private boolean isValidInput(){
        if(this.comboBoxMomento.getValue() == null || this.comboBoxMomento.getValue().isEmpty()) return false;
        String livello = this.textLivello.getText();

        try{
            int n = Integer.parseInt(livello);
            return n >= 0 && n <= 150;
        }catch(NumberFormatException e){
            return false;
        }
    }


}
