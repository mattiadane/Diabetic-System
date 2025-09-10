package com.dashapp.diabeticsystem.controllers.diabetologo;

import com.dashapp.diabeticsystem.models.Chat;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Session;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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


    private final Login mittente = Session.getCurrentUser();
    private int id_destinatario  ;
    private Map<Paziente, Chat> pazienteChatMap;

    public void initialize() {
        pazienteChatMap = mittente.pazienteEUltimoMessaggioDellaChat();

        List<Paziente> p = new ArrayList<>(pazienteChatMap.keySet().stream().toList());

        p.sort((p1, p2) -> {
            Chat c1 = pazienteChatMap.get(p1);
            Chat c2 = pazienteChatMap.get(p2);

            LocalDateTime data1 = (c1 != null) ? c1.getData_invio() : LocalDateTime.MIN;
            LocalDateTime data2 = (c2 != null) ? c2.getData_invio() : LocalDateTime.MIN;


            return data2.compareTo(data1);
        });
        chatListView.setItems(FXCollections.observableArrayList(p));


        chatListView.setCellFactory(lv -> new ListCell<>() {

            @Override
            protected void updateItem(Paziente paziente, boolean empty) {
                super.updateItem(paziente, empty);

                if (empty || paziente == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label nameLabel = new Label(paziente.getNome() + " " + paziente.getCognome());

                    Chat c = pazienteChatMap.get(paziente);
                    String messageText = "Nessun messaggio recente";
                    String messageDate = "";

                    if (c != null) {

                        messageText =  c.getMessaggio();
                        messageDate =  c.getData_invio().format(DateTimeFormatter.ofPattern("dd/M/yyyy HH:mm")) ;
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


                id_destinatario = mittente.getid_loginPaziente(newVal);



                for(Chat c : mittente.chatDiabetologoPaziente(mittente.getId_login(), id_destinatario)){
                    addMessage(c);
                }

                messageScrollPane.setVvalue(1.0);

            }
        });

        messageContainer.heightProperty().addListener((obs, oldVal, newVal) -> messageScrollPane.setVvalue(1.0));


    }


    private void addMessage(Chat msg) {
        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(5, 10, 5, 10));

        Label messageLabel = new Label(msg.getMessaggio());
        messageLabel.setWrapText(true); // Permette al testo di andare a capo
        messageLabel.setMaxWidth(250); // Limita la larghezza della bolla del messaggio

        String sender = msg.getId_mittente() == mittente.getId_login() ? "Mittente" : "Destinatario";

        // Stile del messaggio in base al mittente
        if (sender.equals("Mittente")) {
            messageBox.setAlignment(Pos.CENTER_RIGHT); // Allinea a destra per i messaggi inviati
            messageLabel.setStyle("-fx-background-color: #DCF8C6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 8;");
        } else {
            messageBox.setAlignment(Pos.CENTER_LEFT); // Allinea a sinistra per i messaggi ricevuti
            messageLabel.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 8; -fx-border-color: #ddd; -fx-border-width: 1;");
        }
        messageLabel.setFont(Font.font("Arial", 14));

        // Data e ora del messaggio
        LocalDateTime now = msg.getData_invio();
        // Formato italiano per data e ora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm - dd/MM/yyyy");
        Label timeLabel = new Label(now.format(formatter));
        timeLabel.setFont(Font.font("Arial", 10));
        timeLabel.setTextFill(Color.web("#888888"));

        VBox messageContent = new VBox(messageLabel, timeLabel);
        messageContent.setSpacing(2);

        messageBox.getChildren().add(messageContent);
        messageContainer.getChildren().add(messageBox);
    }

    public void handleInvio() {

        if (!Utility.checkObj(messageInput.getText())) {
            return;
        }
        Chat c = new Chat(mittente.getId_login(), id_destinatario, messageInput.getText(), LocalDateTime.now());
        mittente.inviaMessaggio(c);
        addMessage(c);
        messageInput.clear();

    }
}