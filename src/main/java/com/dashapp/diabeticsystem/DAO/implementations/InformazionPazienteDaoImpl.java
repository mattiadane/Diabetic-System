package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.InformazionePazienteDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.*;

import java.util.ArrayList;
import java.util.List;

public class InformazionPazienteDaoImpl implements InformazionePazienteDao {

    @Override
    public int insertInformation(InformazioniPaziente info) {
        if(info == null) return 0;
        int id = 0;


        if(info.getCommorbita() != null) {
            id += Main.getDbManager().insertAndGetGeneratedId("INSERT INTO comorbita(nome,id_paziente) VALUES (?,?)",info.getCommorbita().getNome(),info.getPaziente().getId_paziente());

        }

        if(info.getPatologiePreg() != null) {
            id += Main.getDbManager().insertAndGetGeneratedId("INSERT INTO patologia_pregressa(nome,id_paziente) VALUES (?,?)",info.getPatologiePreg().getNome(),info.getPaziente().getId_paziente());

        }

        if (info.getFattoriRischio() != null) {
            id += Main.getDbManager().insertAndGetGeneratedId("INSERT INTO fattore_rischio(nome,id_paziente) VALUES (?,?)",info.getFattoriRischio().getNome(),info.getPaziente().getId_paziente());

        }


        return id;

    }

    @Override
    public List<Comorbità> getComorbita(Paziente paziente) {
        List<Comorbità> cs =  new ArrayList<>();
        Main.getDbManager().selectQuery("SELECT nome FROM comorbita WHERE id_paziente = ?",
                rs -> {
                    while (rs.next()) {
                        cs.add(
                                new Comorbità(rs.getString("nome"))
                        );

                    }
                    return null;
                }
                ,paziente.getId_paziente());
        return cs;
    }

    @Override
    public List<FattoreRischio> getFattoriRischio(Paziente paziente) {
        List<FattoreRischio> fr =  new ArrayList<>();
        Main.getDbManager().selectQuery("SELECT nome FROM fattore_rischio WHERE id_paziente = ?",
                rs -> {
                    while (rs.next()) {
                        fr.add(
                                new FattoreRischio(rs.getString("nome"))
                        );

                    }
                    return null;
                }
                ,paziente.getId_paziente());
        return fr;
    }

    @Override
    public List<PatologiaPregressa> getPatologiePregresse(Paziente paziente) {
        List<PatologiaPregressa> pg =  new ArrayList<>();
        Main.getDbManager().selectQuery("SELECT nome FROM patologia_pregressa WHERE id_paziente = ?",
                rs -> {
                    while (rs.next()) {
                        pg.add(
                                new PatologiaPregressa(rs.getString("nome"))
                        );

                    }
                    return null;
                }
                ,paziente.getId_paziente());
        return pg;
    }


}

