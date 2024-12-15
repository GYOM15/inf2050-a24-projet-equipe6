package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.CycleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HandleTotalHoursByCategoryTest {
    private static final String INPUT_FILE_ARCHITECTES = "src/test/java/resources/HandleTotalHoursByCategoryTest/inputFileTest_Architectes.json";
    private static final String INPUT_FILE_PSYCHOLOGUES = "src/test/java/resources/HandleTotalHoursByCategoryTest/inputFileTest_Psychologues.json";
    private static final String INPUT_FILE_GEOLOGUES = "src/test/java/resources/HandleTotalHoursByCategoryTest/inputFileTest_Geologues.json";
    private static final String INPUT_FILE_PODIATRES = "src/test/java/resources/HandleTotalHoursByCategoryTest/inputFileTest_Podiatres.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private JsonFileUtility jsonFileUtility;
    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        CycleValidator.setCurrentCycle(Cycle.DEFAULT_CYCLE);
        errorHandler = new ErrorHandler();
    }

    @Test
    void testHandleHoursTotalArchitectes() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_ARCHITECTES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 51, errorHandler);
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    void testHandleHoursTotalPsychologues() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_PSYCHOLOGUES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 91, errorHandler);
        System.out.println(errorHandler.getErrors());
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    void testHandleHoursTotalGeologues() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_GEOLOGUES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 56, errorHandler);
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    void testHandleHoursTotalPodiatres() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_PODIATRES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 41, errorHandler);
        assertTrue(errorHandler.hasErrors());
    }
    @Test
    void testHandleHoursTotalWithNullOrder() throws Groupe6INF2050Exception {
        ActivityOrder.setCurrentOrder(null); // Simuler un ordre nul
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_ARCHITECTES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 51, errorHandler);
        assertTrue(errorHandler.hasErrors()); // Vérifier qu'aucune erreur n'est générée
    }
    @Test
    void testHandleHoursTotalArchitectesMinHours() throws Groupe6INF2050Exception {
        ActivityOrder.setCurrentOrder(ActivityOrder.ARCHITECTES);
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_ARCHITECTES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 42, errorHandler); // Test avec des heures minimales
        assertFalse(errorHandler.hasErrors()); // Vérifier que des erreurs sont générées
    }
    @Test
    void testHandleHoursTotalPsychologuesMinHours() throws Groupe6INF2050Exception {
        ActivityOrder.setCurrentOrder(ActivityOrder.PSYCHOLOGUES);
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_PSYCHOLOGUES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 90, errorHandler); // Test avec des heures minimales
        assertTrue(errorHandler.hasErrors());
    }
    @Test
    void testHandleHoursTotalGeologuesMinHours() throws Groupe6INF2050Exception {
        ActivityOrder.setCurrentOrder(ActivityOrder.GEOLOGUES);
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_GEOLOGUES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 55, errorHandler); // Test avec des heures minimales
        assertTrue(errorHandler.hasErrors());
    }
    @Test
    void testHandleHoursTotalPodiatresMinHours() throws Groupe6INF2050Exception {
        ActivityOrder.setCurrentOrder(ActivityOrder.PODIATRES);
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_PODIATRES, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        HandleTotalHoursByCategory.handleHoursTotal(jsonFileUtility, 60, errorHandler); // Test avec des heures minimales
        assertTrue(errorHandler.hasErrors()); // Vérifier que des erreurs sont générées
    }
}