package GROUPE6_INF2050.Validators.Generics.ActivityFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityJsonBuilderByCategoriesConditionsTest {
    private ActivityJsonBuilderByCategoriesConditions builder;

    @BeforeEach
    void setUp() {
        builder = new ActivityJsonBuilderByCategoriesConditions();
    }

    @Test
    void testFilterByCategorieCondition_SingleCategory() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        JSONObject activity3 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 15);
        activity1.put("date", "2024-03-21");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "atelier");
        activity2.put("heures", 10);
        activity2.put("date", "2024-03-20");
        activity3.put("description", "Cours d'architecture");
        activity3.put("categorie", "cours");
        activity3.put("heures", 7);
        activity3.put("date", "2024-03-10");
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        builder.filterByCategorieCondition(activities, List.of("cours"));
        JSONArray filteredActivities = builder.getActivitiesByCategory("cours");
        assertEquals(2, filteredActivities.size());
        assertEquals("Cours de Java", filteredActivities.getJSONObject(0).getString("description"));
        assertEquals("Cours d'architecture", filteredActivities.getJSONObject(1).getString("description"));
    }

    @Test
    void testFilterByCategorieCondition_EmptyResult() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activity1.put("date", "2024-03-21");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "atelier");
        activity2.put("heures", 5);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        builder.filterByCategorieCondition(activities, List.of("séminaire"));
        JSONArray filteredSeminars = builder.getActivitiesByCategory("séminaire");
        assertEquals(0, filteredSeminars.size());
    }

    @Test
    void testClearAllFilters() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activity1.put("date", "2024-03-21");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "atelier");
        activity2.put("heures", 5);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        builder.filterByCategorieCondition(activities, List.of("cours"));
        builder.clearAllFilters();
        JSONArray filteredCourses = builder.getActivitiesByCategory("cours");
        assertEquals(0, filteredCourses.size());
    }

    @Test
    void testFilterWithInvalidActivity() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activity1.put("date", "2024-03-21");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "atelier");
        activity2.put("heures", 5);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        builder.filterByCategorieCondition(activities, List.of("cours"));
        JSONArray filteredCourses = builder.getActivitiesByCategory("cours");
        assertEquals(1, filteredCourses.size());
    }
}