package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public interface InsulinaDao {

    /**
     * Funzione che permette di prendere le glicemie di un paziente da una data ad un altra
     * @param inizio data inizio da dove controllare
     * @param fine data fino a dove controllare
     * @param paziente paziente che si vuole prendere le glicemie
     * @return lista di glicemia da una data ad un altra
     */
    ObservableList<Insulina> getInsulinaByDateAndByPatients(LocalDateTime inizio, LocalDateTime fine, Paziente paziente);
}
