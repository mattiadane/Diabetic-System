package com.dashapp.diabeticsystem.models;

public interface UpdatePersona {

    /**
     * Funzione che permette di eseguire un query a database per aggiornare i dati delle persone
     * @param p nuova persona per aggiornare i dati della persona corrente
     * @return <code>true</code> se la query è andata a buon fine, <code>false</code> altrimenti
     */
    boolean updatePersona(Persona p);

    /**
     * Funzione che permette di eseguire un query a database per aggiornare la password delle persone
     * @param password aggiorna la password della persona
     * @return <code>true</code> se la query è andata a buon fine, <code>false</code> altrimenti
     */
    boolean updatePassword(String password);
}
