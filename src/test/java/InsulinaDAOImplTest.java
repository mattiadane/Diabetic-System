import org.junit.jupiter.api.*;

/**
 * Test di integrazione per InsulinaDAOImpl.
 * Questi test richiedono una connessione al database attiva.
 * Assicurarsi che il database sia configurato correttamente prima di eseguire i test.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InsulinaDAOImplTest {

    @BeforeAll
    static void setUpAll() {

    }

    @Test
    @Order(1)
    @DisplayName("Primo Test")
    void primoTest(){

    }

    @AfterAll
    static void terDownAll() {

    }

}