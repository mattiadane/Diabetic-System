package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.collections.ObservableList;

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
     * Funzione che permette di prendere tutte le terapie associate al paziente
     * @param paziente di cui si vogliono prendere le terapie
     * @return lista di terapie associate al paziente se presenti
     */
    ObservableList<Terapia> getAllTherapyByPatient(Paziente paziente);
}
