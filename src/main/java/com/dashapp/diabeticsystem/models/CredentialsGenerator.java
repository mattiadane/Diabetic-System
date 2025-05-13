package com.dashapp.diabeticsystem.models;

public class CredentialsGenerator {

    private String first;
    private String last;

    public CredentialsGenerator(String first, String last){
        if(first == null || first.isEmpty() || last == null || last.isEmpty())
            throw new IllegalArgumentException("Il nome e il cognome non posso essere null oppure stringhe vuote");

        this.first = first;
        this.last = last;
    }

    /**
     * Funzione che permette di generare un password, concatenando il automatico il nome, il cognome e il timestamp in
     * millisecondi al momento della creazione della password
     * @return un oggetto <code>String</code> per la password generata
     */
    public String generatePassword(){
        return this.first + this.last + System.currentTimeMillis();
    }

    /**
     * Funzione che permette di generare automaticamente lo username dell'utente
     * @return un tipo <code>String</code> per lo username dell'utente
     */
    public String createUsername(){
        return (this.first + "." + this.last).replaceAll("\\s+", "").toLowerCase();
    }

}
