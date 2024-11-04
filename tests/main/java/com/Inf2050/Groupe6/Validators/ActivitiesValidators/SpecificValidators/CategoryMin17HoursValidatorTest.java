package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryMin17HoursValidatorTest {

    private JSONObject jsonObject;
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        JSON json = new JSONObject();
        ErrorHandler errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidateMin17_WithSufficientHours() {
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        jsonObject.put("activites", createActivites(10,5, "cours", "atelier"));

        int totalHours =
    }

    private JSONArray createActivites(int ... hoursAndCategories) {
        JSONArray activites = new JSONArray();
        for (int i = 0; i < hoursAndCategories.length; i +=2) {
            int hours = hoursAndCategories[i];
            int category = hoursAndCategories[i+1];
            activites.add(createActivity(hours, category));
        }
        return activites;
    }

    private JSONObject createActivity(int hours, int category) {
        JSONObject activity = new JSONObject();
        activity.put("date", "2024-04-10");
        activity.put("hours", hours);
        activity.put("category", category);
        return activity;
    }
}