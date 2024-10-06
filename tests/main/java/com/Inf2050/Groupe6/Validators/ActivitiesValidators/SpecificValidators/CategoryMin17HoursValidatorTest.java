package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMin17HoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testIsCategoryMin17Hours_ValidHours() {
        JSONObject jsonObject = new JSONObject();
        JSONArray activities = new JSONArray();

        JSONObject activity1 = new JSONObject();
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activities.add(activity1);

        JSONObject activity2 = new JSONObject();
        activity2.put("categorie", "atelier");
        activity2.put("heures", 10);
        activities.add(activity2);
        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 5);

        // Appel de la méthode à tester
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);

        // Vérification qu'aucune erreur n'a été enregistrée
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testIsCategoryMin17Hours_InvalidHours() {
        JSONObject jsonObject = new JSONObject();
        JSONArray activities = new JSONArray();

        JSONObject activity1 = new JSONObject();
        activity1.put("categorie", "cours");
        activity1.put("heures", 5);
        activities.add(activity1);

        JSONObject activity2 = new JSONObject();
        activity2.put("categorie", "atelier");
        activity2.put("heures", 5);
        activities.add(activity2);

        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        // Appel de la méthode à tester
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);

        // Vérification qu'une erreur a été enregistrée
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Il manque 7 heures de formation dans les catégories suivantes : cours, atelier", errorHandler.getErrors().get(0));
    }

    @Test
    public void testIsCategoryMin17Hours_ExcessivelyLowHours() {
        JSONObject jsonObject = new JSONObject();
        JSONArray activities = new JSONArray();

        JSONObject activity1 = new JSONObject();
        activity1.put("categorie", "cours");
        activity1.put("heures", 1);
        activities.add(activity1);

        JSONObject activity2 = new JSONObject();
        activity2.put("categorie", "atelier");
        activity2.put("heures", 1);
        activities.add(activity2);

        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);

        // Appel de la méthode à tester
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);

        // Vérification qu'une erreur a été enregistrée
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Il manque 15 heures de formation dans les catégories suivantes : cours, atelier", errorHandler.getErrors().get(0));
    }

    @Test
    public void testIsCategoryMin17Hours_MixedCategories() {
        JSONObject jsonObject = new JSONObject();
        JSONArray activities = new JSONArray();

        JSONObject activity1 = new JSONObject();
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activities.add(activity1);

        JSONObject activity2 = new JSONObject();
        activity2.put("categorie", "non_concerne");
        activity2.put("heures", 5);
        activities.add(activity2);

        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        // Appel de la méthode à tester
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);

        // Vérification qu'une erreur a été enregistrée
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Il manque 7 heures de formation dans les catégories suivantes : cours", errorHandler.getErrors().get(0));
    }

}