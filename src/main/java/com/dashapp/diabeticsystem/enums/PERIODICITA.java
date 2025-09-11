package com.dashapp.diabeticsystem.enums;

public enum PERIODICITA {
    GIORNO("giorno");

    private final String descrizione;

    PERIODICITA(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return descrizione;
    }


    public static PERIODICITA fromDescrizione(String s) {
        for (PERIODICITA value : PERIODICITA.values()) {
            if(value.descrizione.equalsIgnoreCase(s)){
                return value;
            }
        }
        return null;
    }
}
