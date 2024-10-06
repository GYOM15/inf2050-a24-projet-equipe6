package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMin17HoursValidatorTest {
    private ErrorHandler errorHandler;
    private JSONObject jsonObject, activity1, activity2;
    private JSONArray activities;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        activities = new JSONArray();
        jsonObject =new JSONObject();
        activity1 = new JSONObject();
        activity2 =new JSONObject();
    }

    @Test
    public void testIsCategoryMin17Hours_ValidHours() {
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activities.add(activity1);
        activity2.put("categorie", "atelier");
        activity2.put("heures", 10);
        activities.add(activity2);
        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 5);
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testIsCategoryMin17Hours_InvalidHours() {
        activity1.put("categorie", "cours");
        activity1.put("heures", 5);
        activities.add(activity1);
        activity2.put("categorie", "atelier");
        activity2.put("heures", 5);
        activities.add(activity2);
        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Il manque 7 heures de formation dans les catégories suivantes : cours, atelier", errorHandler.getErrors().get(0));
    }

    @Test
    public void testIsCategoryMin17Hours_ExcessivelyLowHours() {
        activity1.put("categorie", "cours");
        activity1.put("heures", 1);
        activities.add(activity1);
        activity2.put("categorie", "atelier");
        activity2.put("heures", 1);
        activities.add(activity2);
        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Il manque 15 heures de formation dans les catégories suivantes : cours, atelier", errorHandler.getErrors().get(0));
    }

    @Test
    public void testIsCategoryMin17Hours_MixedCategories() {
        activity1.put("categorie", "cours");
        activity1.put("heures", 10);
        activities.add(activity1);
        activity2.put("categorie", "non_concerne");
        activity2.put("heures", 5);
        activities.add(activity2);
        jsonObject.put("activites", activities);
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        CategoryMin17HoursValidator.isCategoryMin17Hours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Il manque 7 heures de formation dans les catégories suivantes : cours", errorHandler.getErrors().get(0));
    }

}