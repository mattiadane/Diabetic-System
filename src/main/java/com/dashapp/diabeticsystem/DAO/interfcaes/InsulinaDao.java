package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface InsulinaDao {

    /**
     * Funzione che permette di prendere le glicemie di un paziente da una data ad un altra
     * @param inizio data inizio da dove controllare
     * @param fine data fino a dove controllare
     * @param paziente paziente che si vuole prendere le glicemie
     * @return lista di glicemia da una data ad un altra
     */
    ObservableList<Insulina> getInsulinaByDateAndByPatients(LocalDateTime inizio, LocalDateTime fine, Paziente paziente);


    /**
     * Funzione che permette di inserire il livello di glicemia del paziente
     * @param insulina che verrà inserita nel sistema
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    boolean insertInsulina(Insulina insulina);



    /**
     * Funzione che permette di eseguire una chiamata a database per fare in modo di prendersi tutti i livelli di glicemia da lui registrati con restrizzioni o non
     * @param limit limite per il numero di registrazioni da prendere. <code>null</code> per non avere restrizioni, un valore <code>int</code> per mettere un limite
     * @param paziente di cui prendere le glicemie
     * @param option se option != 0 prende tutte le glicemia altrienti prende solo quelle del giorno stesso
     * @return un oggetto <code>ObservableList<Insulina></code>
     */
    ObservableList<Insulina> getInsulina(Paziente paziente,Integer limit,int option);


    /**
     * Funzione che permette di rilevare quante misure di glicemia che il paziente ha inserito nel database
     * @param paziente che si vuole conoscere le misurazioni
     * @return il numero di glicemie inserite durante un giorno
     */
    int countDailyInsulinaByPatient(Paziente paziente);

}
