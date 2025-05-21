package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.PERIODO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Insulina {
    private final int livello_insulina;
    private final PERIODO periodo;
    private final LocalDateTime orario;

    public Insulina(int livello_insulina, PERIODO periodo, LocalDateTime orario) {
        this.livello_insulina = livello_insulina;
        this.periodo = periodo;
        this.orario = orario;
    }


    public int getLivello_insulina() {
        return livello_insulina;
    }

    public PERIODO getPeriodo() {
        return periodo;
    }
    public LocalDateTime getOrario() {
        return orario;
    }
}
