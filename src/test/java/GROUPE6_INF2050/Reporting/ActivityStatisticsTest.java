package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ActivityStatisticsTest {
    private static final String INPUT_FILE = "src/test/java/resources/ActivityStatisticsTest/inputFileTest_Default.json";
    private static final String INPUT_FILE_EMPTY ="src/test/java/resources/ActivityStatisticsTest/inputFileTest_Empty.json";
    private static final String OUTPUT_FILE = "src/test/java/resources/outputFileTest.json";
    private JsonFileUtility jsonFileUtility;

    @Test
    void testGetTotalValidActivities() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        int totalValidActivities = ActivityStatistics.getTotalValidActivities(jsonFileUtility);
        assertEquals(8, totalValidActivities);
    }

    @Test
    void testGetTotalActivitiesByCategory() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        Map<ActivityCategory, Integer> activitiesByCategory = ActivityStatistics.getTotalActivitiesByCategory(jsonFileUtility);
        assertEquals(1, activitiesByCategory.get(ActivityCategory.COURS).intValue());
        assertEquals(1, activitiesByCategory.get(ActivityCategory.SEMINAIRE).intValue());
        assertEquals(1, activitiesByCategory.get(ActivityCategory.ATELIER).intValue());
        assertNull(activitiesByCategory.get(ActivityCategory.CATEGORIE_NON_VALIDE));
    }

    @Test
    void testGetTotalValidActivitiesWithEmptyArray() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_EMPTY, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        int totalValidActivities = ActivityStatistics.getTotalValidActivities(jsonFileUtility);
        assertEquals(0, totalValidActivities);
    }

    @Test
    void testGetTotalActivitiesByCategoryWithEmptyArray() throws Groupe6INF2050Exception {
        jsonFileUtility = new JsonFileUtility(INPUT_FILE_EMPTY, OUTPUT_FILE);
        jsonFileUtility.loadAndValid();
        Map<ActivityCategory, Integer> activitiesByCategory = ActivityStatistics.getTotalActivitiesByCategory(jsonFileUtility);
        assertEquals(0, activitiesByCategory.size());
    }

}