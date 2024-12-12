package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorRuleTest {
    private static final String INPUT_FILE_ARCHITECTEORDERVALID = "src/test/java/resources/OrderValidatorRuleTest/inputFileTest_ArchitecteOrderValid.json";
    private static final String INPUT_FILE_PSYCHOLOGUEORDERVALID = "src/test/java/resources/OrderValidatorRuleTest/inputFileTest_PsychologueOrderValid.json";
    private static final String INPUT_FILE_GEOLOGUEORDERVALID = "src/test/java/resources/OrderValidatorRuleTest/inputFileTest_GeologueOrderValid.json";
    private static final String INPUT_FILE_PODIATREORDERVALID = "src/test/java/resources/OrderValidatorRuleTest/inputFileTest_PodiatreOrderValid.json";
    private static final String INPUT_FILE_ORDERINVALID = "src/test/java/resources/OrderValidatorRuleTest/inputFileTest_OrderInvalid.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private ErrorHandler errorHandler;
    private JsonFileUtility jsonFileUtility;
    private StringBuilder message;
    private OrderValidatorRule orderValidatorRule;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        message = new StringBuilder("error");
        orderValidatorRule = new OrderValidatorRule();
    }

    @Test
    public void validateTest_ArchitecteOrderValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ARCHITECTEORDERVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(orderValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_PsychologueOrderValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_PSYCHOLOGUEORDERVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(orderValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_GeologueOrderValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_GEOLOGUEORDERVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(orderValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_PodiatreOrderValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_PODIATREORDERVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(orderValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_OrderInvalid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ORDERINVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(orderValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }
}