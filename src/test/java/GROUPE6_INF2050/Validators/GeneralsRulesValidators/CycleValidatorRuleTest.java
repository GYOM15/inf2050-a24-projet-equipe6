package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CycleValidatorRuleTest {
    private static final String INPUT_FILE_ARCHITECTE2020_2022 = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_Architecte2020_2022.json";
    private static final String INPUT_FILE_ARCHITECTE2018_2020 = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_Architecte2018_2020.json";
    private static final String INPUT_FILE_ARCHITECTE2023_2025 = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_Architecte2023_2025.json";
    private static final String INPUT_FILE_PSYCHOLOGUE2020_2025 = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_Psychologue2020_2025.json";
    private static final String INPUT_FILE_GEOLOGUE2021_2024 = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_Geologue2021_2024.json";
    private static final String INPUT_FILE_PODIATRE2021_2024 = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_Podiatre2021_2024.json";
    private static final String INPUT_FILE_CYCLEINVALID = "src/test/java/resources/CycleValidatorRuleTest/inputFileTest_CycleInvalid.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private ErrorHandler errorHandler;
    private JsonFileUtility jsonFileUtility;
    private StringBuilder message;
    private CycleValidatorRule cycleValidatorRule;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        message = new StringBuilder("error");
        cycleValidatorRule = new CycleValidatorRule();
    }

    @Test
    public void validateTest_Architecte2020_2022() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ARCHITECTE2020_2022, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_Architecte2018_2020() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ARCHITECTE2018_2020, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_Architecte2023_2025() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_ARCHITECTE2023_2025, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_Psychologue2020_2025() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_PSYCHOLOGUE2020_2025, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_Geologue2023_2025() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_GEOLOGUE2021_2024, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_Podiatre2023_2025() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_PODIATRE2021_2024, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertTrue(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

    @Test
    public void validateTest_CycleInvalid() throws Groupe6INF2050Exception {
        jsonFileUtility =new JsonFileUtility(INPUT_FILE_CYCLEINVALID, OUTPUT_FILE );
        jsonFileUtility.loadAndValid();
        assertFalse(cycleValidatorRule.validate(jsonFileUtility, errorHandler, message));
    }

}