package com.dashapp.diabeticsystem.DAO.interfaces;


import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.ObservableList;

public interface PazienteDao {

    /**
     * funzione che permette di inserire un nuovo paziente nel database
     * @param paziente da inserire nel database
     * @return  l'id del paziente appena inserito
     */
    int insertPatient(Paziente paziente);


    /**
     * Funzione che permette di modifcare i dati anagrafici di un paziente
     * @param paziente da modificare
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    boolean updatePatient(Paziente paziente);

    /**
     * Funzione che permette di rimuovere un paziente
     * @param id_paziente da rimuovere dal database
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    boolean deletePatient(int id_paziente);


    /**
     * Funzione che permette di ricercare un paziente tramite codice fiscale
     * @param cf codice_fiscale del paziente da cercare
     * @return L'oggetto paziente se trovato altrimenti null
     */
    Paziente getPatientByCf(String cf);

    /**
     * Funzione che permette di recuperare tutti i pazienti associati ad un diabetologo
     * @param id_diabetologo da ricercare
     * @return lista di tutti i pazienti associato a tale diabetologo
     */
    ObservableList<Paziente> getAllPatientsByDiabetologist(int id_diabetologo);

    /**
     * Funzione che permette di cercare un paziente all'interno della lista dei pazienti associati al diabetologo.
     * @param search oggetto ti tipo <code>String</code> che contiene il codice fiscale del paziente da cercare.
     * @return oggetto di tipo <code>ObservableList</code> con i pazienti che hanno il codice fiscale che inizia con il parametro passato.
     */
    ObservableList<Paziente> getPazientiResearch(String search);


    /**
     * Funzione che permette di ricerca un paziente con l'id
     * @param id_paziente da ricercare
     * @return Il paziente se presente altrimenti null
     */
    Paziente getPatientById(int id_paziente);
}
