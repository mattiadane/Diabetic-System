package com.dashapp.diabeticsystem.models;


public class Diabetologo extends Persona  {

    private int id_diabetologo ;


    public Diabetologo(String nome, String cognome, String email,String codice_fiscale,String sesso){
        super(nome,cognome,email,codice_fiscale,sesso);
    }
    public Diabetologo(int id_diabetologo, String  nome, String cognome, String email,String codice_fiscale,String sesso){
        this(nome,cognome,email,codice_fiscale,sesso);
        this.id_diabetologo = id_diabetologo;
    }

    /**
     * Funzione per settare l'id del paziente
     * @param id_diabetologo di da assegnare al paziente
     */
    public void setId_diabetologo(int id_diabetologo) {
        this.id_diabetologo = id_diabetologo;
    }

    public int getId_diabetologo() {
        return id_diabetologo;
    }


}
