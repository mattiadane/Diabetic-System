package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface InsulinaDao {

    /**
     * Funzione che permette di prendere le glicemie di un paziente da una data a un'altra
     * @param inizio data inizio da dove controllare
     * @param fine data fino a dove controllare
     * @param paziente paziente che si vuole prendere le glicemie
     * @return lista di glicemia da una data a un'altra
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
     * @param option se option != 0 prende tutte le glicemie, altrimenti solo quelle del giorno stesso
     * @return un oggetto <code>ObservableList<Insulina></code>
     */
    ObservableList<Insulina> getInsulina(Paziente paziente,Integer limit,int option);


    /**
     * Funzione che permette di rilevare quante misure di glicemia che il paziente ha inserito nel database
     * @param paziente che si vuole conoscere le misurazioni
     * @return il numero di glicemie inserite durante un giorno
     */
    int countDailyInsulinaByPatient(Paziente paziente);



    /**
     * Funzione che permette di verifcare se nel sistema è già presente una registrazione di glicemia in quel momento della giornata(del giorno stesso) di un determinato paziente
     * @param periodo periodo da controllare
     * @param paziente  paziente di cui si vuole sapere se ha già effetuato l'inserimento dwlla glicemia in quel momento della giornata
     */
    int coundDailyMomentOfDay(PERIODO periodo , Paziente paziente);

}
