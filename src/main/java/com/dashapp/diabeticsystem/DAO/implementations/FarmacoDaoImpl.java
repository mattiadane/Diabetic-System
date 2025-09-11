package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.FarmacoDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.atomic.AtomicReference;

public class FarmacoDaoImpl implements FarmacoDao {


    @Override
    public Farmaco getDrugByName(String name) {
        AtomicReference<Farmaco> farmaco = new AtomicReference<>();
        Main.getDbManager().selectQuery("SELECT * FROM farmaco WHERE nome = ?",
                rs -> {
                    if(rs.next()){
                        farmaco.set(new Farmaco(rs.getInt("id_farmaco"), rs.getString("nome"), rs.getString("descrizione")));
                    }
                    return null;
                }
                ,name);
        return farmaco.get();
    }

    @Override
    public ObservableList<Farmaco> getAllDrugs() {
        ObservableList<Farmaco> drugs = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery("SELECT * FROM farmaco",
                rs -> {
                    while (rs.next()) {
                        drugs.add(new Farmaco(rs.getInt("id_farmaco"),rs.getString("nome"),rs.getString("descrizione")));
                    }
                    return null;
                });
        return drugs;
    }

    @Override
    public Farmaco getDrugById(int id_farmaco) {
        AtomicReference<Farmaco> farmaco = new AtomicReference<>();
        Main.getDbManager().selectQuery("SELECT * FROM farmaco WHERE id_farmaco = ?",
                rs -> {
                    if(rs.next()){
                        farmaco.set(new Farmaco(rs.getInt("id_farmaco"), rs.getString("nome"), rs.getString("descrizione")));
                    }
                    return null;
                }
                ,id_farmaco);
        return farmaco.get();
    }

    @Override
    public ObservableList<Farmaco> getAllDrugsByPaziente(Paziente paziente) {
        ObservableList<Farmaco> drugs = FXCollections.observableArrayList();
         Main.getDbManager().selectQuery("SELECT f.id_farmaco,f.nome,f.descrizione FROM farmaco f\n" +
                "INNER JOIN terapia t ON f.id_farmaco = t.id_farmaco WHERE id_paziente = ?",
                 rs -> {
                    while(rs.next()){
                        drugs.add(new Farmaco(
                                rs.getInt("f.id_farmaco"),rs.getString("f.nome"),rs.getString("f.descrizione")
                        ));
                    }
                    return null;
                 }
                 ,paziente.getId_paziente());
         return   drugs;
    }
}
