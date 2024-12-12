package GROUPE6_INF2050.Validators.Generics.Calculator;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.CycleValidator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityHoursCalculatorTest {
    private ErrorHandler errorHandler;
    private CycleValidator cycleValidator;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler(); // Utilisation d'une instance réelle de ErrorHandler
    }

    @Test
    void testGetTotalHours_ValidActivities() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activity1.put("date", "2024-03-21");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "conférence");
        activity2.put("heures", 5);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2025);
        int totalHours = ActivityHoursCalculator.getTotalHours(jsonObject, errorHandler);
        assertEquals(15, totalHours);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    void testGetTotalHours_InvalidCategory() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "categorie_invalide");
        activity1.put("heures", 5);
        activity1.put("date", "2024-03-21");
        activities.add(activity1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        int totalHours = ActivityHoursCalculator.getTotalHours(jsonObject, errorHandler);
        assertEquals(0, totalHours);
        assertEquals(1, errorHandler.getErrors().size());
    }

    @Test
    void testGetTotalHours_ExceedingMaxHoursPerDay() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 7);
        activity1.put("date", "2024-03-20");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "conférence");
        activity2.put("heures", 6);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2025);
        int totalHours = ActivityHoursCalculator.getTotalHours(jsonObject, errorHandler);
        assertEquals(10, totalHours);
        assertEquals(1, errorHandler.getErrors().size());
    }

    @Test
    void testGetTotalHours_InvalidDate() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 5);
        activity1.put("date", "date_invalide");
        activities.add(activity1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        int totalHours = ActivityHoursCalculator.getTotalHours(jsonObject, errorHandler);
        assertEquals(0, totalHours);
        assertEquals(1, errorHandler.getErrors().size());
    }

    @Test
    void testGetTotalHours_MixedValidAndInvalidActivities() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        JSONObject activity3 = new JSONObject();
        activity1.put("description", "Cours de Java");
        activity1.put("categorie", "cours");
        activity1.put("heures", 5);
        activity1.put("date", "2024-03-20");
        activity2.put("description", "Atelier de design");
        activity2.put("categorie", "categorie_invalide");
        activity2.put("heures", 3);
        activity2.put("date", "2024-03-20");
        activity3.put("description", "Atelier de design");
        activity3.put("categorie", "conférence");
        activity3.put("heures", 4);
        activity3.put("date", "date_invalide");
        activities.add(activity1);
        activities.add(activity2);
        activities.add(activity3);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2025);
        int totalHours = ActivityHoursCalculator.getTotalHours(jsonObject, errorHandler);
        assertEquals(5, totalHours);
        assertEquals(2, errorHandler.getErrors().size());
    }
}