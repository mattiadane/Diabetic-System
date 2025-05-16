package com.dashapp.diabeticsystem.models;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe con metodi <code>static</code> contenente una serie di funzioni per vari controlli da usare all'interno di
 * tutto il programma.
 * @author Aledpl5
 */
public class Utility {


    /**
     * Funzione che permette di controllare la validità della email inserita nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità della email.
     */
    public static boolean isEmailValid(String email) {
        if(email.isEmpty() || email == null){
            return false;
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Funzione che permette di controllare la validità del codice fiscale inserito nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità del codice fiscale.
     */
    public static boolean isCodiceFiscaleValid(String cf) {
        if (cf == null || cf.isEmpty()) {
            return false;
        }

        Pattern p = Pattern.compile("^[A-Z]{6}\\d{2}[A-EHLMPRST]\\d{2}[A-Z0-9]{4}[A-Z]$");
        Matcher m = p.matcher(cf.toUpperCase().trim());

        return m.matches();
    }

    /**
     * Funzione che permette di controllare la validità dei campi nome e congome del form per aggiungere un nuovo paziente
     * @return un valore di tipo <code>boolean</code> per la validità dei campi nome e cognome
     */
    public static boolean checkCredenziali(String nome, String cognome){
        return !nome.isBlank() && !cognome.isBlank();
    }

    public static boolean checkDate(LocalDate date){
        return date != null && !date.isAfter(LocalDate.now());
    }

}
