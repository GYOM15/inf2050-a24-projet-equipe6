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

    @BeforeEach
    void setUp(){
        errorHandler= new ErrorHandler();
    }

    @Test
    void testValidateCycle_ValidCycle() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cycle", "2023-2025");

        CycleValidator.validateCycle(jsonObject, errorHandler);

        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidateCycle_InvalidCycle() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cycle", "2022-2024");

        CycleValidator.validateCycle(jsonObject, errorHandler);

        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Erreur pour le cycle '"+jsonObject.getString("cycle")+"': "+"Le cycle n'est pas supporté. Seul le cycle 2023-2025 est valide.", errorHandler.getErrors().get(0));
    }

    @Test
    void testCheckIfActivityDateInCycle_ValidDate() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date","2024-01-15");
        jsonObject.put("description", "Activity 1");
        JSONArray activites =new JSONArray();
        activites.add(jsonObject);
        JSONObject jsonObjectFinal =new JSONObject();
        jsonObjectFinal.put("activites", activites);
        CycleValidator.checkIfActivityDateInCycle(jsonObjectFinal, errorHandler);

        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testCheckIfActivityDateInCycle_InvalidDate() {
        // Création d'un JSONObject pour l'activité
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", "2022-05-01");
        jsonObject.put("description", "Activity 1");

        // Création d'un JSONArray et ajout de l'activité
        JSONArray activities = new JSONArray();
        activities.add(jsonObject);  // Utilise 'put' au lieu de 'add'

        // Création du JSONObject final contenant le JSONArray
        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        // Appel de la méthode à tester
        CycleValidator.checkIfActivityDateInCycle(jsonObjectFinal, errorHandler);

        // Vérification des résultats
        assertEquals(1, errorHandler.getErrors().size());
        String expectedError = "Erreur pour l'activité 'Activity 1': La date 2022-05-01 doit être entre le cycle '2023-04-01' et '2025-04-01'.";
        assertEquals(expectedError, errorHandler.getErrors().get(0));
    }

    @Test
    void testCheckIfActivityDateInCycle_InvalidDateFormat() {
        // Création d'un JSONObject pour l'activité
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", "invalid-date");
        jsonObject.put("description", "Activity 1");

        // Création d'un JSONArray et ajout de l'activité
        JSONArray activities = new JSONArray();
        activities.add(jsonObject);  // Utilise 'put' au lieu de 'add'

        // Création du JSONObject final contenant le JSONArray
        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CycleValidator.checkIfActivityDateInCycle(jsonObjectFinal, errorHandler);

        assertEquals(1, errorHandler.getErrors().size());
        String expectedError = "Erreur pour l'activité 'Activity 1': Date d'activité invalide : invalid-date";
        assertEquals(expectedError, errorHandler.getErrors().get(0));
    }
}