package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;

public class ListaPazientiController {

    @FXML private TextField codice_fiscale ;
    @FXML private TableView<Paziente> tabellaPazienti ;
    @FXML private TableColumn<Paziente, String> nomePaziente;
    @FXML private TableColumn<Paziente, String> cognomePaziente;
    @FXML private TableColumn<Paziente, String> cfPaziente;
    @FXML private TableColumn<Paziente, Void> azioniColonna;
    private Diabetologo diabetologo;

    public void initialize(){
        this.diabetologo = new Diabetologo();
        this.nomePaziente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.cognomePaziente.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        this.cfPaziente.setCellValueFactory(new PropertyValueFactory<>("codice_fiscale"));

        // Implementazione per settare i bottoni per aprire la scheda del paziente
        Callback<TableColumn<Paziente, Void>, TableCell<Paziente, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Paziente, Void> call(final TableColumn<Paziente, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Apri Scheda");
                    {
                        btn.getStyleClass().add("btn-apri-scheda");
                        btn.setOnAction(event ->
                            apriSchedaPaziente(getTableView().getItems().get(getIndex()))
                        );
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }

                };
            }
        };

        azioniColonna.setCellFactory(cellFactory);
        tabellaPazienti.setItems(diabetologo.getAllPatients());


    }

    /**
     * Funzione che permette di aprire la scheda del paziente selezionato.
     * @param paziente il paziente da visualizzare
     */
    private void apriSchedaPaziente(Paziente paziente) {
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/dettagliPaziente.fxml"));


            Parent root = loader.load();

            DettagliPazienteController dettagliPazienteController = loader.getController();
            dettagliPazienteController.loadTerapie(paziente);


            Stage schedaStage = new Stage();
            Scene newScene = new Scene(root, 1000, 700);

            URL cssUrl = Main.class.getResource("css/style.css");
            if (cssUrl != null) {
                newScene.getStylesheets().add(cssUrl.toExternalForm());
            }

            schedaStage.setTitle("Scheda Paziente: " + paziente.getNome() + " " + paziente.getCognome());
            schedaStage.setScene(newScene);
            schedaStage.initModality(Modality.NONE);
            schedaStage.initOwner(tabellaPazienti.getScene().getWindow());

            schedaStage.show();

        } catch (IOException e) {
                System.out.println("Errore: " + e.getMessage());
        }
    }

    /**
     * Funzione che permette di cercare un paziente in base al codice fiscale inserito.
     */
    public void searchInput() {
        tabellaPazienti.setItems(diabetologo.getPazientiResearch(codice_fiscale.getText()));

    }
}