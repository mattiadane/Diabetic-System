package com.dashapp.diabeticsystem.models;

import java.time.LocalDateTime;

public class AssunzioneFarmaco {

    private final Farmaco farmaco;
    private final String dosaggio_unita;
    private final double dosaggio_quantita;
    private final LocalDateTime data_assunzione;
    private final Paziente paziente;


    public AssunzioneFarmaco(Farmaco farmaco, String dosaggio_unita, double dosaggio_quantita, LocalDateTime data_assunzione, Paziente paziente) {
        this.farmaco = farmaco;
        this.dosaggio_unita = dosaggio_unita;
        this.dosaggio_quantita = dosaggio_quantita;
        this.data_assunzione = data_assunzione;
        this.paziente = paziente;
    }


    public Farmaco getFarmaco() {
        return farmaco;
    }


    public LocalDateTime getData_assunzione() {
        return data_assunzione;
    }

    public double getDosaggio_quantita() {
        return dosaggio_quantita;
    }

    public String getDosaggio_unita() {
        return dosaggio_unita;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    @Override
    public String toString() {
        return farmaco.toString() + " " + data_assunzione.toString() ;
    }
}
