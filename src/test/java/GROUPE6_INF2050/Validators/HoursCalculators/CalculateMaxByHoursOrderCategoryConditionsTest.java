package GROUPE6_INF2050.Validators.HoursCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.CycleValidator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateMaxByHoursOrderCategoryConditionsTest {
    private ErrorHandler errorHandler;
    private CycleValidator cycleValidator;

    @BeforeEach
    void setUp(){
        errorHandler = new ErrorHandler();
    }
    @Test
    void testValidatePsychologueMaxHours() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("categorie","conférence");
        activity1.put("heures", 10);
        activity1.put("date", "2024-03-21");
        activity2.put("categorie","conférence");
        activity2.put("heures", 8);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2025);
        int exceededHours = CalculateMaxByHoursOrderCategoryConditions.validatePsychologueMaxHours(activities, errorHandler);
        assertEquals(3, exceededHours);
    }

    @Test
    void testValidateArchitecteMaxHours() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("categorie", "groupe de discussion");
        activity1.put("heures", 18);
        activity1.put("date", "2024-03-21");
        activity2.put("categorie", "projet de recherche");
        activity2.put("heures", 25);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2023_2025);
        int exceededHours = CalculateMaxByHoursOrderCategoryConditions.validateArchitecteMaxHours(activities, errorHandler);
        assertEquals(0, exceededHours);
    }

    @Test
    void testNoExceedingHours() {
        JSONArray activities = new JSONArray();
        JSONObject activity1 = new JSONObject();
        JSONObject activity2 = new JSONObject();
        activity1.put("categorie", "présentation");
        activity1.put("heures", 15);
        activity1.put("date", "2024-03-21");
        activity2.put("categorie", "conférence");
        activity2.put("heures", 10);
        activity2.put("date", "2024-03-20");
        activities.add(activity1);
        activities.add(activity2);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2025);
        int exceededHoursPsychologues = CalculateMaxByHoursOrderCategoryConditions.validatePsychologueMaxHours(activities, errorHandler);
        assertEquals(0, exceededHoursPsychologues);
        cycleValidator = new CycleValidator(Cycle.CYCLE_2023_2025);
        int exceededHoursArchitectes = CalculateMaxByHoursOrderCategoryConditions.validateArchitecteMaxHours(activities, errorHandler);
        assertEquals(0, exceededHoursArchitectes);
    }

    @Test
    void testEmptyActivities() {
        JSONArray activities = new JSONArray();
        int exceededHoursPsychologues = CalculateMaxByHoursOrderCategoryConditions.validatePsychologueMaxHours(activities, errorHandler);
        assertEquals(0, exceededHoursPsychologues);
        int exceededHoursArchitectes = CalculateMaxByHoursOrderCategoryConditions.validateArchitecteMaxHours(activities, errorHandler);
        assertEquals(0, exceededHoursArchitectes);
    }
}