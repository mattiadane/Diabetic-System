package com.dashapp.diabeticsystem.controllers;


import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.Optional;

public class DettagliPazienteController {

    // componenti FXML
    @FXML private Label main_label;
    @FXML private TableView<Terapia> tabella_terapie;
    @FXML private TableColumn<Terapia, String> col_nome;
    @FXML private TableColumn<Terapia, String> col_dosaggio;
    @FXML private TableColumn<Terapia, String> col_assunzioni;
    @FXML private TableColumn<Terapia, Void> modifica;
    @FXML private TableColumn<Terapia, Void> elimina;
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




        Callback<TableColumn<Terapia, Void>, TableCell<Terapia, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Terapia, Void> call(final TableColumn<Terapia, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Elimina");
                    {
                        btn.getStyleClass().add("btn-elimina");
                        btn.setOnAction(event -> {
                            Optional<ButtonType> result = Utility.createAlert(Alert.AlertType.CONFIRMATION,"Sei sicuro di voler rimuovere la terapia?");
                            if(result.get().getText().equals("Si")){
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
        elimina.setCellFactory(cellFactory);

    }








}
