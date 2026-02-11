package com.dashapp.diabeticsystem.DAO.implementations;

import com.dashapp.diabeticsystem.DAO.interfaces.FarmacoDao;
import com.dashapp.diabeticsystem.DAO.interfaces.TerapiaDao;
import com.dashapp.diabeticsystem.Main;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.DbManager;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TerapiaDaoImpl implements TerapiaDao {

    private DbManager db ;
    private final FarmacoDao farmacoDao = new FarmacoDaoImpl();


    /**
     *
     * Costruttore per i test
     * @param db DbManager da utilizzare
     */
    public TerapiaDaoImpl(DbManager db) { this.db = db; }

    /**
     * Costruttore per la produzione
     */
    public TerapiaDaoImpl(){
        this(Main.getDbManager());
    }



    @Override
    public boolean insertTherapy(Terapia terapia) {
        if(terapia == null ) return false;

        return db.updateQuery("INSERT INTO terapia(id_paziente,id_diabetologo,id_farmaco,dosaggio_quantità" +
                        ",dosaggio_unità,quanto,periodicità,data_inizio_terapia,data_fine_terapia,descrizione) VALUES (?,?,?,?,?,?,?,?,?,?)",
                terapia.getPaziente().getId_paziente(),terapia.getDiabetologo().getId_diabetologo(),terapia.getFarmaco().getId_farmaco(),terapia.getDosaggio_quantita(),terapia.getDosaggio_unita(),
                terapia.getQuanto(),terapia.getPeriodicita().toString(),terapia.getData_inizio(),terapia.getData_fine(),terapia.getDescrizione()
        );
    }


    @Override
    public boolean removeTherapy(int id_terapia){
        return db.updateQuery("DELETE FROM terapia WHERE id_terapia = ?",id_terapia);
    }

    @Override
    public boolean updateTherapy(Terapia terapia) {
        if(terapia == null) return false;

        return db.updateQuery("""
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
    public ObservableList<Terapia> getAllTherapyByPatientByDate(Paziente paziente, LocalDate date) {
        if(paziente == null) return null;


        ObservableList<Terapia> therapies = FXCollections.observableArrayList();


        String query =
                "SELECT f.nome, t.id_terapia, t.dosaggio_quantità, t.dosaggio_unità, t.quanto, t.periodicità, " +
                        "t.data_inizio_terapia, t.data_fine_terapia, t.descrizione, t.id_farmaco " +
                        "FROM terapia t " +
                        "INNER JOIN farmaco f ON t.id_farmaco = f.id_farmaco " +
                        "WHERE id_paziente = ? ";





        if(date != null) {

            query += " AND t.data_inizio_terapia <= ?  AND t.data_fine_terapia >= ?";
            db.selectQuery(query,
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
                    ,paziente.getId_paziente(),date,date);
            return therapies;


        } else {
            db.selectQuery(query,
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

    @Override
    public Terapia getTherapyByFarmacoIdAndPatient(Paziente paziente, Farmaco farmaco) {

        if(paziente == null || farmaco == null) return null;

        return db.selectQuery("SELECT * FROM terapia WHERE id_farmaco = ? AND id_paziente = ?",
                rs -> {
                    if(rs.next()){
                        return new Terapia(rs.getInt("quanto"),PERIODICITA.fromDescrizione(rs.getString("periodicità")),rs.getDouble("dosaggio_quantità"),
                                rs.getString("dosaggio_unità"),rs.getDate("data_inizio_terapia").toLocalDate(),rs.getDate("data_fine_terapia").toLocalDate(),
                                rs.getString("descrizione"),farmaco,rs.getInt("id_terapia")
                                );
                    }
                    return null;
                }
                ,farmaco.getId_farmaco(),paziente.getId_paziente());

    }

}
