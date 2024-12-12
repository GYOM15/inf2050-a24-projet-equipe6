package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Handlers.HandleGeneralRulesValidator;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PersonValidatorRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {
    private static final String INPUT_FILE_MALE = "src/test/java/resources/StatisticsTest/inputFileTest_Male.json";
    private static final String INPUT_FILE_FEMALE = "src/test/java/resources/StatisticsTest/inputFileTest_Female.json";
    private static final String INPUT_FILE_UNKNOW = "src/test/java/resources/StatisticsTest/inputFileTest_Unknow.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private static final String STATISTICS_FILE ="src/main/resources/statistics.json";
    private JsonFileUtility jsonFileUtility;
    private StatisticsData statisticsData;
    private ErrorHandler errorHandler;
    private StatisticsFileManager statisticsFileManager;
    private HandleGeneralRulesValidator handleGeneralRules;
    private PersonValidatorRule personValidatorRule;

    @BeforeEach
    void setUp() {
        statisticsData = new StatisticsData();
        errorHandler = new ErrorHandler();
        statisticsFileManager = new StatisticsFileManager(STATISTICS_FILE);
        handleGeneralRules = new HandleGeneralRulesValidator(statisticsFileManager);
        personValidatorRule = new PersonValidatorRule();
    }

    @Test
    void testValidateAndCalculateStatistics_ValidDeclarations() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_MALE, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, new StringBuilder("inconnue"));
        Statistics statistics = new Statistics(jsonFileUtility, statisticsData, errorHandler);
        ActivityOrder.setCurrentOrder(ActivityOrder.ARCHITECTES);
        statistics.validateAndCalculateStatistics();
        assertEquals(8, statisticsData.getTotalActivities());
        assertEquals(1, statisticsData.getMaleDeclarations());
        assertEquals(0, statisticsData.getFemaleDeclarations());
        assertEquals(0, statisticsData.getUnknownGenderDeclarations());
        assertEquals(1, statisticsData.getCompleteDeclarations());
        assertEquals(0, statisticsData.getIncompleteOrInvalidDeclarations());
    }

    @Test
    void testProcessActivitiesByCategory() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_MALE, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, new StringBuilder("inconnue"));
        Statistics statistics = new Statistics(jsonFileUtility, statisticsData, errorHandler);
        ActivityOrder.setCurrentOrder(ActivityOrder.ARCHITECTES);
        statistics.validateAndCalculateStatistics();
        Map<String, Integer> activitiesByCategory = statisticsData.getActivitiesByCategory();
        assertEquals(1, activitiesByCategory.get(ActivityCategory.COURS.getCategoryFromJsonObj()).intValue());
        assertEquals(1, activitiesByCategory.get(ActivityCategory.SEMINAIRE.getCategoryFromJsonObj()).intValue());
        assertNull(activitiesByCategory.get(ActivityCategory.CATEGORIE_NON_VALIDE.getCategoryFromJsonObj()));
    }

    @Test
    void testProcessGenderStatistics_Female() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_FEMALE, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, new StringBuilder("inconnue"));
        Statistics statistics = new Statistics(jsonFileUtility, statisticsData, errorHandler);
        ActivityOrder.setCurrentOrder(ActivityOrder.ARCHITECTES);
        statistics.validateAndCalculateStatistics();
        assertEquals(0, statisticsData.getMaleDeclarations());
        assertEquals(1, statisticsData.getFemaleDeclarations());
        assertEquals(0, statisticsData.getUnknownGenderDeclarations());
    }

    @Test
    void testProcessGenderStatistics_Unknown() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_UNKNOW, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        personValidatorRule.validate(jsonFileUtility, errorHandler, new StringBuilder("inconnue"));
        Statistics statistics = new Statistics(jsonFileUtility, statisticsData, errorHandler);
        ActivityOrder.setCurrentOrder(ActivityOrder.ARCHITECTES);
        statistics.validateAndCalculateStatistics();
        assertEquals(0, statisticsData.getMaleDeclarations());
        assertEquals(0, statisticsData.getFemaleDeclarations());
        assertEquals(1, statisticsData.getUnknownGenderDeclarations());
    }
}