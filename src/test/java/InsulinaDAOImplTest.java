import com.dashapp.diabeticsystem.enums.GRAVITA;
import com.dashapp.diabeticsystem.enums.PERIODO;
import com.dashapp.diabeticsystem.models.Diabetologo;
import com.dashapp.diabeticsystem.models.Insulina;
import com.dashapp.diabeticsystem.models.Paziente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test per classe Insulina")
class InsulinaTest {

    private Paziente pazienteTest;
    private Diabetologo diabetologoTest;
    private LocalDateTime orarioTest;

    @BeforeEach
    void setUp() {
        // Creazione diabetologo di test
        diabetologoTest = new Diabetologo(
                1,
                "Mattia",
                "Danese",
                "mattia.danese@example.it",
                "DNSMTT04L21I775D",
                "M"
        );

        // Creazione paziente di test
        pazienteTest = new Paziente(
                1,
                "Davide",
                "Danese",
                "davide.danese04@example.it",
                "DNSDVD02L21I775D",
                LocalDate.of(2025, 6, 10),
                "M",
                diabetologoTest,
                null
        );

        orarioTest = LocalDateTime.of(2025, 1, 15, 8, 0);
    }

    // ========== Test Costruttori ==========

    @Test
    @DisplayName("Test costruttore senza paziente")
    void testCostruttoreSenzaPaziente() {
        Insulina insulina = new Insulina(100, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);

        assertNotNull(insulina);
        assertEquals(100, insulina.getLivello_insulina());
        assertEquals(PERIODO.PRIMA_DELLA_COLAZIONE, insulina.getPeriodo());
        assertEquals(orarioTest, insulina.getOrario());
        assertNull(insulina.getPaziente());
    }

    @Test
    @DisplayName("Test costruttore con paziente")
    void testCostruttoreConPaziente() {
        Insulina insulina = new Insulina(100, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest, pazienteTest);

        assertNotNull(insulina);
        assertEquals(100, insulina.getLivello_insulina());
        assertEquals(PERIODO.PRIMA_DELLA_COLAZIONE, insulina.getPeriodo());
        assertEquals(orarioTest, insulina.getOrario());
        assertEquals(pazienteTest, insulina.getPaziente());
    }

    // ========== Test Getters ==========

