package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CycleValidatorTest {

    private ErrorHandler errorHandler;
    private JSONObject jsonObject, activity;
    private JSONArray activities;

    @BeforeEach
    void setUp(){
        errorHandler = new ErrorHandler();
        jsonObject = new JSONObject();
        activity = new JSONObject();
        activities = new JSONArray();
    }

    @Test
    void testValidateCycle_ValidCycle() {
        jsonObject.put("cycle", "2023-2025");
        CycleValidator.validateCycle(jsonObject, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidateCycle_InvalidCycle() {
        jsonObject.put("cycle", "2022-2024");
        CycleValidator.validateCycle(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Erreur pour le cycle '"+jsonObject.getString("cycle")+"': "+"Le cycle n'est pas supporté. Seul le cycle 2023-2025 est valide.", errorHandler.getErrors().get(0));
    }

    @Test
    void testCheckIfActivityDateInCycle_ValidDate() {
        activity.put("date","2024-01-15");
        activity.put("description", "Activity 1");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CycleValidator.checkIfActivityDateInCycle(jsonObject, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testCheckIfActivityDateInCycle_InvalidDate() {
        activity.put("date", "2022-05-01");
        activity.put("description", "Activity 1");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CycleValidator.checkIfActivityDateInCycle(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        String expectedError = "Erreur pour l'activité 'Activity 1': La date 2022-05-01 doit être entre le cycle '2023-04-01' et '2025-04-01'.";
        assertEquals(expectedError, errorHandler.getErrors().get(0));
    }

    @Test
    void testCheckIfActivityDateInCycle_InvalidDateFormat() {
        activity.put("date", "invalid-date");
        activity.put("description", "Activity 1");
        JSONArray activities = new JSONArray();
        activities.add(activity);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        CycleValidator.checkIfActivityDateInCycle(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        String expectedError = "Erreur pour l'activité 'Activity 1': Date d'activité invalide : invalid-date";
        assertEquals(expectedError, errorHandler.getErrors().get(0));
    }
}