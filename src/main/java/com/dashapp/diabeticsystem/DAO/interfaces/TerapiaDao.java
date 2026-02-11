package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public interface TerapiaDao {

    /**
     * Funzione che permette di inserire una nuova terapia a database
     * @param terapia oggetto di tipo <code>Terapia</code> da inserire a database
     * @return valore di tipo <code>boolean</code> per controllare che l'inserimento è andato a buon fine.
     */
    boolean insertTherapy(Terapia terapia);

    /**
     * Funzione che permette di rimuovere una terapia a database
     * @param id_terapia  id della terapia da rimuovere
     * @return valore di tipo <code>boolean</code> per controllare che la rimozione è andata a buon fine.
     */
    boolean removeTherapy(int id_terapia);

    /**
     * Funzione che permette di modificare una terapia
     * @param terapia da modificare
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    boolean updateTherapy(Terapia terapia);

    /**
     * Funzione che permette di prendere tutte le terapie associate al paziente , e se presenti in una determinata data
     * @param paziente di cui si vogliono prendere le terapie
     * @param date data in cui si vuole sapere se in quel determinato giorno ha terapie attuve
     * @return lista di terapie associate al paziente se presenti
     */
    ObservableList<Terapia> getAllTherapyByPatientByDate(Paziente paziente, LocalDate date);


    /**
     * Funzionche che permette di prendere la taerpia di un paziente con il rispettivo farmaco
     * @param farmaco presente nella terapia
     * @param paziente che risulta nella terapia
     * @return la terapia desiderata se presente nel database
     */
    Terapia getTherapyByFarmacoIdAndPatient(Paziente paziente,Farmaco farmaco);
}
