package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.ROLE;

import java.time.LocalDateTime;

public class Chat  {

    private final Paziente paziente;
    private final Diabetologo diabetologo;
    private final String messaggio;
    private final LocalDateTime data_invio;
    private final ROLE ruolo;

    public Chat(Paziente paziente, Diabetologo diabetologo, String messaggio, LocalDateTime data_invio,ROLE ruolo) {
        if(!ruolo.toString().equals("paziente") && !ruolo.toString().equals("diabetologo")){
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

    public ROLE getRuolo() {
        return ruolo;
    }
}
