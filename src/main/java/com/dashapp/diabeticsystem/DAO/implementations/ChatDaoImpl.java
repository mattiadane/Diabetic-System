package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.ChatDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Chat;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatDaoImpl  implements ChatDao {

    @Override
    public boolean sendMessage(Chat chat) {
        if(chat == null) return false;
        return Main.getDbManager().updateQuery("INSERT INTO chat(id_paziente,id_diabetologo,messaggio,mittente) VALUES(?,?,?,?)",
                chat.getPaziente().getId_paziente(),chat.getDiabetologo().getId_diabetologo(),chat.getMessaggio(),chat.getRuolo());

    }

    @Override
    public ObservableList<Chat> chats(Paziente paziente, Diabetologo diabetologo) {
        ObservableList<Chat> messages = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery("SELECT id_paziente,id_diabetologo,messaggio,data_invio,mittente FROM chat " +
                        "WHERE (id_paziente = ? AND id_diabetologo = ?) ORDER BY data_invio ASC ",
                rs -> {
                    while(rs.next()){

                        messages.add(
                                new Chat(
                                        paziente,
                                        diabetologo,
                                        rs.getString("messaggio"),
                                        rs.getTimestamp("data_invio").toLocalDateTime(),
                                        rs.getString("mittente")
                                )
                        );

                    }
                    return null;
                }
                ,paziente.getId_paziente(),diabetologo.getId_diabetologo());
        return messages;
    }
}
