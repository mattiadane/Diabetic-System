package com.dashapp.diabeticsystem.models;

import java.time.LocalDate;

public class Paziente {
    private int id_paziente ;
    private final String nome;
    private final String cognome;
    private final String email;
    private final String codiceFiscale;
    private final LocalDate dataNascita;

    public Paziente(String nome,String cognome,String email,String codiceFiscale,LocalDate dataNascita) {
        if(nome == null || cognome == null || email == null || codiceFiscale == null || dataNascita == null)
            throw new IllegalArgumentException("Errore nel passaggio dei parametri per creare il paziente.");


        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.codiceFiscale = codiceFiscale;
        this.dataNascita = dataNascita;
    }



    /**
     * Funzione per prendere il nome del paziente
     * @return oggetto <code>String</code> per il nome del paziente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Funzione per prendere il cognome del paziente
     * @return oggetto <code>String</code> per il cognome del paziente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Funzione per prendere l'email del paziente
     * @return oggetto <code>String</code> per l'email del paziente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Funzione per prendere il codice fiscale del paziente
     * @return oggetto <code>String</code> per il codice fiscale del paziente
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
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
