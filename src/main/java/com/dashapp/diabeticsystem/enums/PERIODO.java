package com.dashapp.diabeticsystem.enums;

public enum PERIODO {
    PRIMA_DELLA_COLAZIONE("prima della colazione"),
    DOPO_LA_COLAZIONE("dopo la colazione"),
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

    /**
     * Restituisce la costante PERIODO corrispondente alla descrizione fornita.
     * La ricerca è case-insensitive e ignora spazi iniziali/finali.
     *
     * @param text La stringa di descrizione da cercare (es. "prima della colazione").
     * @return La costante PERIODO corrispondente.
     * @throws IllegalArgumentException Se nessuna costante PERIODO corrisponde alla descrizione fornita.
     */
    public static PERIODO fromDescrizione(String text) {
        if (text == null) {

            throw new IllegalArgumentException("La descrizione del PERIODO non può essere null.");
        }

        for (PERIODO periodo : PERIODO.values()) {

            if (periodo.descrizione.equalsIgnoreCase(text.trim())) {
                return periodo;
            }
        }

        throw new IllegalArgumentException("Nessun PERIODO trovato per la descrizione: '" + text + "'");
    }
}
