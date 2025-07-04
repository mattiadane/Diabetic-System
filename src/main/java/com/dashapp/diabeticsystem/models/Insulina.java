package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.enums.PERIODO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class  Insulina {
    private  int livello_insulina;
    private  PERIODO periodo;
    private  LocalDateTime orario;

    public Insulina(int livello_insulina, PERIODO periodo, LocalDateTime orario) {
        this.livello_insulina = livello_insulina;
        this.periodo = periodo;
        this.orario = orario;
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




    public void setLivelloInsulina(int livello_insulina){
        this.livello_insulina = livello_insulina;
    }

    public void setPeriodo(PERIODO periodo){
        this.periodo = periodo;
    }

    public void setOrario(LocalDateTime orario){
        this.orario = orario;
    }

    @Override
    public String toString() {
        return livello_insulina +","+periodo +","+orario;
    }
}
