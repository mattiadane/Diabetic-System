package com.dashapp.diabeticsystem.models;

public interface UpdatePersona {

    /**
     * Funzione che permette di eseguire un query a database per aggiornare i dati delle persone
     * @param p nuova persona per aggiornare i dati della persona corrente
     * @param password nuova password della persona
     * @return <code>true</code> se la query è andata a buon fine, <code>false</code> altrimenti
     */
    boolean updatePersona(Persona p,String password);
}
