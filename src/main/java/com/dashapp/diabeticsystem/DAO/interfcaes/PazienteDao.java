package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.InformazioniPaziente;
import com.dashapp.diabeticsystem.models.Paziente;

public interface PazienteDao {

    /**
     * funzione che permette di inserire un nuovo paziente nel database
     * @param paziente da inserire nel database
     * @return  l'id del paziente appena inserito
     */
    int insertPatient(Paziente paziente);

    boolean updatePatient(Paziente paziente);

    boolean deletePatient(Paziente paziente);

    Paziente loadPazienteById(int id);
}
