package com.dashapp.diabeticsystem.controllers.dashboards;

import com.dashapp.diabeticsystem.controllers.SettingsController;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import com.mysql.cj.util.Util;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.time.LocalDate;

public class DashboardPazienteController   {

    @FXML private Label benvenuto;
    @FXML private ListView<Terapia> listaTerapie;
    @FXML private LineChart<String, Number> chart;

    private final Paziente paziente = new Paziente();




    public void initialize() {
        this.benvenuto.setText("Bentornat" + (paziente.getSesso().equals("M") ? "o" : "a") + " " + paziente);
        SettingsController.setPersona(paziente);
        createTerapieList();
        initChart();
        Platform.runLater(() -> {
            if (paziente.numberDailyTakingMedicine(LocalDate.now()) == 0) {
                Utility.createAlert(Alert.AlertType.WARNING, "Ricordati di assumere i farmaci, oggi non hai ancora assunto nessun farmaco");
            }
        });

    }

    /**
     * Funzione che permette di popolare la lista delle terapie associate al paziente.
     */
    private void createTerapieList() {
        listaTerapie.setItems(paziente.getAllTerapie());

        listaTerapie.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Terapia item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : " ● " + item.toStringForList());
            }
        });
    }

    /**
     * Funzione che permette di inizializzare il grafico per i livelli di insulina registrati
     */
    private void initChart(){
        this.chart.getXAxis().setLabel("Giorni");
        this.chart.getYAxis().setLabel("Valori (mg/dL)");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.setName("Insulina");

        ObservableList<Insulina> data = paziente.getInsulina(10,0);
        for(Insulina reg : data){
            String day = reg.getOrario().toLocalDate().toString() + " " + reg.getOrario().toLocalTime().toString();
            int value = reg.getLivello_insulina();

            series.getData().add(new XYChart.Data<>(day, value));
        }

        if(series.getData().isEmpty()) return;
        this.chart.getData().addAll(series);
    }



}
