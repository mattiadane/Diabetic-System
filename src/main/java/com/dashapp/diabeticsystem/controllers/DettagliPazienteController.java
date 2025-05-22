package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DettagliPazienteController {

    // componenti FXML
    @FXML private Label main_label;
    @FXML private TableView<Terapia> tabella_terapie;
    @FXML private TableColumn<Terapia, String> col_nome;
    @FXML private TableColumn<Terapia, String> col_dosaggio;
    @FXML private TableColumn<Terapia, String> col_assunzioni;
    @FXML private TableColumn<Terapia, Void> modifica;
    @FXML private TableColumn<Terapia, Void> elimina;



    public void initialize(){
        this.col_nome.setCellValueFactory(cellData -> {
            // Get the Terapia object for the current row
            Terapia terapia = cellData.getValue();
            // Return an ObservableValue of the string representation of the Farmaco
            return new SimpleStringProperty(terapia.getFarmaco().toString());
        });
        col_dosaggio.setCellValueFactory(new PropertyValueFactory<>("dosaggio"));
        col_assunzioni.setCellValueFactory(new PropertyValueFactory<>("assunzioni"));

/*
        Callback<TableColumn<Terapia, Void>, TableCell<Terapia, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Terapia, Void> call(final TableColumn<Terapia, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Elimina");
                    {
                        btn.getStyleClass().add("btn-apri");
                        btn.setOnAction(event -> {


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
        elimina.setCellFactory(cellFactory);*/

    }


    public void loadPaziente(Paziente paziente) {
        main_label.setText("Paziente: " + paziente);
        System.out.println("Paziente: " + paziente);

        tabella_terapie.setItems(paziente.loadAllTerapie());
    }
}
