package GROUPE6_INF2050.Validators.GeneralsRulesValidators;


import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class DescriptionValidatorRuleTest {
    private static final String INPUT_FILE_DESCRIPTIONTOOSHORT = "src/test/java/resources/DescriptionValidatorRuleTest/inputFileTest_DescriptionTooShort.json";
    private static final String INPUT_FILE_VALIDDESCRIPTION = "src/test/java/resources/DescriptionValidatorRuleTest/inputFileTest_ValidDescription.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testIsDescriptionOver20Characters_ValidDescription() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_VALIDDESCRIPTION, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertTrue(DescriptionValidatorRule.isDescriptionOver20Characters(jsonFileUtility, errorHandler));
    }

    @Test
    public void testIsDescriptionOver20Characters_DescriptionTooShort() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_DESCRIPTIONTOOSHORT, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertFalse(DescriptionValidatorRule.isDescriptionOver20Characters(jsonFileUtility, errorHandler));
    }
}