package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.*;

import java.util.List;

public interface InformazionePazienteDao {

    /**
     * Funzione che permette di aggiungere le informazione del paziente quali commorbità, ...
     * @param info <code>InformazioniPaziente</code> da aggiungere al database
     * @return id dell'informazione appena inserita
     */
    int insertInformation(InformazioniPaziente info);


    /**
     * @param paziente paziente di cui si volgiono sapere le comorbità
     * @return lista di tutte le comorbità del paziente
     */
    List<Comorbità> getComorbita(Paziente paziente);

    /**
     * @param paziente paziente di cui si volgiono sapere i fattori di rischio
     * @return lista di tutti i fattori di rischio del paziente
     */
    List<FattoreRischio> getFattoriRischio(Paziente paziente);


    /**
     * @param paziente paziente di cui si volgiono sapere le patologie pregresse
     * @return lista di tutti le patologie pregresse  del paziente
     */
    List<PatologiaPregressa> getPatologiePregresse(Paziente paziente);


}
