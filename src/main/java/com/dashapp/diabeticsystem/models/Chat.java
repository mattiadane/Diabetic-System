package com.dashapp.diabeticsystem.models;

import java.time.LocalDateTime;

public class Chat implements Comparable<Chat>  {

    private final int id_mittente;
    private final int id_destinatario;
    private final String messaggio;
    private final LocalDateTime data_invio;

    public Chat(int id_mittente, int id_destinatario, String messaggio, LocalDateTime data_invio) {
        this.id_mittente = id_mittente;
        this.id_destinatario = id_destinatario;
        this.messaggio = messaggio;
        this.data_invio = data_invio;
    }

    public int getId_mittente() {
        return id_mittente;
    }

    public int getId_destinatario() {
        return id_destinatario;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public LocalDateTime getData_invio() {
        return data_invio;
    }

    @Override
    public int compareTo(Chat chat) {
        return this.data_invio.compareTo(chat.data_invio);
    }
}