    @Test
    @DisplayName("Test getLivello_insulina")
    void testGetLivelloInsulina() {
        Insulina insulina = new Insulina(95, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(95, insulina.getLivello_insulina());
    }

    @Test
    @DisplayName("Test getPeriodo")
    void testGetPeriodo() {
        Insulina insulina = new Insulina(100, PERIODO.DOPO_PRANZO, orarioTest);
        assertEquals(PERIODO.DOPO_PRANZO, insulina.getPeriodo());
    }

    @Test
    @DisplayName("Test getOrario")
    void testGetOrario() {
        LocalDateTime orario = LocalDateTime.of(2025, 10, 15, 14, 30);
        Insulina insulina = new Insulina(100, PERIODO.DOPO_PRANZO, orario);
        assertEquals(orario, insulina.getOrario());
    }

    @Test
    @DisplayName("Test getPaziente")
    void testGetPaziente() {
        Insulina insulina = new Insulina(100, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest, pazienteTest);
        assertEquals(pazienteTest, insulina.getPaziente());
    }

    // ========== Test toString ==========

    @Test
    @DisplayName("Test toString")
    void testToString() {
        Insulina insulina = new Insulina(100, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        String expected = "100,prima colazione," + orarioTest;
        assertEquals(expected, insulina.toString());
    }

    // ========== Test getGravita - PRIMA dei pasti ==========

    @Test
    @DisplayName("Test getGravita NORMALE - Prima colazione (80-130)")
    void testGravitaNormalePrimaColazione() {
        Insulina insulina1 = new Insulina(80, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina1.getGravita());

        Insulina insulina2 = new Insulina(100, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina2.getGravita());

        Insulina insulina3 = new Insulina(130, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina3.getGravita());
    }

    @Test
    @DisplayName("Test getGravita LIEVE - Prima colazione (55-79 o 131-180)")
    void testGravitaLievePrimaColazione() {
        // Limite inferiore
        Insulina insulina1 = new Insulina(55, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina1.getGravita());

        Insulina insulina2 = new Insulina(70, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina2.getGravita());

        Insulina insulina3 = new Insulina(79, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina3.getGravita());

        // Limite superiore
        Insulina insulina4 = new Insulina(131, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina4.getGravita());

        Insulina insulina5 = new Insulina(150, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina5.getGravita());

        Insulina insulina6 = new Insulina(180, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina6.getGravita());
    }

    @Test
    @DisplayName("Test getGravita CRITICA - Prima colazione (<55 o >180)")
    void testGravitaCriticaPrimaColazione() {
        // Troppo basso
        Insulina insulina1 = new Insulina(54, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina1.getGravita());

        Insulina insulina2 = new Insulina(30, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina2.getGravita());

        // Troppo alto
        Insulina insulina3 = new Insulina(181, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina3.getGravita());

        Insulina insulina4 = new Insulina(250, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina4.getGravita());
    }

    @Test
    @DisplayName("Test getGravita NORMALE - Prima pranzo (80-130)")
    void testGravitaNormalePrimaPranzo() {
        Insulina insulina1 = new Insulina(85, PERIODO.PRIMA_DEL_PRANZO, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina1.getGravita());

        Insulina insulina2 = new Insulina(105, PERIODO.PRIMA_DEL_PRANZO, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina2.getGravita());

        Insulina insulina3 = new Insulina(125, PERIODO.PRIMA_DEL_PRANZO, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina3.getGravita());
    }

    @Test
    @DisplayName("Test getGravita NORMALE - Prima cena (80-130)")
    void testGravitaNormalePrimaCena() {
        Insulina insulina = new Insulina(110, PERIODO.PRIMA_DELLA_CENA, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina.getGravita());
    }

    // ========== Test getGravita - DOPO i pasti ==========

    @Test
    @DisplayName("Test getGravita CRITICA - Dopo pasti (<=70 o >=250)")
    void testGravitaCriticaDopoColazione() {
        // Troppo basso
        Insulina insulina1 = new Insulina(70, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina1.getGravita());

        Insulina insulina2 = new Insulina(50, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina2.getGravita());

        // Troppo alto
        Insulina insulina3 = new Insulina(250, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina3.getGravita());

        Insulina insulina4 = new Insulina(300, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina4.getGravita());
    }

    @Test
    @DisplayName("Test getGravita LIEVE - Dopo pasti (>180)")
    void testGravitaLieveDopoColazione() {
        Insulina insulina1 = new Insulina(181, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina1.getGravita());

        Insulina insulina2 = new Insulina(200, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina2.getGravita());

        Insulina insulina3 = new Insulina(249, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina3.getGravita());
    }

    @Test
    @DisplayName("Test getGravita NORMALE - Dopo pasti (71-180)")
    void testGravitaNormaleDopoColazione() {
        Insulina insulina1 = new Insulina(71, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina1.getGravita());

        Insulina insulina2 = new Insulina(120, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina2.getGravita());

        Insulina insulina3 = new Insulina(180, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina3.getGravita());
    }

    @Test
    @DisplayName("Test getGravita NORMALE - Dopo pranzo (71-180)")
    void testGravitaNormaleDopoPranzo() {
        Insulina insulina = new Insulina(150, PERIODO.DOPO_PRANZO, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina.getGravita());
    }

    @Test
    @DisplayName("Test getGravita NORMALE - Dopo cena (71-180)")
    void testGravitaNormaleDopoCena() {
        Insulina insulina = new Insulina(140, PERIODO.DOPO_CENA, orarioTest);
        assertEquals(GRAVITA.NORMALE, insulina.getGravita());
    }

    @Test
    @DisplayName("Test getGravita CRITICA - Dopo pranzo (<=70)")
    void testGravitaCriticaDopoPranzo() {
        Insulina insulina = new Insulina(60, PERIODO.DOPO_PRANZO, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina.getGravita());
    }

    @Test
    @DisplayName("Test getGravita LIEVE - Dopo cena (>180)")
    void testGravitaLieveDopoCena() {
        Insulina insulina = new Insulina(220, PERIODO.DOPO_CENA, orarioTest);
        assertEquals(GRAVITA.LIEVE, insulina.getGravita());
    }

    // ========== Test casi limite ==========

    @Test
    @DisplayName("Test valori limite esatti - Prima colazione")
    void testValoriLimiteEsattiPrimaColazione() {
        // Esattamente sul confine normale/lieve inferiore
        assertEquals(GRAVITA.NORMALE, new Insulina(80, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.LIEVE, new Insulina(79, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());

        // Esattamente sul confine normale/lieve superiore
        assertEquals(GRAVITA.NORMALE, new Insulina(130, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.LIEVE, new Insulina(131, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());

        // Esattamente sul confine lieve/critica inferiore
        assertEquals(GRAVITA.LIEVE, new Insulina(55, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.CRITICA, new Insulina(54, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());

        // Esattamente sul confine lieve/critica superiore
        assertEquals(GRAVITA.LIEVE, new Insulina(180, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.CRITICA, new Insulina(181, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest).getGravita());
    }

    @Test
    @DisplayName("Test valori limite esatti - Dopo colazione")
    void testValoriLimiteEsattiDopoColazione() {
        // Esattamente sul confine critica/normale inferiore
        assertEquals(GRAVITA.CRITICA, new Insulina(70, PERIODO.DOPO_LA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.NORMALE, new Insulina(71, PERIODO.DOPO_LA_COLAZIONE, orarioTest).getGravita());

        // Esattamente sul confine normale/lieve
        assertEquals(GRAVITA.NORMALE, new Insulina(180, PERIODO.DOPO_LA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.LIEVE, new Insulina(181, PERIODO.DOPO_LA_COLAZIONE, orarioTest).getGravita());

        // Esattamente sul confine lieve/critica superiore
        assertEquals(GRAVITA.LIEVE, new Insulina(249, PERIODO.DOPO_LA_COLAZIONE, orarioTest).getGravita());
        assertEquals(GRAVITA.CRITICA, new Insulina(250, PERIODO.DOPO_LA_COLAZIONE, orarioTest).getGravita());
    }

    @Test
    @DisplayName("Test valori estremi")
    void testValoriEstremi() {
        // Valori molto bassi
        Insulina insulina1 = new Insulina(10, PERIODO.PRIMA_DELLA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina1.getGravita());

        // Valori molto alti
        Insulina insulina2 = new Insulina(500, PERIODO.DOPO_LA_COLAZIONE, orarioTest);
        assertEquals(GRAVITA.CRITICA, insulina2.getGravita());
    }

    @Test
    @DisplayName("Test tutti i periodi - coverage completo")
    void testTuttiIPeriodi() {
        PERIODO[] periodiPrima = {PERIODO.PRIMA_DELLA_COLAZIONE, PERIODO.PRIMA_DEL_PRANZO, PERIODO.PRIMA_DELLA_CENA};
        PERIODO[] periodiDopo = {PERIODO.DOPO_LA_COLAZIONE, PERIODO.DOPO_PRANZO, PERIODO.DOPO_CENA};

        // Test per tutti i periodi "prima"
        for (PERIODO periodo : periodiPrima) {
            Insulina insulina = new Insulina(100, periodo, orarioTest);
            assertEquals(GRAVITA.NORMALE, insulina.getGravita(),
                    "Fallito per periodo: " + periodo);
        }

        // Test per tutti i periodi "dopo"
        for (PERIODO periodo : periodiDopo) {
            Insulina insulina = new Insulina(100, periodo, orarioTest);
            assertEquals(GRAVITA.NORMALE, insulina.getGravita(),
                    "Fallito per periodo: " + periodo);
        }
    }
}