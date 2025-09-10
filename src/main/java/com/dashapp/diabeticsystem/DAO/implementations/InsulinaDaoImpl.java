package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.InsulinaDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

public class InsulinaDaoImpl implements InsulinaDao {



    @Override
    public ObservableList<Insulina> getInsulinaByDateAndByPatients(LocalDateTime inizio, LocalDateTime fine, Paziente paziente) {
        ObservableList<Insulina> list = FXCollections.observableArrayList();

        Main.getDbManager().selectQuery("SELECT * FROM insulina WHERE id_paziente = ? AND orario BETWEEN ? AND ?",
                rs -> {
                    while (rs.next()) {
                        list.add(
                                new Insulina(rs.getInt("valore_glicemia"), PERIODO.fromDescrizione(rs.getString("periodo")),rs.getTimestamp("orario").toLocalDateTime())
                        );
                    }
                    return null;
                },
                paziente.getId_paziente(),inizio,fine
        );

        return list;
    }

}
