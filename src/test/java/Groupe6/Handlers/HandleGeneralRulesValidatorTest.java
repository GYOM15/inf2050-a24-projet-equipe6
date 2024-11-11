package Groupe6.Handlers;

import org.example.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.example.Inf2050.Groupe6.Handlers.HandleGeneralRulesValidator;
import org.example.Inf2050.Groupe6.Utilities.JsonFileUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandleGeneralRulesValidatorTest {

    private static final String INPUT_FILE_AllVALID = "tests/main/Vendor/HandleGeneralRulesValidatorTest/inputHandleGeneralRulesValidatorTest_AllValid.json";
    private static final String INPUT_FILE_INVALIDPERMITNUMBER = "tests/main/Vendor/HandleGeneralRulesValidatorTest/inputHandleGeneralRulesValidatorTest_InvalidPermitNumber.json";
    private static final String INPUT_FILE_MISSINGFIELDS = "tests/main/Vendor/HandleGeneralRulesValidatorTest/inputHandleGeneralRulesValidatorTest_MissingFields.json";
    private static final String OUTPUT_FILE = "tests/main/Vendor/outputTest.json";
    private ErrorHandler errorHandler;


    @BeforeEach
    void setup() throws Groupe6INF2050Exception {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testHandleGeneralsRules_AllValid() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_AllVALID, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertTrue(HandleGeneralRulesValidator.handleGeneralsRules(jsonFileUtility, errorHandler));
        assertFalse(errorHandler.hasErrors());
    }

    @Test
    public void testHandleGeneralsRules_InvalidPermitNumber() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_INVALIDPERMITNUMBER, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        try {
            HandleGeneralRulesValidator.handleGeneralsRules(jsonFileUtility, errorHandler);
            fail("Une exception devait être levée en raison du numéro de permis invalide.");
        } catch (Groupe6INF2050Exception e) {
            assertNotNull(e);
            assertTrue(e.getMessage().contains("Échec de la validation pour les raisons suivantes"));
        }
    }

    @Test
    public void testHandleGeneralsRules_MissingFields() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_MISSINGFIELDS, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        try {
            HandleGeneralRulesValidator.handleGeneralsRules(jsonFileUtility, errorHandler);
        } catch (Groupe6INF2050Exception e) {
            assertNotNull(e);
            assertTrue(e.getMessage().contains("Échec de la validation pour les raisons suivantes"));
        }
    }
}