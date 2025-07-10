package com.dashapp.diabeticsystem.controllers;

import com.dashapp.diabeticsystem.models.Chat;
import com.dashapp.diabeticsystem.models.Login;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Session;
import com.dashapp.diabeticsystem.utility.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatPazienteController {
    @FXML
    private Label contatto;
    @FXML
    private VBox messageContainer;
    @FXML
    private TextField messageInput;
    @FXML
    private ScrollPane messageScrollPane;

    Paziente p = new Paziente();
    private final Login mittente = Session.getCurrentUser();
    private final int id_destinatario = mittente.getid_loginDibaetologo(p.getMyDiabetologo());

    public void initialize(){
        contatto.setText(p.getMyDiabetologo().toString());

        loadMessagesFromDatabase();

        messageContainer.heightProperty().addListener((obs, oldVal, newVal) -> messageScrollPane.setVvalue(1.0));
    }




    private void loadMessagesFromDatabase() {
        messageContainer.getChildren().clear(); // Questa è la riga che dovrebbe pulire

        ObservableList<Chat> messages = mittente.chatDiabetologoPaziente(id_destinatario, mittente.getId_login());
        for (Chat msg : messages) {
            addMessage(msg);
        }
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
        if(!Utility.checkObj(messageInput.getText())){
            return;
        }
        Chat c = new Chat(mittente.getId_login(), id_destinatario, messageInput.getText(), LocalDateTime.now());
        mittente.inviaMessaggio(c);
        addMessage(c);
        messageInput.clear();
    }
}
