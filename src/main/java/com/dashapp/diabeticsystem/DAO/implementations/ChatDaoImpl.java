package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.ChatDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.ROLE;
import com.dashapp.diabeticsystem.models.Chat;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class ChatDaoImpl  implements ChatDao {

    @Override
    public boolean sendMessage(Chat chat) {
        if(chat == null) return false;
        return Main.getDbManager().updateQuery("INSERT INTO chat(id_paziente,id_diabetologo,messaggio,mittente) VALUES(?,?,?,?)",
                chat.getPaziente().getId_paziente(), chat.getDiabetologo().getId_diabetologo(), chat.getMessaggio(), chat.getRuolo().toString());

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
                                        ROLE.fromDescriptionToRole(rs.getString("mittente"))
                                )
                        );

                    }
                    return null;
                }
                ,paziente.getId_paziente(),diabetologo.getId_diabetologo());
        return messages;
    }


    public ObservableMap<Paziente, Chat> lastMessageEveryPatient(Diabetologo diabetologo){
        ObservableMap<Paziente,Chat> pazientiChat = FXCollections.observableHashMap();
        Main.getDbManager().selectQuery(
                """
                        SELECT
                           p.nome,
                           p.cognome,
                           p.email,
                           p.codice_fiscale,
                           p.sesso,
                           p.id_paziente,
                           p.data_nascita,
                           MAX(c.data_invio) AS data,
                           (
                               SELECT c_sub.messaggio
                               FROM chat AS c_sub
                               WHERE c_sub.id_paziente = p.id_paziente AND c_sub.id_diabetologo = ?
                               ORDER BY c_sub.data_invio DESC
                               LIMIT 1
                           ) AS ultimo_messaggio,
                           (
                               SELECT c_sub.mittente
                               FROM chat AS c_sub
                               WHERE c_sub.id_paziente = p.id_paziente AND c_sub.id_diabetologo = ?
                               ORDER BY c_sub.data_invio DESC
                               LIMIT 1
                           ) AS mittente
                       FROM
                           paziente AS p
                       LEFT JOIN
                           chat AS c ON p.id_paziente = c.id_paziente
                       WHERE
                           p.id_diabetologo = ?
                       GROUP BY
                           p.id_paziente
                       ORDER BY
                           MAX(c.data_invio) DESC;""",

                rs -> {
                    while(rs.next()){
                        Chat c = null;
                        Paziente p =          new Paziente(rs.getInt("p.id_paziente"),rs.getString("p.nome"),
                                rs.getString("p.cognome"),rs.getString("p.email"),rs.getString("p.codice_fiscale"),
                                rs.getDate("p.data_nascita").toLocalDate(),rs.getString("p.sesso"));

                        if(rs.getString("ultimo_messaggio") != null && rs.getTimestamp("data") != null) {

                            c =  new Chat(p,diabetologo,rs.getString("ultimo_messaggio"),rs.getTimestamp("data").toLocalDateTime(),ROLE.fromDescriptionToRole(rs.getString("mittente")));

                        }
                        pazientiChat.put(p,c);
                    }
                    return null;
                }
                ,diabetologo.getId_diabetologo(),diabetologo.getId_diabetologo(),diabetologo.getId_diabetologo());
        return pazientiChat;
    }

}
