package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.Diabetologo;
import javafx.collections.ObservableList;

public interface DiabetologoDao {

    /**
     * Funzione che permette di restituire il Dibaetologo attraverso l'id_diabetolgo
     * @param id_diabetologo del diabetologo di cui si volgiono i dati
     * @return l'ogetto diabetologo se presente altrimenti null
     */
    Diabetologo getDiabetologistById(int id_diabetologo);


    /**
     * Funzione che permette di inserire un diabetologo nel database
     * @param diabetologo che verrà inserito nel sistema
     * @return id del diabetologo appena inserito
     */
    int insertDiabetologist(Diabetologo diabetologo);

    /**
     * Funzione che permette di recuperare tutti i diabetologi del sistema
     * @return Lista di tutti i diabetologi del sistema
     */
    ObservableList<Diabetologo> getAllDiabetologists();

    /**
     * Funzione che permette di rimuovere un diabetologo tramite il suo id
     * @param id_diabetologo da rimuovere
     * @return <code>true</code> se l'operazione va a buon fine <code>false</code> altrimenti
     */
    boolean removeDiabetologist(int id_diabetologo);

}
