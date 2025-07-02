package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.PERIODO;

import java.time.LocalDateTime;

public class  Insulina {
    private  int livello_insulina;
    private  PERIODO periodo;
    private  LocalDateTime orario;
    private  boolean checkAssunzione;

    public Insulina(int livello_insulina, PERIODO periodo, LocalDateTime orario, boolean checkAssunzione) {
        this.livello_insulina = livello_insulina;
        this.periodo = periodo;
        this.orario = orario;
        this.checkAssunzione = checkAssunzione;
    }

    public Insulina(){}

    public int getLivello_insulina() {
        return livello_insulina;
    }

    public PERIODO getPeriodo() {
        return periodo;
    }
    public LocalDateTime getOrario() {
        return orario;
    }

    public boolean getAssuzione(){ return this.checkAssunzione; }
}
