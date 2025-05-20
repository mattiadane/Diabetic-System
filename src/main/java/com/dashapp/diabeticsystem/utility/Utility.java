package com.dashapp.diabeticsystem.utility;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe con metodi <code>static</code> contenente una serie di funzioni per vari controlli da usare all'interno di
 * tutto il programma.
 * @author Aledpl5
 */
public class Utility {

    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]\\d|2[0-3]):[0-5]\\d$");


    /**
     * Funzione che permette di controllare la validità della email inserita nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità della email.
     */
    public static boolean isEmailValid(String email) {
        if(email.isEmpty()){
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

    public static boolean checkPassword(String password){
        return password != null && !password.isEmpty();
    }

    public static boolean checkInsulina(String number){

        if(number == null || number.isEmpty()  ) return false;
        try{
            int n = Integer.parseInt(number);
            return n >= 0 && n <= 150;
        }catch(NumberFormatException e){
            return false;
        }


    }

    /**
     * Funzione che permette di controllare la validità dell'orario inserito all'interno di un campo
     * di testo.
     * @param time <code>String</code> con l'orario inserito
     * @return <code>true</code> se l'orario segue il pattern, <code>false</code> altrimenti.
     */
    public static boolean checkTime(String time){
        if(time == null || time.isEmpty()) return false;
        return TIME_PATTERN.matcher(time).matches();
    }

    /**
     * Funzione che permette di resettare al valore di default un determinato componente
     * @param parent componente passato come parametro da resettare
     */
    public static void resetField(Pane parent){
        for(Node child : parent.getChildren()){
            if( child instanceof TextField){
                ((TextField) child).setText("");
            } else if (child instanceof DatePicker){
                ((DatePicker) child).setValue(null);
            } else if (child instanceof ComboBox<?>){
                ((ComboBox<?>) child).setValue(null);
            } else if (child instanceof Pane) {
                resetField((Pane) child);
            }
        }
    }

    public static String convertName(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static String convertSurname(String surname){
        return surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
    }

}
