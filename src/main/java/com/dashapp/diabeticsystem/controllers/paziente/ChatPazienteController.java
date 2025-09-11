package com.dashapp.diabeticsystem.controllers.paziente;

import com.dashapp.diabeticsystem.DAO.implementations.ChatDaoImpl;
import com.dashapp.diabeticsystem.DAO.implementations.PazienteDaoImpl;
import com.dashapp.diabeticsystem.DAO.interfcaes.ChatDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.PazienteDao;
import com.dashapp.diabeticsystem.models.*;
import com.dashapp.diabeticsystem.utility.Utility;
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


    private final ChatDao chatDao = new ChatDaoImpl();
    private final PazienteDao pazienteDao = new PazienteDaoImpl();
    private final Paziente mittente = pazienteDao.getPatientById(Session.getCurrentUser().getId_paziente());
    private final Diabetologo destinatario = mittente.getDiabetologo();



    @FXML
    private Label contatto;
    @FXML
    private VBox messageContainer;
    @FXML
    private TextField messageInput;
    @FXML
    private ScrollPane messageScrollPane;



    public void initialize(){
        contatto.setText(destinatario.toString());

        loadMessagesFromDatabase();

        messageContainer.heightProperty().addListener((obs, oldVal, newVal) -> messageScrollPane.setVvalue(1.0));
    }




    private void loadMessagesFromDatabase() {
        messageContainer.getChildren().clear();

        for (Chat msg : chatDao.chats(mittente,destinatario)) {
            addMessage(msg);
        }
    }

    private void addMessage(Chat msg) {
        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(5, 10, 5, 10));

        Label messageLabel = new Label(msg.getMessaggio());
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(250);

        String sender = msg.getRuolo().equals("paziente") ? "Mittente" : "Destinatario";

        // Stile del messaggio in base al mittente
        if (sender.equals("Mittente")) {
            messageBox.setAlignment(Pos.CENTER_RIGHT);
            messageLabel.setStyle("-fx-background-color: #DCF8C6; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 8;");
        } else {
            messageBox.setAlignment(Pos.CENTER_LEFT);
            messageLabel.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 8; -fx-border-color: #ddd; -fx-border-width: 1;");
        }
        messageLabel.setFont(Font.font("Arial", 14));


        LocalDateTime now = msg.getData_invio();

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
        Chat chat = new Chat(mittente, destinatario, messageInput.getText(), LocalDateTime.now(),"paziente");
        if(!chatDao.sendMessage(chat))
            return;
        addMessage(chat);
        messageInput.clear();
    }
}
