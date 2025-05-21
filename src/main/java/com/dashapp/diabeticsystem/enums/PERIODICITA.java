package com.dashapp.diabeticsystem.enums;

public enum PERIODICITA {
    GIORNO("giorno"),
    SETTIMANA("settimana"),
    MESE("mese");

    private final String descrizione;

    PERIODICITA(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return descrizione;
    }
}
