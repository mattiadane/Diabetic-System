package com.dashapp.diabeticsystem.enums;

/**
 * Enumeratore per il tipo di utente che si è loggato tramite il form di login.
 *
 */
public enum ROLE {
    ADMIN("admin"),
    PAZIENTE("paziente"),
    DIABETOLOGO("diabetologo");

    private final String descrizione;

    ROLE(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return descrizione;
    }

    public static ROLE fromDescriptionToRole(String s){
        if(s ==  null){
            return null;
        }
        for(ROLE r : ROLE.values()){
            if(r.descrizione.equalsIgnoreCase(s)){
                return r;
            }
        }
        return null;
    }
}
