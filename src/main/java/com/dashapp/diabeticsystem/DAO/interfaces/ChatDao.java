package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.Chat;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface ChatDao {

    /**
     * Funzione che permette di inviare un messaggio
     * @param chat da inserire a database
     * @return <code>True</code> se il messagio è stato inviato <code>false</code> altrimenti
     */
    boolean sendMessage(Chat chat);


    /**
     * Funzione che permette di restituire la chat tra un determinato paziente e diabetologo
     * @param paziente oggetto <code>Paziente</code>
     * @param diabetologo oggetto <code>Diabetologo</code>
     * @return La lista di messaggi della chat se presenti altrimenti null
     */
    ObservableList<Chat> chats(Paziente paziente, Diabetologo diabetologo);

    /**
     * Funzione che permette di prendere l'ultimo messagio di ogni paziente del diabetologo
     * @param diabetologo  il diabetologo
     * @return una mappa con chiave il paziente e set il suo ultimo messaggio
     */
    ObservableMap<Paziente,Chat> lastMessageEveryPatient(Diabetologo diabetologo);
      }
