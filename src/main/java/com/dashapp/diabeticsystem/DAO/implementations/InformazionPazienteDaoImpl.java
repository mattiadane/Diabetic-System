package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.InformazionePazienteDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.InformazioniPaziente;
import com.dashapp.diabeticsystem.models.Paziente;

public class InformazionPazienteDaoImpl implements InformazionePazienteDao {
    @Override
    public int insertInformation(InformazioniPaziente info) {
        return Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO informazione_paziente(fattori_rischio, commorbità, patologie_pregresse, patologie_in_concomitanza) VALUES (?, ?, ?, ?)",
                 info.getFattoriRischio(), info.getCommorbita(), info.getPatologiePreg(), info.getPatologieAtt()
        );
    }

    @Override
    public InformazioniPaziente getInformationById(int id) {
        return Main.getDbManager().selectQuery("SELECT * FROM informazione_paziente WHERE id_informazione = ?",
                rs -> {
                    if(rs.next()){
                        return  new InformazioniPaziente(
                                rs.getString("fattori_rischio"),rs.getString("commorbità"),
                                rs.getString("patologie_pregresse"),rs.getString("patologie_in_concomitanza"),
                                rs.getInt("id_informazione")
                        );
                    }
                    return null;
                }
                ,id);
    }

    @Override
    public boolean updateInformation(InformazioniPaziente info) {
        if(info == null) return false;

        return Main.getDbManager().updateQuery("UPDATE informazione_paziente SET fattori_rischio = ?, commorbità = ?, patologie_pregresse = ?, patologie_in_concomitanza = ? WHERE id_informazione = ?",
                info.getFattoriRischio(),info.getCommorbita(),info.getPatologiePreg(),info.getPatologieAtt(), info.getId_informazione());

    }

    @Override
    public InformazioniPaziente getInformationByPatient(Paziente paziente) {
        if(paziente == null) return null;

        return Main.getDbManager().selectQuery("SELECT p.id_paziente ,i.id_informazione,i.fattori_rischio,i.commorbità,i.patologie_pregresse,i.patologie_in_concomitanza FROM informazione_paziente i\n" +
                "INNER JOIN paziente p on i.id_informazione = p.id_informazione_paziente WHERE p.id_paziente = ?",
                rs -> {
                    if (rs.next()){
                        return new InformazioniPaziente(rs.getString("fattori_rischio"),rs.getString("commorbità"),rs.getString("patologie_pregresse"),rs.getString("patologie_in_concomitanza"),rs.getInt("id_informazione"));
                    }
                    return null;
                }
                ,paziente.getId_paziente());
    }


}

