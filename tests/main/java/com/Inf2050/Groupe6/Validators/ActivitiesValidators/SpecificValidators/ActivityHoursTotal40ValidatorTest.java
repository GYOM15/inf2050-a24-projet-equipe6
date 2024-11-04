package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityHoursTotal40ValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

//    @Test
//    public void testIsActivitiesTotalHoursMin40_Exact40Hours() {
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("heures", 20);
//        jsonObject1.put("description", "Activity 1");
//
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("heures", 20);
//        jsonObject2.put("description", "Activity 2");
//        // Création d'un JSONArray et ajout de l'activité
//        JSONArray activities = new JSONArray();
//        activities.add(jsonObject1);
//        activities.add(jsonObject2);
//        // Création du JSONObject final contenant le JSONArray
//        JSONObject jsonObjectFinal = new JSONObject();
//        jsonObjectFinal.put("activites", activities);
//
//        // Appel de la méthode à tester
//        ActivityHoursTotal40Validator.isActivitiesTotalHoursMin40(jsonObjectFinal, errorHandler);
//
//        // Vérification qu'il n'y a pas d'erreurs
//        assertEquals(0, errorHandler.getErrors().size());
//    }

//    @Test
//    public void testIsActivitiesTotalHoursMin40_Below40Hours() {
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("heures", 15);
//        jsonObject1.put("description", "Activity 1");
//
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("heures", 10);
//        jsonObject2.put("description", "Activity 2");
//        // Création d'un JSONArray et ajout de l'activité
//        JSONArray activities = new JSONArray();
//        activities.add(jsonObject1);
//        activities.add(jsonObject2);
//
//        // Création du JSONObject final contenant le JSONArray
//        JSONObject jsonObjectFinal = new JSONObject();
//        jsonObjectFinal.put("activites", activities);
//        // Appel de la méthode à tester
//        ActivityHoursTotal40Validator.isActivitiesTotalHoursMin40(jsonObjectFinal, errorHandler);
//
//        // Vérification qu'une erreur a été enregistrée
//        assertEquals(1, errorHandler.getErrors().size());
//        assertEquals("Erreur sur le nombre d'heures total des Activités : Il manque 15 heures pour compléter 40 heures dans le cycle.", errorHandler.getErrors().get(0));
//    }

//    @Test
//    public void testIsActivitiesTotalHoursMin40_Below1Hours() {
//        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("heures", 0);
//        jsonObject1.put("description", "Activity 1");
//
//        JSONArray activities = new JSONArray();
//        activities.add(jsonObject1);
//
//        JSONObject jsonObjectFinal = new JSONObject();
//        jsonObjectFinal.put("activites", activities);
//        // Appel de la méthode à tester
//        ActivityHoursTotal40Validator.isActivitiesTotalHoursMin40(jsonObjectFinal, errorHandler);
//
//        // Vérification qu'une erreur a été enregistrée
//        assertEquals("Erreur pour l'activité 'Activity 1': Les heures d'une catégorie ne peuvent pas être inférieures à 1", errorHandler.getErrors().get(0));
//    }

}