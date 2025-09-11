package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.ObservableList;

public interface FarmacoDao {
    /**
     * Funzione che permette di prendere un determinato farmaco
     * @param name nome del farmaco da cercare
     * @return <code>null</code> se il farmaco e non è presente nella lista, oggetto <code>Farmaco</code> altrimenti.
     */
    Farmaco getDrugByName(String name);

    /**
     * Funzione che permette, tramite chiamata a database, di prendere tutti i farmaci registrati.
     * @return oggetto <code>ObservableList<Farmaco></code> di tutti i farmarci presenti nel database
     */
    ObservableList<Farmaco> getAllDrugs();

    /**
     * Funzione che permette di prendere un determinato farmaco
     * @param id_farmaco id del farmaco da cercare
     * @return <code>null</code> se la lista dei farmaci è vuota oppure non è presente, oggetto <code>Farmaco</code> altrimenti.
     */
    Farmaco getDrugById(int id_farmaco);


    /**
     * Funzione che permette di recuperare tutti i farmaci che un determinato paziente deve assumere
     * @param paziente che si vogliono conoscere tutti i suoi farmaci delle sue terapie
     * @return lista di farmaci se presenti altrimenti null
     */
    ObservableList<Farmaco> getAllDrugsByPaziente(Paziente paziente);
}
