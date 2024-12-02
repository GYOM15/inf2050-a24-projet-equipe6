package Groupe6.Validators.GeneralsRulesValidators;


import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.DescriptionValidator;




class DescriptionValidatorTest {
    private static final String INPUT_FILE_DESCRIPTIONTOOSHORT = "tests/main/Vendor/DescriptionValidatorTest/inputTestIsDescriptionOver20Characters_DescriptionTooShort.json";
    private static final String INPUT_FILE_VALIDDESCRIPTION = "tests/main/Vendor/DescriptionValidatorTest/inputTestIsDescriptionOver20Characters_ValidDescription.json";
    private static final String OUTPUT_FILE = "tests/main/Vendor/outputTest.json";
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testIsDescriptionOver20Characters_ValidDescription() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_VALIDDESCRIPTION, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertTrue(DescriptionValidator.isDescriptionOver20Characters(jsonFileUtility, errorHandler));
    }

    @Test
    public void testIsDescriptionOver20Characters_DescriptionTooShort() throws Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(INPUT_FILE_DESCRIPTIONTOOSHORT, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        assertFalse(DescriptionValidator.isDescriptionOver20Characters(jsonFileUtility, errorHandler));
    }
}