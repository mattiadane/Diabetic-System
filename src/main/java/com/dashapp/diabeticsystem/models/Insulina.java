package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.enums.PERIODO;


import java.time.LocalDateTime;

public class  Insulina {

    private int id_glicemia;
    private final float livello_insulina;
    private final PERIODO periodo;
    private final LocalDateTime orario;
    private String sintomi;
    private boolean notificata;
    private Paziente paziente;


    public Insulina(float livello_insulina, PERIODO periodo, LocalDateTime orario) {
        this.livello_insulina = livello_insulina;
        this.periodo = periodo;
        this.orario = orario;

    }

    public Insulina(float livello_insulina, PERIODO periodo, LocalDateTime orario,String sintomi) {
        this(livello_insulina,periodo,orario);
        this.sintomi = sintomi;

    }

    public Insulina(float livello_insulina, PERIODO periodo, LocalDateTime orario,String sintomi,Paziente paziente) {
        this(livello_insulina, periodo, orario,sintomi);
        this.paziente = paziente;
    }

    public  Insulina(float livello_insulina, PERIODO periodo, LocalDateTime orario,String sintomi,Paziente paziente,boolean notificata,int id_glicemia){
        this(livello_insulina,periodo,orario,sintomi,paziente);
        this.notificata = notificata;
        this.id_glicemia = id_glicemia;
    }


    public int getId_glicemia() {
        return id_glicemia;
    }

    public boolean isNotificata() {
        return notificata;
    }

    public float getLivello_insulina() {
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


    public String getSintomo() {
        return sintomi;
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
