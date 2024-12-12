package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonValidatorRuleTest {

    private static final String INPUT_FILE_FEMALEPERSONVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_FemalePersonValid.json";
    private static final String INPUT_FILE_MALEPERONVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_MalePersonValid.json";
    private static final String INPUT_FILE_UNKNOWPERONVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_UnknowPersonValid.json";
    private static final String INPUT_FILE_GENDERINVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_GenderInvalid.json";
    private static final String INPUT_FILE_NAMEINVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_NameInvalid.json";
    private static final String INPUT_FILE_FIRSTNAMEINVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_FirstNameInvalid.json";
    private static final String INPUT_FILE_ALLINVALID = "src/test/java/resources/PersonValidatorRuleTest/inputFileTest_AllInvalid.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private ErrorHandler errorHandler;
    private JsonFileUtility jsonFileUtility;
    private StringBuilder message;
    private  PersonValidatorRule personValidatorRule;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        message = new StringBuilder("error");
        personValidatorRule = new PersonValidatorRule();
    }

    @Test
    public void validateTest_FemalePersonValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_FEMALEPERSONVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertEquals(2, PersonValidatorRule.getGender());
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    public void validateTest_MalePersonValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_MALEPERONVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertEquals(1, PersonValidatorRule.getGender());
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    public void validateTest_UnknowPersonValid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_UNKNOWPERONVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertEquals(0, PersonValidatorRule.getGender());
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    public void validateTest_GenderInvalid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_GENDERINVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertFalse(errorHandler.hasErrors());
    }

    @Test
    public void validateTest_NameInvalid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_NAMEINVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertFalse(errorHandler.hasErrors());
    }

    @Test
    public void validateTest_FirstNameInvalid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_FIRSTNAMEINVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertFalse(errorHandler.hasErrors());
    }

    @Test
    public void validateTest_AllInvalid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ALLINVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, message);
        assertFalse(errorHandler.hasErrors());
    }



}