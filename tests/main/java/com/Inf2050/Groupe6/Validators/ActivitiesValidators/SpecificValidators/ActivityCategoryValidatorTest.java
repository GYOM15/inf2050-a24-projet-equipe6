package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityCategoryValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp(){
        errorHandler =new ErrorHandler();
    }

    @Test
    public void testValidateCategory_ValidCategory() {
        // Création d'un JSON avec une catégorie valide
        JSONObject jsonObject = new JSONObject();
        JSONArray activities = new JSONArray();
        JSONObject activity = new JSONObject();
        activity.put("categorie", "cours");
        activities.add(activity);
        jsonObject.put("activites", activities.toString());

        // Appel de la méthode à tester
        ActivityCategoryValidator.validateCategory(jsonObject, errorHandler);

        // Vérification qu'il n'y a pas d'erreurs
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateCategory_InvalidCategory() {
        // Création d'un JSON avec une catégorie invalide
        JSONObject jsonObject = new JSONObject();
        JSONArray activities = new JSONArray();
        JSONObject activity = new JSONObject();
        activity.put("categorie", "invalidCategory");  // Assurez-vous que "invalidCategory" est invalide
        activities.add(activity);
        jsonObject.put("activites", activities.toString());

        // Appel de la méthode à tester
        ActivityCategoryValidator.validateCategory(jsonObject, errorHandler);

        // Vérification qu'une erreur a été enregistrée
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("La catégorie invalidCategory n'est pas valide", errorHandler.getErrors().get(0));  // Assurez-vous que le message correspond
    }
}