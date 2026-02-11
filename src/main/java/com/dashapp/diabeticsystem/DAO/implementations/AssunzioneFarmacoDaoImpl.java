package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.AssunzioneFarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfaces.PazienteDao;
import com.dashapp.diabeticsystem.DAO.interfaces.TerapiaDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssunzioneFarmacoDaoImpl implements AssunzioneFarmacoDao {
    PazienteDao pazienteDao = new PazienteDaoImpl();
    TerapiaDao terapiaDao = new TerapiaDaoImpl();

    @Override
    public boolean insertTakingDrug(AssunzioneFarmaco assunzioneFarmaco) {
        if (assunzioneFarmaco == null) return false;

        return Main.getDbManager().updateQuery("INSERT INTO assunzione_farmaco(id_paziente,id_farmaco,dosaggio_quantità,dosaggio_unità,data_assunzione) " +
                "VALUES(?,?,?,?,?)", assunzioneFarmaco.getPaziente().getId_paziente(), assunzioneFarmaco.getFarmaco().getId_farmaco(), assunzioneFarmaco.getDosaggio_quantita(), assunzioneFarmaco.getDosaggio_unita(), assunzioneFarmaco.getData_assunzione());

    }

    @Override
    public double totalDailyDosageTakingDrug(Paziente paziente, Farmaco farmaco, LocalDateTime date) {
        if (paziente == null || date == null) return 0;

        LocalDateTime inizio = date.toLocalDate().atStartOfDay();
        LocalDateTime fine = date.toLocalDate().atTime(LocalTime.MAX);

        String query = "SELECT SUM(dosaggio_quantità) AS sum FROM assunzione_farmaco WHERE id_paziente = ? AND data_assunzione BETWEEN ? AND ? ";

        if (farmaco != null)
            query += "AND id_farmaco = " + farmaco.getId_farmaco();

        return Main.getDbManager().selectQuery(query,
                rs -> {
                    if (rs.next()) {
                        return rs.getDouble("sum");

                    }
                    return 0.0;
                }, paziente.getId_paziente(), inizio, fine);
    }

    public Map<Paziente, List<Terapia>> listPatientNoTakingDrugForThreeDaysConsecutiv(Diabetologo diabetologo) {
        LocalDateTime startDate = LocalDate.now().minusDays(2).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atTime(23, 59, 59);


        Map<Paziente, List<Terapia>> mappa = new HashMap<>();

        for (Paziente paziente : pazienteDao.getAllPatientsByDiabetologist(diabetologo.getId_diabetologo())) {
            List<Terapia> terapie = terapiaDao.getAllTherapyByPatientByDate(paziente, null)
                    .stream().
                    filter(t -> t.getData_inizio().isBefore(LocalDate.now().minusDays(2))).
                    toList();

            List<Terapia> terapia_non_assunte = new ArrayList<>();


            for (Terapia terapia : terapie) {
                int count = Main.getDbManager().selectQuery("""
                                SELECT COUNT(*) AS c FROM assunzione_farmaco AS s  WHERE id_paziente = ? AND id_farmaco = ? AND data_assunzione BETWEEN ? AND ? ;
                                
                                """,
                        rs1 -> {
                            if (rs1.next()) {
                                return rs1.getInt("c");
                            }
                            return 0;
                        }, paziente.getId_paziente(), terapia.getFarmaco().getId_farmaco(), startDate, endDate
                );

                if (count == 0) {
                    terapia_non_assunte.add(terapia);
                }
            }

            if (!terapia_non_assunte.isEmpty()) {
                mappa.put(paziente, terapia_non_assunte);
            }

        }

        return mappa;
    }


}

