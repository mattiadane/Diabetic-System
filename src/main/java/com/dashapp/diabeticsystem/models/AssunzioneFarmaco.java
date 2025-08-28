package com.dashapp.diabeticsystem.models;

import java.time.LocalDateTime;

public class AssunzioneFarmaco {

    private final Farmaco farmaco;
    private final String dosaggio_unita;
    private final double dosaggio_quantita;
    private final String sintomi;
    private final LocalDateTime data_assunzione;


    public AssunzioneFarmaco(Farmaco farmaco, String dosaggio_unita, double dosaggio_quantita, String sintomi, LocalDateTime data_assunzione) {
        this.farmaco = farmaco;
        this.dosaggio_unita = dosaggio_unita;
        this.dosaggio_quantita = dosaggio_quantita;
        this.sintomi = sintomi;
        this.data_assunzione = data_assunzione;
    }


    public Farmaco getFarmaco() {
        return farmaco;
    }

    public String getSintomi() {
        return sintomi;
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

    @Override
    public String toString() {
        return farmaco.toString() + " " + data_assunzione.toString() ;
    }
}
