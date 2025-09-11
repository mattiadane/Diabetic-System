package com.dashapp.diabeticsystem.models;

import java.time.LocalDateTime;

public class Chat  {

    private final Paziente paziente;
    private final Diabetologo diabetologo;
    private final String messaggio;
    private final LocalDateTime data_invio;
    private final String ruolo;

    public Chat(Paziente paziente, Diabetologo diabetologo, String messaggio, LocalDateTime data_invio,String ruolo) {
        if(!ruolo.equals("paziente") && !ruolo.equals("diabetologo")){
            throw new IllegalArgumentException();
        }
        this.paziente = paziente;
        this.diabetologo = diabetologo;
        this.messaggio = messaggio;
        this.data_invio = data_invio;
        this.ruolo = ruolo;

    }

    public Paziente getPaziente() {
        return paziente;
    }

    public Diabetologo getDiabetologo() {
        return diabetologo;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public LocalDateTime getData_invio() {
        return data_invio;
    }

    public String getRuolo() {
        return ruolo;
    }
}
