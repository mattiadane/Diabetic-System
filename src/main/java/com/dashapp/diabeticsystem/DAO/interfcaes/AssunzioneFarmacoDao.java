package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.AssunzioneFarmaco;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;

import java.time.LocalDateTime;

public interface AssunzioneFarmacoDao {


    /**
     * Funzione che permette di inserire nel database l'assunzione di un farmaco di un determinato paziente
     * @param assunzioneFarmaco da inserire nel database
     * @return <code>true</code> se l'iserimento è andato a buon fine ,<code>false</code> altrimenti
     */
    boolean insertTakingDrug(AssunzioneFarmaco assunzioneFarmaco);


    /**
     * Funzione che permette di recuperare la somma giornaliera di farmaco che il paziente ha assunto
     * @param farmaco
     * @param paziente
     * @param date la data dove guardare la somma giornaliera
     * @return la somma che ha ragiunto durate il giorno un paziente di un determinato farmaco
     */
    double totalDailyDosageTakingDrug(Paziente paziente, Farmaco farmaco, LocalDateTime date);
}
