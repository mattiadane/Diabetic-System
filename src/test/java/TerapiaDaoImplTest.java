import com.dashapp.diabeticsystem.DAO.implementations.TerapiaDaoImpl;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TerapiaDaoImplTest {

    private TestDbManager db;
    private TerapiaDaoImpl terapiaDao;

    private Paziente paziente;
    private Diabetologo diabetologo;
    private Farmaco farmaco;

    @BeforeEach
    void setup() throws SQLException {
        db = new TestDbManager();
        terapiaDao = new TerapiaDaoImpl(db);

        // Inseriamo diabetologo
        int idDiabetologo = db.insertAndGetGeneratedId(
                "INSERT INTO diabetologo(nome, cognome, codice_fiscale, email, sesso) VALUES (?,?,?,?,?)",
                "Mario", "Rossi", "RSSMRA80A01H501Z", "mario@example.com", "M"
        );
        diabetologo = new Diabetologo(idDiabetologo, "Mario", "Rossi", "RSSMRA80A01H501Z", "mario@example.com", "M");

        // Inseriamo paziente
        int idPaziente = db.insertAndGetGeneratedId(
                "INSERT INTO paziente(nome, cognome, codice_fiscale, data_nascita, email, sesso, id_diabetologo) VALUES (?,?,?,?,?,?,?)",
                "Luca", "Bianchi", "BNCGLC90A01H501Z", java.sql.Date.valueOf("1990-01-01"),
                "luca@example.com", "M", idDiabetologo
        );
        paziente = new Paziente(idPaziente, "Luca", "Bianchi", "luca@example.com",
              "BNCGLC90A01H501Z"  , LocalDate.of(1990,1,1), "M", diabetologo);

        // Farmaco finto
        farmaco = new Farmaco(1, "Metformina", "500mg");
    }

    /**
     * Test di inserimento di una nuova terapia
     */
    @Test
    void testInsertTherapy() {
        Terapia terapia = new Terapia(
                2,
                PERIODICITA.GIORNO,
                500,
                "mg",
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                "Terapia iniziale",
                farmaco,
                diabetologo,
                paziente
        );

        boolean result = terapiaDao.insertTherapy(terapia);

        // controllo che la terapia è stata effettivamnte inserita
        assertTrue(result);

        Integer count = db.selectQuery(
                "SELECT COUNT(*) FROM terapia WHERE id_paziente = ?",
                rs -> rs.next() ? rs.getInt(1) : 0,
                paziente.getId_paziente()
        );

        // controllo che sia presente nel db
        assertEquals(1, count);
    }

    /**
     * Test per prendere una terapia dato farmaco e paziente
     */
    @Test
    void testGetTherapyByFarmacoIdAndPatient() {
        // Inseriamo una terapia manualmente
        db.insertAndGetGeneratedId(
                "INSERT INTO terapia(id_paziente,id_diabetologo,id_farmaco,dosaggio_quantità,dosaggio_unità,quanto,periodicità,data_inizio_terapia,data_fine_terapia,descrizione) VALUES (?,?,?,?,?,?,?,?,?,?)",
                paziente.getId_paziente(), diabetologo.getId_diabetologo(), farmaco.getId_farmaco(),
                500, "mg", 2, "GIORNALIERA",
                java.sql.Date.valueOf(LocalDate.now()),
                java.sql.Date.valueOf(LocalDate.now().plusDays(30)),
                "Test terapia"
        );

        Terapia terapia = terapiaDao.getTherapyByFarmacoIdAndPatient(paziente, farmaco);

        // controllo che tutto sia valido
        assertNotNull(terapia);
        assertEquals(2, terapia.getQuanto());
        assertEquals("mg", terapia.getDosaggio_unita());
    }


    /**
     * Test rimozione di una terapia
     */
    @Test
    void testRemoveTherapy() {
        int idTerapia = db.insertAndGetGeneratedId(
                "INSERT INTO terapia(id_paziente,id_diabetologo,id_farmaco,dosaggio_quantità,dosaggio_unità,quanto,periodicità,data_inizio_terapia,data_fine_terapia,descrizione) VALUES (?,?,?,?,?,?,?,?,?,?)",
                paziente.getId_paziente(), diabetologo.getId_diabetologo(), farmaco.getId_farmaco(),
                500, "mg", 2, "GIORNALIERA",
                java.sql.Date.valueOf(LocalDate.now()),
                java.sql.Date.valueOf(LocalDate.now().plusDays(30)),
                "Test terapia"
        );

        boolean removed = terapiaDao.removeTherapy(idTerapia);

        // controllo che è stata rimossa
        assertTrue(removed);

        Integer count = db.selectQuery(
                "SELECT COUNT(*) FROM terapia WHERE id_terapia = ?",
                rs -> rs.next() ? rs.getInt(1) : 0,
                idTerapia
        );

        // controllo che non sia presente a db
        assertEquals(0, count);
    }
}
