package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.DateValidator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityHoursCalculator {

    private static final int MAX_HOURS_PER_DAY = 10;

    static class Activity {
        String date;
        int heures;

        Activity(JSONObject activityJson) {
            this.date = activityJson.getString("date");
            this.heures = activityJson.getInt("heures");
        }
    }

    private static void addHours(Map<String, Integer> hoursByDate, Activity activity, ErrorHandler errorHandler) {
        if (!DateValidator.validate(activity.date, errorHandler)) {
            errorHandler.addError("La date " + activity.date + " est invalide. Les heures ne seront pas ajoutées.");
            return; // Si la date est invalide, on sort de la méthode sans ajouter d'heures
        }

        int newTotal = hoursByDate.getOrDefault(activity.date, 0) + activity.heures;
        if (newTotal > MAX_HOURS_PER_DAY) {
            errorHandler.addError("La somme des heures pour la date " + activity.date + " dépasse 10. Seulement 10 heures seront comptabilisées.");
            newTotal = MAX_HOURS_PER_DAY;
        }
        hoursByDate.put(activity.date, newTotal);
    }

    private static int calcul(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = jsonObject.getJSONArray("activites");
        Map<String, Integer> hoursByDate = new HashMap<>();
        activities.forEach(obj -> addHours(hoursByDate, new Activity((JSONObject) obj), errorHandler));
        return hoursByDate.values().stream().mapToInt(Integer::intValue).sum();
    }

    public static int getTotalHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        int heureInf17 = CategoryMin17HoursValidator.calculateAndValidateMin17Hours(jsonObject,errorHandler);
        if (heureInf17 < 17) {
            return calcul(jsonObject, errorHandler) + jsonObject.optInt("heures_transferees_du_cycle_precedent");
        }
        return calcul(jsonObject, errorHandler);
    }
}