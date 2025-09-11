package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.AssunzioneFarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfaces.PazienteDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.AssunzioneFarmaco;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AssunzioneFarmacoDaoImpl implements AssunzioneFarmacoDao {
    PazienteDao pazienteDao = new PazienteDaoImpl();

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

    public ObservableList<Paziente> listPatientNoTakingDrugForThreeDaysConsecutiv(Diabetologo diabetologo){
        LocalDateTime startDate = LocalDate.now().minusDays(2).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().atTime(23, 59, 59);


        ObservableList<Paziente> list = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery(
                """
                        SELECT COUNT(af.id_assunzione) AS count,p.id_paziente FROM paziente p
                        LEFT JOIN assunzione_farmaco af ON p.id_paziente = af.id_paziente AND af.data_assunzione BETWEEN ? AND ?
                        WHERE p.id_diabetologo = ?\s
                        GROUP BY p.id_paziente""",
                rs -> {
                    while(rs.next()){

                        if(rs.getInt("count") == 0){
                            list.add( pazienteDao.getPatientById(rs.getInt("p.id_paziente")));
                        }
                    }
                    return null;
                }
                ,startDate,endDate,diabetologo.getId_diabetologo()
        );
        return list;
    }
}
