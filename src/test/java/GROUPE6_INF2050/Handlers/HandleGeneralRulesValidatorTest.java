package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HandleGeneralRulesValidatorTest {

    private static final String INPUT_FILE_AllVALID = "src/test/java/resources/HandleGeneralRulesValidatorTest/inputHandleGeneralRulesValidatorTest_AllValid.json";
    private static final String INPUT_FILE_INVALIDPERMITNUMBER = "src/test/java/resources/HandleGeneralRulesValidatorTest/inputHandleGeneralRulesValidatorTest_InvalidPermitNumber.json";
    private static final String INPUT_FILE_MISSINGFIELDS = "src/test/java/resources/HandleGeneralRulesValidatorTest/inputHandleGeneralRulesValidatorTest_MissingFields.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private static final String STATISTIC_FILE = "src/test/java/resources/statisticsTest.json";

    private ErrorHandler errorHandler;
    private StatisticsData statisticsData;
    private StatisticsFileManager statisticsFileManager;
    private HandleGeneralRulesValidator handleValidator;

    @BeforeEach
    void setup() throws Groupe6INF2050Exception {
        errorHandler = new ErrorHandler();
        statisticsData = new StatisticsData();
        statisticsFileManager = new StatisticsFileManager(STATISTIC_FILE);
        handleValidator = new HandleGeneralRulesValidator(statisticsFileManager);
    }

    @Test
    public void testHandleGeneralsRules_AllValid() throws Groupe6INF2050Exception, IOException {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_AllVALID, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertTrue(handleValidator.handleGeneralsRules(jsonFileUtility, errorHandler,statisticsData));
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    public void testHandleGeneralsRules_InvalidPermitNumber() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_INVALIDPERMITNUMBER, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        try {
            handleValidator.handleGeneralsRules(jsonFileUtility, errorHandler, statisticsData);
            fail("Une exception devait être levée en raison du numéro de permis invalide.");
        } catch (Groupe6INF2050Exception e) {
            assertNotNull(e);
            assertTrue(e.getMessage().contains("Échec de la validation pour les raisons suivantes"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHandleGeneralsRules_MissingFields() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_MISSINGFIELDS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        try {
            handleValidator.handleGeneralsRules(jsonFileUtility, errorHandler, statisticsData);
        } catch (Groupe6INF2050Exception e) {
            assertNotNull(e);
            assertTrue(e.getMessage().contains("Échec de la validation pour les raisons suivantes"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}