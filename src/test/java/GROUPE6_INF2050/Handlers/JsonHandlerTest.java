package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;
import GROUPE6_INF2050.Validators.CycleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonHandlerTest {
    private static final String INPUT_FILE = "src/test/java/resources/JsonHandlerTest/inputFileTestJsonHandler.json";
    private static final String INPUT_FILE_INVALID = "src/test/java/resources/JsonHandlerTest/inputJsonHandlerTest_InvalidJsonThrowsException.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private static final String STATISTIC_FILE = "src/test/java/resources/statisticsTest.json";
    private JsonHandler jsonHandler;
    private HandleGeneralRulesValidator generalRulesValidator;
    private JsonFileUtility jsonFileUtility;
    private StatisticsData statisticsData;
    private StatisticsFileManager statisticsFileManager;

    @BeforeEach
    void setUp() {
        statisticsFileManager =new StatisticsFileManager(STATISTIC_FILE);
        generalRulesValidator = new HandleGeneralRulesValidator(statisticsFileManager);
        jsonHandler = new JsonHandler(generalRulesValidator);
        statisticsData = new StatisticsData();
    }

    @Test
    void testHandleJson_SuccessfulValidation() throws IOException, Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE, OUTPUT_FILE);
        jsonHandler.handleJson(jsonFileUtility, statisticsData);
        assertEquals(1, statisticsData.getTotalDeclarations());
        assertEquals(Cycle.CYCLE_2023_2025, CycleValidator.getCycle());
        assertEquals(ActivityOrder.ARCHITECTES, ActivityOrder.getCurrentOrder());
    }

    @Test
    void testHandleJson_InvalidJsonThrowsException() {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_INVALID, OUTPUT_FILE);
        assertThrows(Groupe6INF2050Exception.class, () -> jsonHandler.handleJson(jsonFileUtility, statisticsData));
        assertEquals(1, statisticsData.getTotalDeclarations());
    }

    @Test
    void testUpdateStatistics() throws IOException {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE, OUTPUT_FILE);
        try {
            jsonHandler.handleJson(jsonFileUtility, statisticsData);
        } catch (Groupe6INF2050Exception | IOException e) {
            fail("La méthode handleJson ne devrait pas lever d'exception ici.");
        }
        Map<String, Integer> resultat = new HashMap<>();
        resultat.put("architectes", 1);
        assertEquals(1, statisticsData.getTotalDeclarations());
        assertEquals(resultat, statisticsData.getValidDeclarationsByOrder());
    }

}