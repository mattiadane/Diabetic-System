package com.dashapp.diabeticsystem.utility;

import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    private static final Pattern ONLY_LETTERS = Pattern.compile("[a-zA-Z]+");
    private static final Pattern ONLY_NUMBER_INT = Pattern.compile("\\d+");
    private static final Pattern ONLY_NUMBER_DOUBLE = Pattern.compile("\\d+\\.\\d+");


    /**
     * Funzione che permette di controllare la validità della email inserita nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità della email.
     */
    public static boolean isEmailValid(String email) {
        if(!checkObj(email)) return false;

        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Funzione che permette di controllare la validità del codice fiscale inserito nel form del nuovo utente.
     * @return un valore di tipo <code>boolean</code> per la validità del codice fiscale.
     */
    public static boolean isCodiceFiscaleValid(String cf) {
        if (!checkObj(cf)) return false;

        Pattern p = Pattern.compile("^[A-Z]{6}\\d{2}[A-EHLMPRST]\\d{2}[A-Z0-9]{4}[A-Z]$");
        Matcher m = p.matcher(cf.toUpperCase().trim());

        return m.matches();
    }

    /**
     * Funzione che permette di controllare la validità di una data passata come parametro.
     * @param date oggetto di tipo <code>LocalDate</code> da controllare.
     * @return <code>true</code> se passa i controlli, <code>false</code> altrimenti.
     */
    public static boolean checkDateNascita(LocalDate date){
        if(!checkObj(date)) return false;

        return !date.isAfter(LocalDate.now());
    }

    /**
     * Controlla di parametri base per la validità di un qualsiasi oggetto.
     * @param o oggetto qualsiasi
     * @return <code>true</code> se sono veri i controlli, <code>false</code> altrimenti.
     */
    public static boolean checkObj(Object o){
        return o != null && !o.toString().isEmpty();
    }


    /**
     * Funzione che controlla la validità numerica dell'insulina inserita da un paziente.
     * @param number valore del livello di insulina.
     * @return <code>true</code> se passa i controlli, <code>false</code> altrimenti.
     */
    public static boolean checkInsulina(String number){
        if( !checkObj(number) ) return false;
        try{
            double n = Double.parseDouble(number);
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
        if(!checkObj(time)) return false;
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

    /**
     *
     * @param name
     * @return
     */
    public static String convertName(String name){
        String[] words = name.trim().split(" "); // Suddivide la stringa in parole basandosi sugli spazi
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.trim().isEmpty()) { // Ignora spazi multipli che potrebbero creare parole vuote
                result.append(word.substring(0, 1).toUpperCase()) // Prima lettera maiuscola
                        .append(word.substring(1).toLowerCase())    // Resto della parola minuscolo
                        .append(" "); // Aggiunge uno spazio dopo ogni parola
            }
        }
        return result.toString().trim(); // Rimuove lo spazio finale in eccesso
    }

    /**
     * Funzione che permette di creare un Alert personalizzato.
     * @param type tipo dell'alert.
     * @param message messaggio da mostrare all'utente.
     */
    public static void createAlert(Alert.AlertType type, String message){
        Alert alert = new Alert(type);

        switch (type){
            case INFORMATION:
                alert.setTitle("Informazione");
                break;
            case WARNING:
                alert.setTitle("Attenzione!");
                break;
            case ERROR:
                alert.setTitle("Errore");
                break;
        }
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Funzione che controlla se una determinata stringa contiene solamente lettere.
     * @param s stringa da controllare
     * @return <code>true</code> se la stringa contiene solo lettere, <code>false</code> altrimenti
     */
    public static boolean checkOnlyLetters(String s){
        if(!checkObj(s)) return false;
        return ONLY_LETTERS.matcher(s).matches();
    }

    /**
     * Funzione che permette di controllare la validità a livello di oggetto <code>String</code> di un numero (come pattern).
     * @param s stringa da controllare.
     * @return <code>true</code> se è un numero valido (come pattern), <code>false</code> altrimenti.
     */
    public static boolean checkOnlyNumbers(String s) {
        if (!checkObj(s)) return false;
        return ONLY_NUMBER_INT.matcher(s).matches() || ONLY_NUMBER_DOUBLE.matcher(s).matches();
    }


    /**
     * Funzione che permette di controllare che la data di inizio sia minore della data di fine  e che la data di fine sia maggiore o uguale ad oggi
     * e la data di oggi sia maggiore di oggi

     */
    public static boolean checkDates(LocalDate data_inizio, LocalDate data_fine){
        if(!checkObj(data_inizio) || !checkObj(data_fine)) return false;

        if(data_inizio.isAfter(data_fine) || data_inizio.isEqual(data_fine)) return false;

        return (data_inizio.isAfter(LocalDate.now()) || data_inizio.isEqual(LocalDate.now())) && (data_fine.isAfter(LocalDate.now()));
    }



}
