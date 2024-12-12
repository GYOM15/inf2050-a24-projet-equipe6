package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonFieldsValidatorRuleTest {
    private static final String INPUT_FILE_ALLFIELD = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_AllField.json";
    private static final String INPUT_FILE_WITHOUTPERMIS = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutPermit.json";
    private static final String INPUT_FILE_WITHOUTACTIVITIES = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutActivities.json";
    private static final String INPUT_FILE_WITHOUTCYCLE = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutCycle.json";
    private static final String INPUT_FILE_WITHOUTORDER = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutOrder.json";
    private static final String INPUT_FILE_WITHOUTDATE = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutDate.json";
    private static final String INPUT_FILE_WITHOUTCATEGORIE = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutCategorie.json";
    private static final String INPUT_FILE_WITHOUTDESCRIPTION = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutDescription.json";
    private static final String INPUT_FILE_WITHOUTHOURS = "src/test/java/resources/JsonFieldsValidatorRuleTest/inputFileTest_WhitoutHours.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private ErrorHandler errorHandler;
    private JsonFileUtility jsonFileUtility;
    private StringBuilder message;
    private JsonFieldsValidatorRule jsonFieldsValidatorRule;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        message = new StringBuilder("error");
        jsonFieldsValidatorRule = new JsonFieldsValidatorRule();
    }

    @Test
    public void validateTest_AllField() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ALLFIELD, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutPermit() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTPERMIS, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutCycle() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTCYCLE, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutActivities() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTACTIVITIES, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutHours() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTHOURS, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutOrder() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTORDER, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutDescription() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTDESCRIPTION, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutDate() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTDATE, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_WithoutCategorie() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_WITHOUTCATEGORIE, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(jsonFieldsValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }
}