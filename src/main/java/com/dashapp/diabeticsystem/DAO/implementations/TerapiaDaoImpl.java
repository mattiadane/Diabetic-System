package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.FarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfcaes.TerapiaDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TerapiaDaoImpl implements TerapiaDao {


    private final FarmacoDao farmacoDao = new FarmacoDaoImpl();

    @Override
    public boolean insertTherapy(Terapia terapia) {
        if(terapia == null ) return false;

        return Main.getDbManager().updateQuery("INSERT INTO terapia(id_paziente,id_diabetologo,id_farmaco,dosaggio_quantità" +
                        ",dosaggio_unità,quanto,periodicità,data_inizio_terapia,data_fine_terapia,descrizione) VALUES (?,?,?,?,?,?,?,?,?,?)",
                terapia.getPaziente().getId_paziente(),terapia.getDiabetologo().getId_diabetologo(),terapia.getFarmaco().getId_farmaco(),terapia.getDosaggio_quantita(),terapia.getDosaggio_unita(),
                terapia.getQuanto(),terapia.getPeriodicita().toString(),terapia.getData_inizio(),terapia.getData_fine(),terapia.getDescrizione()
        );
    }


    @Override
    public boolean removeTherapy(int id_terapia){
        return Main.getDbManager().updateQuery("DELETE FROM terapia WHERE id_terapia = ?",id_terapia);
    }

    @Override
    public boolean updateTherapy(Terapia terapia) {
        if(terapia == null) return false;

        return Main.getDbManager().updateQuery("""
                        UPDATE terapia SET
                        id_farmaco = ?,
                        dosaggio_quantità = ?,
                        dosaggio_unità = ?,
                        quanto = ?,
                        periodicità = ?,
                        data_inizio_terapia = ?,
                        data_fine_terapia = ?,
                        descrizione = ?
                        WHERE (id_terapia = ?)""",
                terapia.getFarmaco().getId_farmaco(), terapia.getDosaggio_quantita(), terapia.getDosaggio_unita(), terapia.getQuanto(), terapia.getPeriodicita().toString(), terapia.getData_inizio(), terapia.getData_fine(), terapia.getDescrizione(), terapia.getId_terapia()
        );
    }

    @Override
    public ObservableList<Terapia> getAllTherapyByPatient(Paziente paziente) {
        if(paziente == null) return null;
        ObservableList<Terapia> therapies = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery("SELECT f.nome,t.id_terapia,t.dosaggio_quantità,t.dosaggio_unità,t.quanto,t.periodicità,t.data_inizio_terapia,t.data_fine_terapia,t.descrizione,t.id_farmaco FROM terapia t\n" +
                        "INNER JOIN farmaco f ON t.id_farmaco = f.id_farmaco WHERE id_paziente = ?; ",
                rs -> {
                    while(rs.next()){
                        Farmaco f = farmacoDao.getDrugById(rs.getInt("t.id_farmaco"));
                        therapies.add(
                                new Terapia(
                                        rs.getInt("t.quanto"), PERIODICITA.valueOf(rs.getString("t.periodicità").toUpperCase()),
                                        rs.getDouble("t.dosaggio_quantità"),rs.getString("t.dosaggio_unità"),rs.getDate("t.data_inizio_terapia").toLocalDate()
                                        ,rs.getDate("t.data_fine_terapia").toLocalDate(),rs.getString("t.descrizione"),f,rs.getInt("t.id_terapia")

                                )
                        );
                    }
                    return null;
                }
                ,paziente.getId_paziente());
        return therapies;

    }

}
