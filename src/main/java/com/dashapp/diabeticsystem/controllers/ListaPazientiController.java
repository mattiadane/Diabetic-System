package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Paziente;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaPazientiController {

    @FXML private TableView<Paziente> tabellaPazienti ;
    @FXML private TableColumn<Paziente, String> nomePaziente;
    @FXML private TableColumn<Paziente, String> cognomePaziente;
    @FXML private TableColumn<Paziente, String> cfPaziente;
    @FXML private TableColumn<Paziente, Void> azioniColonna;

    public void initialize(){
        this.nomePaziente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.cognomePaziente.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        this.cfPaziente.setCellValueFactory(new PropertyValueFactory<>("cf"));

        // TODO: implementazione per settare i bottoni per aprire la scheda del paziente
    }
}
