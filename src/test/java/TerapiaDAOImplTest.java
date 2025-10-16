import com.dashapp.diabeticsystem.DAO.implementations.TerapiaDaoImpl;
import com.dashapp.diabeticsystem.enums.PERIODICITA;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Farmaco;
import com.dashapp.diabeticsystem.models.Paziente;
import com.dashapp.diabeticsystem.models.Terapia;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test di integrazione per TerapiaDaoImpl.
 * Questi test richiedono una connessione al database attiva.
 * Assicurarsi che il database sia configurato correttamente prima di eseguire i test.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TerapiaDaoImplTest {

    private static TerapiaDaoImpl terapiaDao;
    private static Paziente pazienteTest;
    private static Diabetologo diabetologoTest;
    private static Farmaco farmacoTest;

    @BeforeAll
    static void setUpAll() {
        terapiaDao = new TerapiaDaoImpl();

        // Setup diabetologo di test (deve esistere nel DB)
        diabetologoTest = new Diabetologo(
                1, // ID esistente nel DB
                "Mario",
                "Rossi",
                "mario.rossi@test.com",
                "RSSMRA70A01H501Z",
                "M"
        );

        // Setup paziente di test (deve esistere nel DB)
        pazienteTest = new Paziente(
                1, // ID esistente nel DB
                "Luigi",
                "Verdi",
                "luigi.verdi@test.com",
                "VRDLGU80A01H501W",
                LocalDate.of(1980, 1, 1),
                "M"
        );

        // Setup farmaco di test (deve esistere nel DB)
        // Nota: Farmaco ha campi final, quindi non possiamo usare setter
        farmacoTest = new Farmaco(
                1, // ID esistente nel DB
                "Insulina Glargine",
                "Insulina ad azione prolungata"
        );
    }

    @Test
    @Order(1)
    @DisplayName("Test inserimento terapia valida con tutti i campi")
    void testInsertTherapy_Success() {
        Terapia terapia = new Terapia(
                2, // quanto
                PERIODICITA.GIORNO,
                10.0, // dosaggio quantità
                "UI", // dosaggio unità
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                "Terapia insulinica basale",
                farmacoTest,
                diabetologoTest,
                pazienteTest
        );

        boolean result = terapiaDao.insertTherapy(terapia);
        assertTrue(result, "L'inserimento della terapia dovrebbe avere successo");

        // Verifica che sia stata effettivamente inserita
        Terapia recuperata = terapiaDao.getTherapyByFarmacoIdAndPatient(pazienteTest, farmacoTest);
        assertNotNull(recuperata, "La terapia inserita dovrebbe essere recuperabile");
    }

    @Test
    @Order(2)
    @DisplayName("Test inserimento terapia null")
    void testInsertTherapy_NullTerapia() {
        boolean result = terapiaDao.insertTherapy(null);
        assertFalse(result, "L'inserimento di una terapia null dovrebbe fallire");
    }

    @Test
    @Order(3)
    @DisplayName("Test recupero tutte le terapie di un paziente")
    void testGetAllTherapyByPatient_Success() {
        // Prima inseriamo una terapia
        Terapia terapia = new Terapia(
                3,
                PERIODICITA.GIORNO,
                5.0,
                "mg",
                LocalDate.now(),
                LocalDate.now().plusMonths(3),
                "Terapia settimanale di test",
                farmacoTest,
                diabetologoTest,
                pazienteTest
        );
        terapiaDao.insertTherapy(terapia);

        ObservableList<Terapia> terapie = terapiaDao.getAllTherapyByPatient(pazienteTest);

        assertNotNull(terapie, "La lista delle terapie non dovrebbe essere null");
        assertFalse(terapie.isEmpty(), "La lista delle terapie non dovrebbe essere vuota");

        // Verifica i campi della terapia recuperata
        Terapia primaTerap = terapie.get(0);
        assertNotNull(primaTerap.getFarmaco(), "Il farmaco non dovrebbe essere null");
        assertTrue(primaTerap.getQuanto() > 0, "Il campo 'quanto' dovrebbe essere positivo");
        assertNotNull(primaTerap.getPeriodicita(), "La periodicità non dovrebbe essere null");
        assertTrue(primaTerap.getDosaggio_quantita() > 0, "Il dosaggio dovrebbe essere positivo");
        assertNotNull(primaTerap.getDosaggio_unita(), "L'unità di dosaggio non dovrebbe essere null");
        assertNotNull(primaTerap.getData_inizio(), "La data inizio non dovrebbe essere null");
        assertNotNull(primaTerap.getData_fine(), "La data fine non dovrebbe essere null");
        assertTrue(primaTerap.getId_terapia() > 0, "L'ID terapia dovrebbe essere positivo");
    }

    @Test
    @Order(4)
    @DisplayName("Test recupero terapie con paziente null")
    void testGetAllTherapyByPatient_NullPaziente() {
        ObservableList<Terapia> terapie = terapiaDao.getAllTherapyByPatient(null);
        assertNull(terapie, "Il risultato dovrebbe essere null per paziente null");
    }

    @Test
    @Order(5)
    @DisplayName("Test recupero terapia specifica per farmaco e paziente")
    void testGetTherapyByFarmacoIdAndPatient_Success() {
        // Prima inseriamo una terapia specifica
        Terapia terapia = new Terapia(
                2,
                PERIODICITA.GIORNO,
                15.5,
                "ml",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                "Terapia specifica per test recupero",
                farmacoTest,
                diabetologoTest,
                pazienteTest
        );
        terapiaDao.insertTherapy(terapia);

        Terapia recuperata = terapiaDao.getTherapyByFarmacoIdAndPatient(pazienteTest, farmacoTest);

        assertNotNull(recuperata, "La terapia dovrebbe essere recuperata");
        assertEquals(farmacoTest.getId_farmaco(),
                recuperata.getFarmaco().getId_farmaco(),
                "L'ID del farmaco dovrebbe corrispondere");
        assertEquals(farmacoTest.getNome(),
                recuperata.getFarmaco().getNome(),
                "Il nome del farmaco dovrebbe corrispondere");
        assertEquals(2, recuperata.getQuanto(),
                "La quantità dovrebbe essere 2");
        assertEquals(PERIODICITA.GIORNO, recuperata.getPeriodicita(),
                "La periodicità dovrebbe essere GIORNALIERA");
        assertEquals(15.5, recuperata.getDosaggio_quantita(), 0.01,
                "Il dosaggio dovrebbe essere 15.5");
        assertEquals("ml", recuperata.getDosaggio_unita(),
                "L'unità di dosaggio dovrebbe essere ml");
        assertEquals("Terapia specifica per test recupero", recuperata.getDescrizione(),
                "La descrizione dovrebbe corrispondere");
        assertTrue(recuperata.getId_terapia() > 0,
                "L'ID terapia dovrebbe essere stato assegnato dal DB");

        // Test dei metodi di Terapia
        assertEquals("15.5ml", recuperata.getDosaggio(),
                "getDosaggio() dovrebbe concatenare quantità e unità");
        assertTrue(recuperata.getAssunzioni().contains("2"),
                "getAssunzioni() dovrebbe contenere il numero di volte");
        assertTrue(recuperata.getPeriodo().contains("2024"),
                "getPeriodo() dovrebbe contenere l'anno");
        assertNotNull(recuperata.toStringForList(),
                "toStringForList() non dovrebbe essere null");
    }

    @Test
    @Order(6)
    @DisplayName("Test recupero terapia con paziente null")
    void testGetTherapyByFarmacoIdAndPatient_NullPaziente() {
        Terapia result = terapiaDao.getTherapyByFarmacoIdAndPatient(null, farmacoTest);
        assertNull(result, "Il risultato dovrebbe essere null per paziente null");
    }

    @Test
    @Order(7)
    @DisplayName("Test recupero terapia con farmaco null")
    void testGetTherapyByFarmacoIdAndPatient_NullFarmaco() {
        Terapia result = terapiaDao.getTherapyByFarmacoIdAndPatient(pazienteTest, null);
        assertNull(result, "Il risultato dovrebbe essere null per farmaco null");
    }

    @Test
    @Order(8)
    @DisplayName("Test recupero terapia con entrambi i parametri null")
    void testGetTherapyByFarmacoIdAndPatient_BothNull() {
        Terapia result = terapiaDao.getTherapyByFarmacoIdAndPatient(null, null);
        assertNull(result, "Il risultato dovrebbe essere null per entrambi i parametri null");
    }

    @Test
    @Order(9)
    @DisplayName("Test aggiornamento terapia con modifica di tutti i campi")
    void testUpdateTherapy_Success() {
        // Prima inseriamo una terapia
        Terapia terapiaOriginale = new Terapia(
                1,
                PERIODICITA.GIORNO,
                20.0,
                "g",
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 9, 1),
                "Terapia da aggiornare",
                farmacoTest,
                diabetologoTest,
                pazienteTest
        );
        terapiaDao.insertTherapy(terapiaOriginale);

        // Recuperiamo la terapia inserita
        Terapia terapiaDaAggiornare = terapiaDao.getTherapyByFarmacoIdAndPatient(
                pazienteTest,
                farmacoTest
        );

        assertNotNull(terapiaDaAggiornare, "La terapia dovrebbe essere stata inserita");
        int idOriginale = terapiaDaAggiornare.getId_terapia();

        // Creiamo una nuova terapia con valori aggiornati ma stesso ID
        Terapia terapiaAggiornata = new Terapia(
                4, // nuovo quanto
                PERIODICITA.GIORNO, // nuova periodicità
                30.0, // nuovo dosaggio
                "UI", // nuova unità
                LocalDate.of(2024, 4, 1), // nuova data inizio
                LocalDate.of(2024, 10, 1), // nuova data fine
                "Terapia aggiornata con nuovi parametri", // nuova descrizione
                farmacoTest,
                idOriginale // stesso ID
        );

        boolean updateResult = terapiaDao.updateTherapy(terapiaAggiornata);
        assertTrue(updateResult, "L'aggiornamento dovrebbe avere successo");

        // Verifichiamo tutte le modifiche
        Terapia terapiaVerifica = terapiaDao.getTherapyByFarmacoIdAndPatient(
                pazienteTest,
                farmacoTest
        );

        assertNotNull(terapiaVerifica, "La terapia aggiornata dovrebbe essere presente");
        assertEquals(idOriginale, terapiaVerifica.getId_terapia(),
                "L'ID dovrebbe rimanere lo stesso");
        assertEquals(4, terapiaVerifica.getQuanto(),
                "La quantità dovrebbe essere aggiornata a 4");
        assertEquals(PERIODICITA.GIORNO, terapiaVerifica.getPeriodicita(),
                "La periodicità dovrebbe essere aggiornata a SETTIMANALE");
        assertEquals(30.0, terapiaVerifica.getDosaggio_quantita(), 0.01,
                "Il dosaggio dovrebbe essere aggiornato a 30.0");
        assertEquals("UI", terapiaVerifica.getDosaggio_unita(),
                "L'unità dovrebbe essere aggiornata a UI");
        assertEquals(LocalDate.of(2024, 4, 1), terapiaVerifica.getData_inizio(),
                "La data inizio dovrebbe essere aggiornata");
        assertEquals(LocalDate.of(2024, 10, 1), terapiaVerifica.getData_fine(),
                "La data fine dovrebbe essere aggiornata");
        assertEquals("Terapia aggiornata con nuovi parametri",
                terapiaVerifica.getDescrizione(),
                "La descrizione dovrebbe essere aggiornata");
    }

    @Test
    @Order(10)
    @DisplayName("Test aggiornamento terapia null")
    void testUpdateTherapy_NullTerapia() {
        boolean result = terapiaDao.updateTherapy(null);
        assertFalse(result, "L'aggiornamento di una terapia null dovrebbe fallire");
    }

    @Test
    @Order(11)
    @DisplayName("Test rimozione terapia esistente")
    void testRemoveTherapy_Success() {
        // Prima inseriamo una terapia da rimuovere
        Terapia terapiaDaRimuovere = new Terapia(
                1,
                PERIODICITA.GIORNO,
                5.0,
                "mg",
                LocalDate.now(),
                LocalDate.now().plusMonths(1),
                "Terapia da rimuovere",
                farmacoTest,
                diabetologoTest,
                pazienteTest
        );
        terapiaDao.insertTherapy(terapiaDaRimuovere);

        // Recuperiamo l'ID della terapia
        Terapia terapiaInserita = terapiaDao.getTherapyByFarmacoIdAndPatient(
                pazienteTest,
                farmacoTest
        );

        assertNotNull(terapiaInserita, "La terapia dovrebbe essere stata inserita");
        int idTerapia = terapiaInserita.getId_terapia();
        assertTrue(idTerapia > 0, "L'ID terapia dovrebbe essere valido");

        // Rimuoviamo la terapia
        boolean removeResult = terapiaDao.removeTherapy(idTerapia);
        assertTrue(removeResult, "La rimozione dovrebbe avere successo");

        // Verifichiamo che la terapia sia stata rimossa
        Terapia terapiaRimossa = terapiaDao.getTherapyByFarmacoIdAndPatient(
                pazienteTest,
                farmacoTest
        );

        assertNull(terapiaRimossa, "La terapia dovrebbe essere stata rimossa dal database");
    }

    @Test
    @Order(12)
    @DisplayName("Test rimozione terapia con ID inesistente")
    void testRemoveTherapy_NonExistentId() {
        // Prova a rimuovere con un ID molto alto che probabilmente non esiste
        int idInesistente = 999999;

        assertDoesNotThrow(() -> terapiaDao.removeTherapy(idInesistente),
                "La rimozione di un ID inesistente non dovrebbe lanciare eccezioni");
    }

    @Test
    @Order(13)
    @DisplayName("Test con tutte le periodicità disponibili")
    void testAllPeriodicita() {
        PERIODICITA[] periodicita = PERIODICITA.values();

        for (PERIODICITA p : periodicita) {
            // Crea e inserisce una terapia per ogni periodicità
            Terapia terapia = new Terapia(
                    1,
                    p,
                    10.0,
                    "ml",
                    LocalDate.now(),
                    LocalDate.now().plusMonths(1),
                    "Test periodicità " + p.toString(),
                    farmacoTest,
                    diabetologoTest,
                    pazienteTest
            );

            boolean insertResult = terapiaDao.insertTherapy(terapia);
            assertTrue(insertResult, "Inserimento dovrebbe riuscire per periodicità " + p);

            // Verifica il recupero
            Terapia recuperata = terapiaDao.getTherapyByFarmacoIdAndPatient(
                    pazienteTest, farmacoTest);
            assertNotNull(recuperata, "La terapia dovrebbe essere recuperata");
            assertEquals(p, recuperata.getPeriodicita(),
                    "La periodicità dovrebbe corrispondere");

            // Cleanup immediato
            terapiaDao.removeTherapy(recuperata.getId_terapia());
        }
    }

    @Test
    @Order(14)
    @DisplayName("Test con dosaggi decimali diversi")
    void testDifferentDosaggi() {
        double[] dosaggi = {0.5, 1.0, 2.5, 10.0, 100.5, 1000.0};

        for (double dosaggio : dosaggi) {
            Terapia terapia = new Terapia(
                    1,
                    PERIODICITA.GIORNO,
                    dosaggio,
                    "UI",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30),
                    "Test dosaggio " + dosaggio,
                    farmacoTest,
                    diabetologoTest,
                    pazienteTest
            );

            terapiaDao.insertTherapy(terapia);

            Terapia recuperata = terapiaDao.getTherapyByFarmacoIdAndPatient(
                    pazienteTest, farmacoTest);
            assertNotNull(recuperata);
            assertEquals(dosaggio, recuperata.getDosaggio_quantita(), 0.01,
                    "Il dosaggio dovrebbe essere preservato correttamente");

            // Cleanup
            terapiaDao.removeTherapy(recuperata.getId_terapia());
        }
    }

    @Test
    @Order(15)
    @DisplayName("Test con date diverse")
    void testDifferentDates() {
        LocalDate oggi = LocalDate.now();
        LocalDate[] dateInizio = {
                oggi,
                oggi.minusDays(30),
                oggi.plusDays(7),
                LocalDate.of(2024, 1, 1)
        };

        for (LocalDate dataInizio : dateInizio) {
            LocalDate dataFine = dataInizio.plusMonths(6);

            Terapia terapia = new Terapia(
                    2,
                    PERIODICITA.GIORNO,
                    10.0,
                    "mg",
                    dataInizio,
                    dataFine,
                    "Test date",
                    farmacoTest,
                    diabetologoTest,
                    pazienteTest
            );

            terapiaDao.insertTherapy(terapia);

            Terapia recuperata = terapiaDao.getTherapyByFarmacoIdAndPatient(
                    pazienteTest, farmacoTest);
            assertNotNull(recuperata);
            assertEquals(dataInizio, recuperata.getData_inizio(),
                    "La data inizio dovrebbe corrispondere");
            assertEquals(dataFine, recuperata.getData_fine(),
                    "La data fine dovrebbe corrispondere");

            // Cleanup
            terapiaDao.removeTherapy(recuperata.getId_terapia());
        }
    }

    @AfterEach
    void tearDown() {
        // Pulizia dopo ogni test: rimuovi tutte le terapie del paziente di prova
        try {
            ObservableList<Terapia> terapie = terapiaDao.getAllTherapyByPatient(pazienteTest);
            if (terapie != null && !terapie.isEmpty()) {
                for (Terapia t : terapie) {
                    terapiaDao.removeTherapy(t.getId_terapia());
                }
            }
        } catch (Exception e) {
            System.err.println("Errore durante il cleanup: " + e.getMessage());
        }
    }

    @AfterAll
    static void tearDownAll() {
        // Cleanup finale per assicurarsi che non rimangano dati
        try {
            ObservableList<Terapia> terapie = terapiaDao.getAllTherapyByPatient(pazienteTest);
            if (terapie != null && !terapie.isEmpty()) {
                for (Terapia t : terapie) {
                    terapiaDao.removeTherapy(t.getId_terapia());
                }
            }
        } catch (Exception e) {
            System.err.println("Errore durante il cleanup finale: " + e.getMessage());
        }
        System.out.println("Test completati per TerapiaDaoImpl");
    }
}