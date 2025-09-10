package com.dashapp.diabeticsystem.DAO.interfcaes;

import com.dashapp.diabeticsystem.models.Diabetologo;

public interface DiabetologoDao {


    /**
     * Funzione che permette di restituire il Dibaetologo attraverso l'id_diabetolgo
     * @param id_diabetologo
     * @return l'ogetto diabetologo se presente altrimenti null
     */
    Diabetologo getDiabetologoById(int id_diabetologo);
}
