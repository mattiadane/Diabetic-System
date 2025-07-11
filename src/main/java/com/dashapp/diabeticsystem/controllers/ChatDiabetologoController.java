package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class ChatDiabetologoController {

    @FXML
    private  ListView<Paziente> chatListView;
    @FXML
    private Label chatHeaderLabel;
    @FXML
    private ScrollPane messageScrollPane;
    @FXML
    private VBox messageContainer;
    @FXML
    private TextField messageInput;
    @FXML
    private Button sendButton;

    private Login mittente = Session.getCurrentUser();


    public void initialize(){
        List<Paziente> p = mittente.pazienteEUltimoMessaggioDellaChat().keySet().stream().toList();
       chatListView.setItems(FXCollections.observableArrayList(p));
    }
}
