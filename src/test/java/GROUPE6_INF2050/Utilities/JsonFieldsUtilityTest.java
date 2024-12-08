//package GROUPE6_INF2050.Utilities;
//
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import GROUPE6_INF2050.Handlers.ErrorHandler;
//import GROUPE6_INF2050.Validators.GeneralsRulesValidators.JsonFieldsValidatorRule;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class JsonFieldsUtilityTest {
//
//    private ErrorHandler errorHandler;
//    private JSONObject jsonObject;
//    private JSONObject activity;
//
//    @BeforeEach
//    void setUp() {
//        errorHandler = new ErrorHandler();
//        jsonObject = new JSONObject();
//        activity = new JSONObject();
//    }
//
//    @AfterEach
//    void clean(){
//        errorHandler = new ErrorHandler();
//        jsonObject = new JSONObject();
//        activity = new JSONObject();
//    }
//
//    @Test
//    void testCheckJsonFields_ValidJson() {
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("ordre", "architectes");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 1);
//        jsonObject.put("activites", new JSONArray());
//
//        boolean result = JsonFieldsValidatorRule.checkJsonFields(jsonObject, errorHandler);
//        assertTrue(result);
//
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }
//
//    @Test
//    void testCheckJsonFields_MissingRequiredKey() {
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("ordre", "architectes");
//
//        assertFalse(JsonFieldsValidatorRule.checkJsonFields(jsonObject, errorHandler));
//
//        assertEquals(1, errorHandler.getErrors().size());
//        assertTrue(errorHandler.getErrors().contains("La clé : activites est manquante dans le fichier JSON"));
//    }
//
//    @Test
//    void testCheckArchitecteSpecificKey_HeuresTransfereesValid() {
//        jsonObject.put("ordre", "architectes");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 10);
//        jsonObject.put("activites", new JSONArray());
//
//        assertTrue(JsonFieldsValidatorRule.checkJsonFields(jsonObject, errorHandler));
//
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }
//
//    @Test
//    void testCheckArchitecteSpecificKey_HeuresTransfereesInvalid() {
//        jsonObject.put("ordre", "geologues");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 1);
//        jsonObject.put("activites", new JSONArray());
//
//        assertFalse(JsonFieldsValidatorRule.checkJsonFields(jsonObject, errorHandler));
//
//        assertEquals(1, errorHandler.getErrors().size());
//        assertTrue(errorHandler.getErrors().contains("La clé 'heures_transferees_du_cycle_precedent' ne doit exister que pour l'ordre architectes."));
//    }
//
//    @Test
//    void testValidateCycleByOrder_ValidCycle() {
//        jsonObject.put("ordre", "architectes");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 1);
//        jsonObject.put("activites", new JSONArray());
//
//        JsonFieldsValidatorRule.checkJsonFields(jsonObject, errorHandler);
//
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }
//
//    @Test
//    void testValidateCycleByOrder_InvalidCycle() {
//        jsonObject.put("ordre", "architectes");
//        jsonObject.put("cycle", "2014-2022");
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 1);
//        jsonObject.put("activites", new JSONArray());
//
//        JsonFieldsValidatorRule.checkJsonFields(jsonObject, errorHandler);
//
//        assertTrue(errorHandler.getErrors().contains("Le cycle 2014-2022 n'est pas valide pour l'ordre architectes"));
//    }
//
//    @Test
//    void testCheckActivitiesFields_ValidActivities() {
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("ordre", "architectes");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 1);
//        jsonObject.put("activites", new JSONArray());
//
//        activity.put("description", "activité A");
//        activity.put("categorie", "cours");
//        activity.put("heures", 10);
//        activity.put("date", "2024-01-01");
//
//        jsonObject.getJSONArray("activites").add(activity);
//
//        assertTrue(JsonFieldsValidatorRule.areAllFieldsValid(jsonObject, errorHandler));
//
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }
//
//    @Test
//    void testCheckActivitiesFields_MissingActivityKey() {
//        jsonObject.put("numero_de_permis", "12345");
//        jsonObject.put("cycle", "2023-2025");
//        jsonObject.put("ordre", "architectes");
//        jsonObject.put("heures_transferees_du_cycle_precedent", 1);
//        jsonObject.put("activites", new JSONArray());
//
//        activity.put("description", "activité A");
//        activity.put("categorie", "cours");
//        activity.put("date", "2024-01-01");
//
//        jsonObject.getJSONArray("activites").add(activity);
//
//        assertFalse(JsonFieldsValidatorRule.areAllFieldsValid(jsonObject, errorHandler));
//
//        assertEquals(1, errorHandler.getErrors().size());
//        assertTrue(errorHandler.getErrors().contains("La clé : heures est manquante pour l'activité 0"));
//    }
//
//}