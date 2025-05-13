package com.dashapp.diabeticsystem.models;

public class PasswordGenerator {

    private String first;
    private String last;

    public PasswordGenerator(String first, String last){
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

}
