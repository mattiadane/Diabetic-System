package com.dashapp.diabeticsystem.models;

import com.dashapp.diabeticsystem.utility.Utility;


public abstract class Persona implements UpdatePersona {
    private String nome;
    private String cognome;
    private String email;
    private String codice_fiscale;


    public Persona() {

    }

    public Persona(String nome, String cognome, String email,String codice_fiscale) {
        if(nome == null || nome.isEmpty() || cognome == null || cognome.isEmpty() || email == null || email.isEmpty() || codice_fiscale==null || codice_fiscale.isEmpty())
            throw new IllegalArgumentException("Il nome, il cognome e la email non posso essere null oppure stringhe vuote");

        this.nome = Utility.convertName(nome);
        this.cognome = Utility.convertName(cognome);
        this.email = email.trim().toLowerCase();
        this.codice_fiscale = codice_fiscale.trim().toUpperCase();
    }



    @Override
    public String toString() {
        return nome + " " + cognome;
    }


    /**
     * Funzione per prendere il nome della Persona
     * @return oggetto <code>String</code> per il nome del paziente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Funzione per prendere il cognome del Persona
     * @return oggetto <code>String</code> per il cognome del paziente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Funzione per prendere l'email del Persona
     * @return oggetto <code>String</code> per l'email del paziente
     */
    public String getEmail() {
        return email;
    }


    /**
     * Funzione per prendere il codice fiscale del Persona
     * @return oggetto <code>String</code> per il codice fiscale del paziente
     */

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    public void setNome(String nome) {
        this.nome = Utility.convertName(nome);
    }

    public void setCognome(String cognome) {
        this.cognome = Utility.convertName(cognome);
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase().trim();
    }

    public abstract boolean updatePersona(Persona p,String password);

}
