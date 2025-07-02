package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.chart.LineChart;
import java.util.Date;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class DettagliPazienteController {


    @FXML private TableView<Terapia> tabella_terapie;
    @FXML private TableColumn<Terapia, String> col_nome;
    @FXML private TableColumn<Terapia, String> col_dosaggio;
    @FXML private TableColumn<Terapia, String> col_assunzioni;
    @FXML private TableColumn<Terapia, String> col_periodo;

    @FXML private TableColumn<Terapia, Void> modifica;
    @FXML private TableColumn<Terapia, Void> elimina;

    @FXML private LineChart<String, Number> chart;
    private  Paziente paziente;
    private Diabetologo diabetologo;

    public void loadTerapie(Paziente paziente) {
        this.paziente = paziente;
        tabella_terapie.setItems(paziente.loadAllTerapie());

    }


    public void initialize(){
        this.diabetologo = new Diabetologo();
        this.col_nome.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFarmaco().getNome()));

        this.col_dosaggio.setCellValueFactory(new PropertyValueFactory<>("dosaggio"));
        this.col_assunzioni.setCellValueFactory(new PropertyValueFactory<>("assunzioni"));
        this.col_periodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));


        Callback<TableColumn<Terapia, Void>, TableCell<Terapia, Void>> cellFactoryElimina = new Callback<>() {
            @Override
            public TableCell<Terapia, Void> call(final TableColumn<Terapia, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Elimina");
                    {
                        btn.getStyleClass().add("btn-elimina");
                        btn.setOnAction(event -> {
                            Optional<ButtonType> result = Utility.createAlert(Alert.AlertType.CONFIRMATION,"Sei sicuro di voler rimuovere la terapia?");
                            if( result.isPresent() && result.get().getText().equals("Si")){
                                boolean success = diabetologo.rimuoviTerapia(getTableView().getItems().get(getIndex()));
                                if(!success){
                                    return;
                                }
                                paziente.rimuoviTerapie(getTableView().getItems().get(getIndex()));

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

        Callback<TableColumn<Terapia, Void>, TableCell<Terapia, Void>> cellFactoryModifica = new Callback<>() {
            @Override
            public TableCell<Terapia, Void> call(final TableColumn<Terapia, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Modifica");
                    {
                        btn.getStyleClass().add("btn-modifica");
                        btn.setOnAction(event ->
                            apriSchedaTerapia(getTableView().getItems().get(getIndex()),paziente)
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
        modifica.setCellFactory(cellFactoryModifica);
        elimina.setCellFactory(cellFactoryElimina);
    }

    /**
     * Funzione che permette di aprire la scheda del paziente selezionato.
     * @param terapia terapia da visualizzare
     * @param paziente paziente a cui appartiene la terapia.
     */
    private void apriSchedaTerapia(Terapia terapia,Paziente paziente){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/modificaTerapia.fxml"));

            Parent root = loader.load();

            ModificaTerapiaController modificaTerapiaController = loader.getController();
            modificaTerapiaController.loadData(terapia,paziente);

            Stage schedaTerapia = new Stage();
            Scene newScene = new Scene(root, 600, 700);

            URL cssUrl = Main.class.getResource("css/style.css");
            if(cssUrl != null){
                newScene.getStylesheets().add(cssUrl.toExternalForm());
            }

            schedaTerapia.setTitle("Scheda Terapia di: " + this.paziente.getNome() + " " + this.paziente.getCognome());
            schedaTerapia.setScene(newScene);
            schedaTerapia.initModality(Modality.NONE);
            schedaTerapia.show();
        }catch(IOException err){
            System.out.println("Errore: " + err.getMessage());
        }
    }

    /**
     * Funzione che permette di mostrare i livelli di insulina di una settimana di un determinato paziente
     */
    public void initChart(Paziente paziente){
        // setto i label per x e y
        chart.getXAxis().setLabel("Giorni");
        chart.getYAxis().setLabel("Registrazioni");

        XYChart.Series<String,Number> series = new XYChart.Series<>();
        series.setName("Insulina");

        ObservableList<Insulina> data = paziente.getAllInsulinas();
        if(data.isEmpty()) {
            Utility.createAlert(Alert.AlertType.WARNING, "Non ci sono registrazioni di insulina per questo paziente");
        }

        for(Insulina reg : data){
            String day = reg.getOrario().toLocalDate().toString();
            int value = reg.getLivello_insulina();

            series.getData().add(new XYChart.Data<>(day, value));
        }

        if(series.getData().isEmpty()) return;
        chart.getData().add(series);
    }

}
