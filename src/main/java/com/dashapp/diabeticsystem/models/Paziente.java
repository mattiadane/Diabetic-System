package com.dashapp.diabeticsystem.models;

import java.time.LocalDate;

public class Paziente extends Persona {
    private Diabetologo diabetologo;


    private int id_paziente ;
    private final LocalDate dataNascita;

    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso,Diabetologo diabetologo) {
        super(nome,cognome,email,codiceFiscale,sesso);
        this.dataNascita = dataNascita;
        this.diabetologo = diabetologo;
        if(Session.getCurrentUser() != null){
            this.id_paziente = Session.getCurrentUser().getId_paziente();
        }
    }
    public Paziente(int id_paziente, String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso,Diabetologo diabetologo) {
        this(nome, cognome, email, codiceFiscale, dataNascita,sesso,diabetologo);
        this.id_paziente = id_paziente;

    }


    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso) {
        super(nome,cognome,email,codiceFiscale,sesso);
        this.dataNascita = dataNascita;
        if(Session.getCurrentUser() != null){
            this.id_paziente = Session.getCurrentUser().getId_paziente();
        }

    }

    public Paziente(int id_paziente, String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita,String sesso) {
        this(nome, cognome, email, codiceFiscale, dataNascita,sesso);
        this.id_paziente = id_paziente;
    }



    public Diabetologo getDiabetologo() {
        return diabetologo;
    }

    /**
     * Funzione per prendere la data di nascita del paziente
     * @return oggetto <code>Date</code> per la data di nascita del paziente.
     */
    public LocalDate getDataNascita() {
        return dataNascita;
    }

    /**
     * Funzione per prendere l'id del paziente
     * @return oggetto <code>int</code> per l'id del paziente.
     */
    public int getId_paziente() {
        return id_paziente;
    }

    /**
     * Funzione per settare l'id del paziente
     * @param id_paziente di da assegnare al paziente
     */
    public void setId_paziente(int id_paziente) {
        this.id_paziente = id_paziente;
    }

}
