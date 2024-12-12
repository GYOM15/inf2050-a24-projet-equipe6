package GROUPE6_INF2050.Reporting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsDataTest {
    private StatisticsData statisticsData;

    @BeforeEach
    void setUp() {
        statisticsData = new StatisticsData();
    }

    @Test
    void testIncrementTotalDeclarations() {
        statisticsData.incrementTotalDeclarations(5);
        assertEquals(5, statisticsData.getTotalDeclarations());
    }

    @Test
    void testIncrementCompleteDeclarations() {
        statisticsData.incrementCompleteDeclarations(3);
        assertEquals(3, statisticsData.getCompleteDeclarations());
    }

    @Test
    void testIncrementIncompleteOrInvalidDeclarations() {
        statisticsData.incrementIncompleteOrInvalidDeclarations(2);
        assertEquals(2, statisticsData.getIncompleteOrInvalidDeclarations());
    }

    @Test
    void testIncrementMaleDeclarations() {
        statisticsData.incrementMaleDeclarations(4);
        assertEquals(4, statisticsData.getMaleDeclarations());
    }

    @Test
    void testIncrementFemaleDeclarations() {
        statisticsData.incrementFemaleDeclarations(6);
        assertEquals(6, statisticsData.getFemaleDeclarations());
    }

    @Test
    void testIncrementUnknownGenderDeclarations() {
        statisticsData.incrementUnknownGenderDeclarations(1);
        assertEquals(1, statisticsData.getUnknownGenderDeclarations());
    }

    @Test
    void testIncrementTotalActivities() {
        statisticsData.incrementTotalActivities(8);
        assertEquals(8, statisticsData.getTotalActivities());
    }

    @Test
    void testIncrementActivitiesByCategory() {
        statisticsData.incrementActivitiesByCategory("Cours", 3);
        statisticsData.incrementActivitiesByCategory("Atelier", 2);
        statisticsData.incrementActivitiesByCategory("Cours", 1);
        Map<String, Integer> activities = statisticsData.getActivitiesByCategory();
        assertEquals(4, activities.get("Cours"));
        assertEquals(2, activities.get("Atelier"));
    }

    @Test
    void testIncrementCompleteDeclarationsByOrder() {
        statisticsData.incrementCompleteDeclarationsByOrder("Architectes", 2);
        statisticsData.incrementCompleteDeclarationsByOrder("Géologues", 3);
        statisticsData.incrementCompleteDeclarationsByOrder("Architectes", 1);
        Map<String, Integer> completeDeclarations = statisticsData.getCompleteDeclarationsByOrder();
        assertEquals(3, completeDeclarations.get("Architectes"));
        assertEquals(3, completeDeclarations.get("Géologues"));
    }

    @Test
    void testIncrementValidDeclarationsByOrder() {
        statisticsData.incrementValidDeclarationsByOrder("Psychologues", 5);
        statisticsData.incrementValidDeclarationsByOrder("Podiatres", 4);
        statisticsData.incrementValidDeclarationsByOrder("Psychologues", 2);
        Map<String, Integer> validDeclarations = statisticsData.getValidDeclarationsByOrder();
        assertEquals(7, validDeclarations.get("Psychologues"));
        assertEquals(4, validDeclarations.get("Podiatres"));
    }

    @Test
    void testIncrementInvalidPermitDeclarations() {
        statisticsData.incrementInvalidPermitDeclarations(3);
        assertEquals(3, statisticsData.getInvalidPermitDeclarations());
    }

    @Test
    void testReset() {
        statisticsData.incrementTotalDeclarations(5);
        statisticsData.incrementCompleteDeclarations(3);
        statisticsData.incrementMaleDeclarations(2);
        statisticsData.incrementActivitiesByCategory("Cours", 1);
        statisticsData.reset();
        assertEquals(0, statisticsData.getTotalDeclarations());
        assertEquals(0, statisticsData.getCompleteDeclarations());
        assertEquals(0, statisticsData.getMaleDeclarations());
        assertTrue(statisticsData.getActivitiesByCategory().isEmpty());
    }

    @Test
    void testToString() {
        statisticsData.incrementTotalDeclarations(1);
        statisticsData.incrementCompleteDeclarations(1);
        statisticsData.incrementActivitiesByCategory("Cours", 2);
        String toStringOutput = statisticsData.toString();
        assertTrue(toStringOutput.contains("totalDeclarations=1"));
        assertTrue(toStringOutput.contains("completeDeclarations=1"));
        assertTrue(toStringOutput.contains("activitiesByCategory={Cours=2}"));
    }

}