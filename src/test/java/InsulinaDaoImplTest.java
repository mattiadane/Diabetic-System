
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
    private Diabetologo diabetologo;

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
        diabetologo = new Diabetologo(1,"Mario","Rossi","RSSMRA80A01H501Z","mario.rossi@example.com","M");
        paziente = new Paziente(1, "Luca", "Bianchi", "luca@example.com", "BNCGLC90A01H501Z", LocalDate.parse("1990-01-01"),"M",diabetologo);
    }

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

        assertEquals(1, count);
    }

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

        assertEquals(1, list.size());
        assertEquals(150, list.get(0).getLivello_insulina());
    }

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

        assertEquals(2, list.size());
    }

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

        assertEquals(2, count);
    }

    @Test
    void testCountDailyMomentOfDay() {
        // Inserisco una misurazione oggi PRIMA COLAZIONE
        testDb.updateQuery("""
                INSERT INTO insulina(id_paziente, valore_glicemia, orario, periodo, sintomi)
                VALUES (1, 110, NOW(), 'prima della colazione', 'ok')
                """);

        int count = dao.coundDailyMomentOfDay(PERIODO.PRIMA_DELLA_COLAZIONE, paziente);

        assertEquals(1, count);
    }
}
