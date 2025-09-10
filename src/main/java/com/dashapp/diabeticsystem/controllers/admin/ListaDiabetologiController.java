package com.dashapp.diabeticsystem.controllers.admin;

import com.dashapp.diabeticsystem.DAO.implementations.DiabetologoDaoImpl;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.Optional;

public class ListaDiabetologiController {

    private final DiabetologoDaoImpl diabetologoDao = new DiabetologoDaoImpl();


    @FXML
    private TableView<Diabetologo> tabellaDiabetologo ;
    @FXML private TableColumn<Diabetologo, String> nomeDiabetologo;
    @FXML private TableColumn<Diabetologo, String> cognomeDiabetologo;
    @FXML private TableColumn<Diabetologo, String> cfDiabetologo;
    @FXML private TableColumn<Diabetologo, Void> azioniColonna;

    public void initialize(){

        this.nomeDiabetologo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.cognomeDiabetologo.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        this.cfDiabetologo.setCellValueFactory(new PropertyValueFactory<>("codice_fiscale"));
        this.tabellaDiabetologo.setItems(diabetologoDao.getAllDiabetologists());

        Callback<TableColumn<Diabetologo, Void>, TableCell<Diabetologo, Void>> cellFactoryElimina = new Callback<>() {
            @Override
            public TableCell<Diabetologo, Void> call(final TableColumn<Diabetologo, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("Elimina");
                    {
                        btn.getStyleClass().add("btn-elimina");
                        btn.setOnAction(event -> {
                            Optional<ButtonType> result = Utility.createAlert(Alert.AlertType.CONFIRMATION,"Sei sicuro di voler rimuovere il diabetologo?");
                            if(result.isPresent()  && result.get().getText().equals("Si")){
                                boolean success = diabetologoDao.removeDiabetologist(getTableView().getItems().get(getIndex()).getId_diabetologo());

                                if(!success){
                                    Utility.createAlert(Alert.AlertType.ERROR,"Errore nella rimozione del diabetologo");
                                }
                                getTableView().getItems().remove(getIndex());


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
        this.azioniColonna.setCellFactory(cellFactoryElimina);
    }

}
