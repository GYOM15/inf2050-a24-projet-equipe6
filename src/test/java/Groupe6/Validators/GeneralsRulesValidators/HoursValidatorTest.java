package Groupe6.Validators.GeneralsRulesValidators;


import org.example.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.example.Inf2050.Groupe6.Utilities.JsonFileUtility;
import org.example.Inf2050.Groupe6.Validators.GeneralsRulesValidators.HoursValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HoursValidatorTest {
    private static final String INPUT_FILE_VALIDHOURS = "tests/main/Vendor/HoursValidatorTest/inputTestAreHoursPositive_ValidHours.json";
    private static final String INPUT_FILE_NEGATIVEHOURS = "tests/main/Vendor/HoursValidatorTest/inputTestAreHoursPositive_NegativeHours.json";
    private static final String OUTPUT_FILE = "tests/main/Vendor/outputTest.json";
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testAreHoursPositive_ValidHours() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_VALIDHOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertTrue(HoursValidator.areHoursPositive(jsonFileUtility, errorHandler));
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testAreHoursPositive_NegativeHours() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_NEGATIVEHOURS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertFalse(HoursValidator.areHoursPositive(jsonFileUtility, errorHandler));
        assertFalse(errorHandler.getErrors().isEmpty());
    }

}