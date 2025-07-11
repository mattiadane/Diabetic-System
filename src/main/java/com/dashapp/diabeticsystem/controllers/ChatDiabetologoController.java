package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Chat;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ChatDiabetologoController {

    @FXML
    private ListView<Paziente> chatListView;
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

    private final Login mittente = Session.getCurrentUser();
    private Map<Paziente, Chat> pazienteChatMap;

    public void initialize() {
        pazienteChatMap = mittente.pazienteEUltimoMessaggioDellaChat();

        List<Paziente> p = pazienteChatMap.keySet().stream().toList();
        chatListView.setItems(FXCollections.observableArrayList(p));

        chatListView.setCellFactory(lv -> new ListCell<Paziente>() {
            @Override
            protected void updateItem(Paziente paziente, boolean empty) {
                super.updateItem(paziente, empty);

                if (empty || paziente == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label(paziente.getNome() + " " + paziente.getCognome());

                    Chat c = pazienteChatMap.get(paziente);
                    String messageText;
                    String messageDate;

                    if (c != null) {

                        messageText =  c.getMessaggio();
                        messageDate =  c.getData_invio().format(DateTimeFormatter.ofPattern("dd/M/yyyy hh:mm")) ;
                    } else {
                        messageText = "Nessun messaggio recente";
                        messageDate = "";
                    }

                    Label lastMessageLabel = new Label(messageText + (messageDate.isEmpty() ? "" : " - " + messageDate));

                    nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.1em; -fx-text-fill: #333;");
                    lastMessageLabel.setStyle("-fx-font-size: 0.9em; -fx-text-fill: #777; -fx-wrap-text: true;");
                    lastMessageLabel.setMaxWidth(250.0); // Potresti ancora voler fissare questo
                    lastMessageLabel.setEllipsisString("...");
                    lastMessageLabel.setWrapText(true);

                    VBox cellContent = new VBox(nameLabel, lastMessageLabel);
                    cellContent.setSpacing(2);
                    HBox wrapper = new HBox(cellContent);
                    wrapper.setPadding(new Insets(8, 10, 8, 10));
                    wrapper.setStyle("-fx-background-color: #ffffff; -fx-border-width: 0 0 1 0; -fx-border-color: #e0e0e0;");

                    setGraphic(wrapper);
                }
            }
        });

        // Listener di selezione e gestione invio messaggio (come nel codice precedente)
        chatListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                chatHeaderLabel.setText(newVal.getNome() + " " + newVal.getCognome());
                messageContainer.getChildren().clear();
                messageContainer.getChildren().add(new Label("Caricamento chat con " + newVal.getNome() + "..."));
            } else {
                chatHeaderLabel.setText("Seleziona una chat");
                messageContainer.getChildren().clear();
            }
        });

        sendButton.setOnAction(event -> sendMessage());
        messageInput.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        String message = messageInput.getText().trim();
        if (!message.isEmpty()) {
            System.out.println("Invio messaggio: " + message);
            Label sentMessageLabel = new Label("Tu: " + message);
            sentMessageLabel.setStyle("-fx-background-color: #dcf8c6; -fx-padding: 5px 10px; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-alignment: center-right;");
            messageContainer.getChildren().add(sentMessageLabel);
            messageInput.clear();
            messageScrollPane.layout();
            messageScrollPane.setVvalue(1.0);
        }
    }
}