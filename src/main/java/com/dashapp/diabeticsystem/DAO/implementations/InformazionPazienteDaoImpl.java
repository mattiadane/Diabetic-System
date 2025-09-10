package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.InformazionePazienteDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.InformazioniPaziente;

public class InformazionPazienteDaoImpl implements InformazionePazienteDao {
    @Override
    public boolean insertInformation(int id_paziente, InformazioniPaziente info) {
        return Main.getDbManager().updateQuery(
                "INSERT INTO informazione_paziente(id_paziente, fattori_rischio, commorbità, patologie_pregresse, patologie_in_concomitanza) VALUES (?, ?, ?, ?, ?)",
                id_paziente, info.getFattoriRischio(), info.getCommorbita(), info.getPatologiePreg(), info.getPatologieAtt()
        );
    }
}
