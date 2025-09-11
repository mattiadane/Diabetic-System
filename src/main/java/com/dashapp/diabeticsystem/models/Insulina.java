package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.enums.PERIODO;


import java.time.LocalDateTime;

public class  Insulina {
    private final int livello_insulina;
    private final PERIODO periodo;
    private final LocalDateTime orario;
    private Paziente paziente;

    public Insulina(int livello_insulina, PERIODO periodo, LocalDateTime orario) {
        this.livello_insulina = livello_insulina;
        this.periodo = periodo;
        this.orario = orario;
    }

    public Insulina(int livello_insulina, PERIODO periodo, LocalDateTime orario,Paziente paziente) {
        this(livello_insulina, periodo, orario);
        this.paziente = paziente;
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


    public Paziente getPaziente() {
        return paziente;
    }

    @Override
    public String toString() {
        return livello_insulina +","+periodo +","+orario;
    }



    public GRAVITA getGravita() {
        GRAVITA gravita;
        if(periodo.toString().startsWith("prima")){
            if(livello_insulina >= 80 && livello_insulina <= 130){
               gravita = GRAVITA.NORMALE;
            } else if((livello_insulina < 80 &&  livello_insulina >= 55) || (livello_insulina > 130 && livello_insulina <= 180)){
                gravita = GRAVITA.LIEVE;
            } else
                gravita = GRAVITA.CRITICA;
        } else {
            if (livello_insulina <= 70 || livello_insulina >= 250) {
                gravita = GRAVITA.CRITICA;
            } else if(livello_insulina > 180) {
                gravita = GRAVITA.LIEVE;
            } else {
                gravita = GRAVITA.NORMALE;
            }
        }
        return gravita;
    }
}
