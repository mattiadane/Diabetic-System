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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class ListaPazientiController {


    @FXML private TextField codice_fiscale ;
    @FXML private TableView<Paziente> tabellaPazienti ;
    @FXML private TableColumn<Paziente, String> nomePaziente;
    @FXML private TableColumn<Paziente, String> cognomePaziente;
    @FXML private TableColumn<Paziente, String> cfPaziente;
    @FXML private TableColumn<Paziente, Void> terapie;
    @FXML private TableColumn<Paziente,Void> elimina;
    @FXML private TableColumn<Paziente,Void> modifica;



    private Diabetologo diabetologo;

    public void initialize(){
        this.diabetologo = new Diabetologo();
        this.nomePaziente.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.cognomePaziente.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        this.cfPaziente.setCellValueFactory(new PropertyValueFactory<>("codice_fiscale"));


        // Implementazione per settare i bottoni per aprire la scheda del paziente
        Callback<TableColumn<Paziente, Void>, TableCell<Paziente, Void>> apriScheda = new Callback<>() {
            @Override
            public TableCell<Paziente, Void> call(final TableColumn<Paziente, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Apri");
                    {
                        btn.getStyleClass().add("btn-apri-scheda");
                        btn.setOnAction(event ->
                            apriSchedaPaziente(getTableView().getItems().get(getIndex()),"fxml/dettagliPaziente.fxml")
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



        // Implementazione per settare i bottoni per eliminare il paziente
        Callback<TableColumn<Paziente, Void>, TableCell<Paziente, Void>> eliminaPaziente = new Callback<>() {
            @Override
            public TableCell<Paziente, Void> call(final TableColumn<Paziente, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Elimina");
                    {
                        btn.getStyleClass().add("btn-elimina");
                        btn.setOnAction(event -> {
                            Optional<ButtonType> result = Utility.createAlert(Alert.AlertType.CONFIRMATION, "Sei sicuro di voler rimuovere il diabetologo?");
                            if (result.isPresent() && result.get().getText().equals("Si")) {
                                boolean success = diabetologo.rimuoviPaziente(getTableView().getItems().get(getIndex()));
                                if (!success) {
                                    Utility.createAlert(Alert.AlertType.ERROR, "Errore nella rimozione del diabetologo");
                                }

                            }
                        });
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


        // Implementazione per settare i bottoni per modificare la scheda del paziente
        Callback<TableColumn<Paziente, Void>, TableCell<Paziente, Void>> modificaPaziente = new Callback<>() {
            @Override
            public TableCell<Paziente, Void> call(final TableColumn<Paziente, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Modifica paziente");
                    {
                        btn.getStyleClass().add("btn-apri-scheda");
                        btn.setOnAction(event ->
                                apriSchedaPaziente(getTableView().getItems().get(getIndex()),"fxml/modificaPaziente.fxml")
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




        terapie.setCellFactory(apriScheda);
        elimina.setCellFactory(eliminaPaziente);
        modifica.setCellFactory(modificaPaziente);
        aggiornaTabella();

    }

    /**
     * Funzione che permette di aprire fxml passato come parametro.
     * @param paziente il paziente da visualizzare
     * @param fxml pagina da aprire
     */
    private void apriSchedaPaziente(Paziente paziente,String fxml) {
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));


            Parent root = loader.load();

            if(fxml.equals("fxml/dettagliPaziente.fxml")){
                DettagliPazienteController dettagliPazienteController = loader.getController();
                dettagliPazienteController.loadTerapie(paziente);
                dettagliPazienteController.setTextFields(paziente);

            } else {
                ModificaPazienteController modificaPazienteController = loader.getController();
                modificaPazienteController.setPaziente(paziente);
                modificaPazienteController.setController(this);
            }



            Stage schedaStage = new Stage();






            Scene newScene = new Scene(root, 1000, 700);

            URL cssUrl = Main.class.getResource("css/style.css");
            if (cssUrl != null) {
                newScene.getStylesheets().add(cssUrl.toExternalForm());
            }

            schedaStage.setTitle("Scheda Paziente: " + paziente.getNome() + " " + paziente.getCognome());
            schedaStage.setScene(newScene);
            schedaStage.initModality(Modality.NONE);



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

    public void aggiornaTabella(){
        tabellaPazienti.setItems(diabetologo.loadAllPatients());
    }

}