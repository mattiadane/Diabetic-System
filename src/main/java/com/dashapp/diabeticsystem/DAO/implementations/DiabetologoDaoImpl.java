package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.DiabetologoDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Diabetologo;

import java.util.concurrent.atomic.AtomicReference;

public class DiabetologoDaoImpl  implements DiabetologoDao {

    @Override
    public Diabetologo getDiabetologoById(int id_diabetologo) {
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
}
