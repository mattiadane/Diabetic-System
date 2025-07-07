package com.dashapp.diabeticsystem.models;

import java.time.LocalDateTime;

public class AssunzioneFarmaco {

    private Farmaco farmaco;
    private String dosaggio_unita;
    private double dosaggio_quantita;
    private String sintomi;
    private LocalDateTime data_assunzione;


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
}
