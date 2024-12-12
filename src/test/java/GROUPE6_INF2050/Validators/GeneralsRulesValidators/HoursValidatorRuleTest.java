package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HoursValidatorRuleTest {
    private static final String INPUT_FILE_VALIDHOURS = "src/test/java/resources/HoursValidatorTest/inputFileTest_ValidHours.json";
    private static final String INPUT_FILE_NEGATIVEHOURS = "src/test/java/resources/HoursValidatorTest/inputFileTest_NegativeHours.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testAreHoursPositive_ValidHours() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_VALIDHOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertTrue(HoursValidatorRule.areHoursPositive(jsonFileUtility, errorHandler));
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testAreHoursPositive_NegativeHours() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_NEGATIVEHOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertFalse(HoursValidatorRule.areHoursPositive(jsonFileUtility, errorHandler));
        assertFalse(errorHandler.getErrors().isEmpty());
    }

}