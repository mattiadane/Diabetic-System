package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.PazienteDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Paziente;

public class PazienteDaoImpl implements PazienteDao {

    @Override
    public int insertPatient(Paziente paziente) {
        return Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO paziente(nome,cognome,email,codice_fiscale,data_nascita,id_diabetologo) VALUES (?,?,?,?,?,?)",
                paziente.getNome(),paziente.getCognome(),paziente.getEmail(),paziente.getCodice_fiscale(),paziente.getDataNascita(),paziente.getDiabetologo().getId_diabetologo()
        );

    }

    @Override
    public boolean updatePatient(Paziente paziente) {
        return false;
    }

    @Override
    public boolean deletePatient(Paziente paziente) {
        return false;
    }

    @Override
    public Paziente loadPazienteById(int id) {
        return null;
    }
}
