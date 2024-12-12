package GROUPE6_INF2050.Validators.HoursCalculators;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.CycleValidator;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.CycleValidatorRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateMinHoursByOrderCategoryConditionsTest {
    private static final String INPUT_FILE_GEOLOGUE_MINIMIUM_HOURS = "src/test/java/resources/CalculateMinHoursByOrderCategoryConditionsTest/inputFileTestGeologueMinimumHours.json";
    private static final String INPUT_FILE_GEOLOGUE_INSUFFICIENT_HOURS ="src/test/java/resources/CalculateMinHoursByOrderCategoryConditionsTest/inputFileTestGeologueInsufficientHours.json";
    private static final String INPUT_FILE_ARCHITECTE_MINIMIUM_HOURS = "src/test/java/resources/CalculateMinHoursByOrderCategoryConditionsTest/inputFileTestArchitecteMinimumHours.json";
    private static final String INPUT_FILE_ARCHITECTE_INSUFFICIENT_HOURS ="src/test/java/resources/CalculateMinHoursByOrderCategoryConditionsTest/inputFileTestArchitecteInsufficientHours.json";
    private static final String INPUT_FILE_PSYCHOLOGUE_MINIMIUM_HOURS = "src/test/java/resources/CalculateMinHoursByOrderCategoryConditionsTest/inputFileTestPsychologueMinimumHours.json";
    private static final String INPUT_FILE_PSYCHOLOGUE_INSUFFICIENT_HOURS ="src/test/java/resources/CalculateMinHoursByOrderCategoryConditionsTest/inputFileTestPsychologueInsufficientHours.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private JsonFileUtility jsonFileUtility;
    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        CycleValidator.setCurrentCycle(Cycle.DEFAULT_CYCLE);
        errorHandler = new ErrorHandler();
    }


    @Test
    void testValidateGeologueMinimumHours() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_GEOLOGUE_MINIMIUM_HOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(jsonFileUtility, ActivityOrder.GEOLOGUES, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    void testValidateGeologueInsufficientHours() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_GEOLOGUE_INSUFFICIENT_HOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(jsonFileUtility, ActivityOrder.GEOLOGUES, errorHandler);
        assertEquals(3, errorHandler.getErrors().size());
    }

    @Test
    void testValidateArchitecteMinimumHours() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_ARCHITECTE_MINIMIUM_HOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(jsonFileUtility, ActivityOrder.ARCHITECTES, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
    }

    @Test
    void testValidateArchitecteSufficientHours() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_ARCHITECTE_INSUFFICIENT_HOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(jsonFileUtility, ActivityOrder.ARCHITECTES, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    void testValidatePsychologueMinimumHours() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_PSYCHOLOGUE_MINIMIUM_HOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(jsonFileUtility, ActivityOrder.PSYCHOLOGUES, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    void testValidatePsychologueInsufficientHours() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_PSYCHOLOGUE_INSUFFICIENT_HOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(jsonFileUtility, ActivityOrder.PSYCHOLOGUES, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
    }

}