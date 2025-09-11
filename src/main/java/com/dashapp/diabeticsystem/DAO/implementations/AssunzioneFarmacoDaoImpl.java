package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.AssunzioneFarmacoDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.AssunzioneFarmaco;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssunzioneFarmacoDaoImpl implements AssunzioneFarmacoDao {

    @Override
    public boolean insertTakingDrug(AssunzioneFarmaco assunzioneFarmaco) {
        if(assunzioneFarmaco == null) return false;

        return Main.getDbManager().updateQuery("INSERT INTO assunzione_farmaco(id_paziente,id_farmaco,dosaggio_quantità,dosaggio_unità,data_assunzione,sintomi) " +
                "VALUES(?,?,?,?,?,?)",assunzioneFarmaco.getPaziente().getId_paziente(),assunzioneFarmaco.getFarmaco().getId_farmaco(),assunzioneFarmaco.getDosaggio_quantita(),assunzioneFarmaco.getDosaggio_unita(),assunzioneFarmaco.getData_assunzione(),assunzioneFarmaco.getSintomi());

    }

    @Override
    public double totalDailyDosageTakingDrug(Paziente paziente, Farmaco farmaco, LocalDateTime date) {
        if(paziente == null || date == null) return 0;

        LocalDateTime inizio = date.toLocalDate().atStartOfDay();
        LocalDateTime fine = date.toLocalDate().atTime(LocalTime.MAX);

        String query = "SELECT SUM(dosaggio_quantità) AS sum FROM assunzione_farmaco WHERE id_paziente = ? AND data_assunzione BETWEEN ? AND ? ";

        if(farmaco != null)
            query += "AND id_farmaco = "  + farmaco.getId_farmaco();

        return Main.getDbManager().selectQuery(query,
                rs -> {
                    if(rs.next()){
                        return rs.getDouble("sum");

                    }
                    return null;
                },paziente.getId_paziente(),inizio,fine);
    }
}
