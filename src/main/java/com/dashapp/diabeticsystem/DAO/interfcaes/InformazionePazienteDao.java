package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.InformazioniPaziente;

public interface InformazionePazienteDao {


    /**
     * Funzione che permette di aggiungere le informazione del paziente quali commorbità ecc...
     * @param id_paziente del paziente da associare alle proprie informazioni
     * @param info
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    boolean insertInformation(int id_paziente ,InformazioniPaziente info);

}
