package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.InsulinaDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InsulinaDaoImpl implements InsulinaDao {


    @Override
    public ObservableList<Insulina> getInsulinaByDateAndByPatients(LocalDateTime inizio, LocalDateTime fine, Paziente paziente) {
        ObservableList<Insulina> list = FXCollections.observableArrayList();

        Main.getDbManager().selectQuery("SELECT * FROM insulina WHERE id_paziente = ? AND orario BETWEEN ? AND ?",
                rs -> {
                    while (rs.next()) {
                        list.add(
                                new Insulina(rs.getInt("valore_glicemia"), PERIODO.fromDescrizione(rs.getString("periodo")),rs.getTimestamp("orario").toLocalDateTime(),rs.getString("sintomi"))
                        );
                    }
                    return null;
                },
                paziente.getId_paziente(),inizio,fine
        );

        return list;
    }

    @Override
    public boolean insertInsulina(Insulina insulina) {
        return Main.getDbManager().updateQuery("INSERT INTO insulina(id_paziente,valore_glicemia,orario,periodo,sintomi) VALUES(?,?,?,?,?)",
                insulina.getPaziente().getId_paziente(),insulina.getLivello_insulina(),insulina.getOrario(),insulina.getPeriodo().toString(),insulina.getSintomo());
    }

    @Override
    public ObservableList<Insulina> getInsulina(Paziente paziente, Integer limit, int option) {
        if(limit == null) limit = Integer.MAX_VALUE;

        ObservableList<Insulina> list = FXCollections.observableArrayList();

        String baseQuery = "SELECT * FROM (SELECT * FROM insulina WHERE id_paziente = ? ORDER BY id_glicemia DESC LIMIT " + limit  + " ) AS sub";

        if(option != 0){
            baseQuery += " WHERE Date(orario) = " + "'" + LocalDate.now() + "'";
        }



        baseQuery += " ORDER BY orario ASC";

        Main.getDbManager().selectQuery(baseQuery,
                rs -> {
                    while (rs.next()) {
                        list.add(
                                new Insulina(rs.getInt("valore_glicemia"), PERIODO.fromDescrizione(rs.getString("periodo")),rs.getTimestamp("orario").toLocalDateTime(),rs.getString("sintomi"),paziente)
                        );
                    }
                    return null;
                },
                paziente.getId_paziente()
        );

        return list;
    }

    @Override
    public int countDailyInsulinaByPatient(Paziente paziente) {
        return getInsulina(paziente,null,1).size();

    }

    @Override
    public int coundDailyMomentOfDay(PERIODO periodo, Paziente paziente) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        String startTime = startOfDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        String endTime = endOfDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return Main.getDbManager().selectQuery("\n" +
                "SELECT COUNT(*) as c FROM insulina\n" +
                "WHERE orario BETWEEN ? AND ? AND periodo = ?AND id_paziente = ?;",
                rs -> {
                    if (rs.next()) {
                        return rs.getInt("c");
                    }
                    return 0;
                },startTime,endTime,periodo.toString(),paziente.getId_paziente());
    }

}
