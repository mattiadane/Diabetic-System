package com.dashapp.diabeticsystem.utility;

public class CredentialsGenerator {

    private String first;
    private String last;
    private int id_paziente;
    private int id_diabetologo;

    public CredentialsGenerator(int id_paziente, int id_diabetologo,String first, String last){
        if( first == null || first.isEmpty() || last == null || last.isEmpty())
            throw new IllegalArgumentException("Il nome e il cognome non posso essere null oppure stringhe vuote");

        this.first = first;
        this.last = last;
        this.id_paziente = id_paziente;
        this.id_diabetologo = id_diabetologo;
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
        String username = (id_diabetologo > 0 && id_paziente == 0) ? "med_" : "paz_";
        username += (this.first + "." + this.last + Integer.toString((this.id_diabetologo+this.id_paziente)));
        return username.replaceAll("\\s+", "").toLowerCase();
    }

}
