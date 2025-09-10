package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.DiabetologoDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Diabetologo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.concurrent.atomic.AtomicReference;

public class DiabetologoDaoImpl  implements DiabetologoDao {

    @Override
    public Diabetologo getDiabetologistById(int id_diabetologo) {
        AtomicReference<Diabetologo> diabetologo = new AtomicReference<>();
        Main.getDbManager().selectQuery("SELECT * FROM diabetologo WHERE id_diabetologo = ?",
                rs -> {
                    if (rs.next()){
                        diabetologo.set(new Diabetologo(
                                rs.getInt("id_diabetologo"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"),
                                rs.getString("codice_fiscale"), rs.getString("sesso")
                        ));
                    }
                    return null;
                },id_diabetologo);
        return diabetologo.get();
    }


    @Override
    public int insertDibetologist(Diabetologo diabetologo) {
        return Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO diabetologo(nome,cognome,codice_fiscale,email,sesso) VALUES (?,?,?,?,?)",
                diabetologo.getNome(),diabetologo.getCognome(),diabetologo.getCodice_fiscale(),diabetologo.getEmail(),diabetologo.getSesso()
        );

    }

    @Override
    public ObservableList<Diabetologo> getAllDiabetologists() {
        ObservableList<Diabetologo> diabetologists = FXCollections.observableArrayList();

        Main.getDbManager().selectQuery("SELECT id_diabetologo,nome,cognome,codice_fiscale,email,sesso FROM diabetologo",
                rs -> {
                    while (rs.next()) {
                        diabetologists.add(
                                new Diabetologo(rs.getInt("id_diabetologo"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getString("sesso"))
                        );
                    }
                    return null;
                }

        );

        return diabetologists;
    }

    @Override
    public boolean removeDiabetologist(int id_diabetologo) {
        return Main.getDbManager().updateQuery("DELETE FROM diabetologo WHERE id_diabetologo = ?",id_diabetologo);
    }


}
