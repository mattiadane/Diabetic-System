package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.enums.PERIODO;


import java.time.LocalDateTime;

public class  Insulina {
    private  int livello_insulina;
    private  PERIODO periodo;
    private  LocalDateTime orario;
    private GRAVITA gravita ;
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



    @Override
    public String toString() {
        return livello_insulina +","+periodo +","+orario;
    }



    public GRAVITA getGravita() {
        if(periodo.toString().startsWith("prima")){
            if(livello_insulina >= 80 && livello_insulina <= 130){
               this.gravita = GRAVITA.NORMALE;
            } else if((livello_insulina < 80 &&  livello_insulina >= 55) || (livello_insulina > 130 && livello_insulina <= 180)){
                this.gravita = GRAVITA.LIEVE;
            } else
                this.gravita = GRAVITA.CRITICA;
        } else {
            if (livello_insulina <= 70 || livello_insulina >= 250) {
                this.gravita = GRAVITA.CRITICA;
            } else if(livello_insulina > 180) {
                this.gravita = GRAVITA.LIEVE;
            } else {
                this.gravita = GRAVITA.NORMALE;
            }
        }
        return this.gravita;
    }
}
