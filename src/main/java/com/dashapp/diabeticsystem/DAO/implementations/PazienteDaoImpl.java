package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfcaes.PazienteDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.models.Paziente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PazienteDaoImpl implements PazienteDao {

    @Override
    public int insertPatient(Paziente paziente) {
        return Main.getDbManager().insertAndGetGeneratedId(
                "INSERT INTO paziente(nome,cognome,email,codice_fiscale,data_nascita,id_diabetologo) VALUES (?,?,?,?,?,?)",
                paziente.getNome(),paziente.getCognome(),paziente.getEmail(),paziente.getCodice_fiscale(),paziente.getDataNascita(),paziente.getDiabetologo().getId_diabetologo()
        );

    }

    @Override
    public boolean updatePatient(Paziente paziente) {
        return false;
    }

    @Override
    public boolean deletePatient(int id_paziente) {
        return Main.getDbManager().updateQuery("DELETE FROM paziente WHERE id_paziente = ?",id_paziente);
    }

    @Override
    public Paziente loadPazienteById(int id) {
        return null;
    }

    @Override
    public ObservableList<Paziente> getAllPatientsByDiabetologist(int id_diabetologo) {
        ObservableList<Paziente> pazienti = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery("SELECT id_paziente,nome,cognome,codice_fiscale,data_nascita,email,sesso FROM paziente WHERE id_diabetologo = ?",
                rs -> {
                    while (rs.next()){

                        pazienti.add(
                                new Paziente(rs.getInt("id_paziente"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate(),rs.getString("sesso"))
                        );
                    }
                    return null;
                } ,id_diabetologo);
        return pazienti;
    }


    @Override
    public ObservableList<Paziente> getPazientiResearch(String search) {
        ObservableList<Paziente> pazientiResearch = FXCollections.observableArrayList();
        Main.getDbManager().selectQuery("SELECT id_paziente,nome,cognome,codice_fiscale,data_nascita,email,sesso FROM paziente WHERE codice_fiscale LIKE ? ",
                rs -> {
                    while (rs.next()){
                        pazientiResearch.add(
                                new Paziente(rs.getInt("id_paziente"),rs.getString("nome"),rs.getString("cognome"),rs.getString("email"),rs.getString("codice_fiscale"),rs.getDate("data_nascita").toLocalDate(),rs.getString("sesso"))

                        );
                    }
                    return null;
                },search + "%");
        return pazientiResearch;
    }
}
