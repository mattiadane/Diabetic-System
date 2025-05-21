package com.dashapp.diabeticsystem.enums;

public enum PERIODO {
    PRIMA_DEL_PRANZO("prima del pranzo"),
    DOPO_PRANZO("dopo pranzo"),
    PRIMA_DELLA_CENA("prima di cena"),
    DOPO_CENA("dopo cena");

    private final String descrizione;

    PERIODO(String descrizione){
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return descrizione;
    }
}
