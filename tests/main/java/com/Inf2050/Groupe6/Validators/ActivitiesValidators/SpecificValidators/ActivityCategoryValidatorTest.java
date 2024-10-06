package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCategoryValidatorTest {
    private ErrorHandler errorHandler;
    private JSONObject activity, jsonObject;
    private JSONArray activities;

    @BeforeEach
    void setUp(){
        errorHandler =new ErrorHandler();
        activity = new JSONObject();
        activities = new JSONArray();
        jsonObject = new JSONObject();
    }

    @Test
    public void testValidateCategory_ValidCategory() {
        activity.put("categorie", "cours");
        activities.add(activity);
        jsonObject.put("activites", activities.toString());
        ActivityCategoryValidator.validateCategory(jsonObject, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateCategory_InvalidCategory() {
        activity.put("categorie", "invalidCategory");
        activities.add(activity);
        jsonObject.put("activites", activities.toString());
        ActivityCategoryValidator.validateCategory(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("La catégorie invalidCategory n'est pas valide", errorHandler.getErrors().get(0));  // Assurez-vous que le message correspond
    }
}