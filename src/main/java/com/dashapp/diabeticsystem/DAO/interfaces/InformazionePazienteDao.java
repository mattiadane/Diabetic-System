package com.dashapp.diabeticsystem.DAO.interfaces;

import com.dashapp.diabeticsystem.models.InformazioniPaziente;
import com.dashapp.diabeticsystem.models.Paziente;

public interface InformazionePazienteDao {

    /**
     * Funzione che permette di aggiungere le informazione del paziente quali commorbità, ...
     * @param info <code>InformazioniPaziente</code> da aggiungere al database
     * @return id dell'informazione appena inserita
     */
    int insertInformation(InformazioniPaziente info);

    /**
     * Funzione che permette di prendere le informazioni tramite l'id
     * @param id <code>int</code> dell'informazione da ricercare
     * @return L'oggetto informazionePaziente se presente
     */
    InformazioniPaziente getInformationById(int id);

    /**
     * Funzione che permette di modoficare le informzioni di un paziente
     * @param info <code>InformazioniPaziente</code> nel quale verranno modificate le informazioni
     * @return <code>true</code> se l'operazione è andata a buon fine <code>false</code> altrimenti
     */
    boolean updateInformation(InformazioniPaziente info);

    /**
     * Funzione che permette di recuperare le informazioni di un determinato paziente
     * @param paziente <code>Paziente</code> che gli verrano recuperate le informazioni
     * @return informzioni del paziente richiesto
     */
    InformazioniPaziente getInformationByPatient(Paziente paziente);

}
