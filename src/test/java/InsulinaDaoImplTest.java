
import com.dashapp.diabeticsystem.DAO.implementations.InsulinaDaoImpl;
import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class InsulinaDaoImplTest {

    private TestDbManager testDb;
    private InsulinaDaoImpl dao;
    private Paziente paziente;

    @BeforeEach
    void setup() throws SQLException {
        testDb = new TestDbManager();
        dao = new InsulinaDaoImpl(testDb);



        // Inserisco un diabetologo (FK)
        testDb.updateQuery("""
                INSERT INTO diabetologo(nome, cognome, codice_fiscale, email, sesso)
                VALUES ('Mario', 'Rossi', 'RSSMRA80A01H501Z', 'mario.rossi@example.com', 'M')
                """);

        // Inserisco un paziente (FK)
        testDb.updateQuery("""
                INSERT INTO paziente(nome, cognome, codice_fiscale, data_nascita, email, sesso, id_diabetologo)
                VALUES ('Luca', 'Bianchi', 'BNCGLC90A01H501Z', '1990-01-01', 'luca@example.com', 'M', 1)
                """);
        Diabetologo diabetologo = new Diabetologo(1, "Mario", "Rossi", "RSSMRA80A01H501Z", "mario.rossi@example.com", "M");
        paziente = new Paziente(1, "Luca", "Bianchi", "luca@example.com", "BNCGLC90A01H501Z", LocalDate.parse("1990-01-01"),"M", diabetologo);
    }

    /**
     * Test inserimento insulina
     */
    @Test
    void testInsertInsulina() {
        Insulina i = new Insulina(
                120,
                PERIODO.PRIMA_DELLA_COLAZIONE,
                LocalDateTime.now(),
                "nessun sintomo",
                paziente
        );

        boolean ok = dao.insertInsulina(i);
        assertTrue(ok);

        Integer count = testDb.selectQuery(
                "SELECT COUNT(*) AS c FROM insulina",
                rs -> rs.next() ? rs.getInt("c") : 0
        );

        // controllo sia effettivamente una sola insulina
        assertEquals(1, count);
    }

    /**
     * Test per prendere livelli di insulina di un determinato paziente per data
     */
    @Test
    void testGetInsulinaByDateAndByPatients() {
        // Inserisco una riga
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 150, NOW(), 'prima della colazione', '')
                """);

        var list = dao.getInsulinaByDateAndByPatients(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                paziente
        );

        // controllo sia un livello solo e che sia uguale a 150
        assertEquals(1, list.size());
        assertEquals(150, list.get(0).getLivello_insulina());
    }

    /**
     * Test per prendere due livelli di insulina
     */
    @Test
    void testGetInsulina() {
        // Inserisco due righe
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 100, NOW(), 'prima della colazione', '')
                """);

        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 130, NOW(), 'dopo pranzo', '')
                """);

        var list = dao.getInsulina(paziente, 10, 0);

        // controllo che siano effettivamente due livelli registrati
        assertEquals(2, list.size());
    }

    /**
     * Test per conteggio livelli di insulina giornalieri
     */
    @Test
    void testCountDailyInsulinaByPatient() {
        // Inserisco due misurazioni oggi
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 110, NOW(), 'prima della colazione', '')
                """);

        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 140, NOW(), 'dopo pranzo', '')
                """);

        int count = dao.countDailyInsulinaByPatient(paziente);

        // controllo che siano effettivamente due livelli registati
        assertEquals(2, count);
    }

    /**
     * Test per conteggio livelli di insulina in un determinato momento della giornata
     */
    @Test
    void testCountDailyMomentOfDay() {
        // Inserisco una misurazione oggi PRIMA COLAZIONE
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 110, NOW(), 'prima della colazione', '')
                """);

        int count = dao.coundDailyMomentOfDay(PERIODO.PRIMA_DELLA_COLAZIONE, paziente);

        // controllo che sia effettivamente un livello registato
        assertEquals(1, count);
    }

    /**
     * Test per controllo livello notificato
     */
    @Test
    void testmarkAsNotified(){
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 110, NOW(), 'prima della colazione', '')
                """);

        dao.markAsNotified(1);

        var list = dao.getInsulinaByDateAndByPatients(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                paziente
        );

        // controllo se è stato effettivamente notificato
        assertTrue(list.get(0).isNotificata());
    }

    /**
     * Test per controllo di non notifica del livello di insulina
     */
    @Test
    void testgetNonNotifiedByDateAndPatient(){
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 110, NOW(), 'prima della colazione', '')
                """);

        var list = dao.getNonNotifiedByDateAndPatient(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                paziente
        );

        // controllo che effettivamente non è stato notificato
        assertEquals(1,list.size());
    }
}
