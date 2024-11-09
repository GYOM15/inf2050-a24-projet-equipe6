package main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Validators.CycleValidator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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
        if (!CycleValidator.isDateWithinCycle(activity.date, errorHandler)) {
            errorHandler.addError("La date " + activity.date + " est invalide. Les heures ne seront pas ajoutées.");
            return;
        }
        int newTotal = hoursByDate.getOrDefault(activity.date, 0) + activity.heures;
        if (newTotal > MAX_HOURS_PER_DAY) {
            if (errorHandler != null) {
                errorHandler.addError("La somme des heures pour la date " + activity.date + " dépasse 10. Seulement 10 heures seront comptabilisées.");
            }
            newTotal = MAX_HOURS_PER_DAY;
        }
        hoursByDate.put(activity.date, newTotal);
    }

    private static int calcul(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        Map<String, Integer> hoursByDate = new HashMap<>();
        activities.forEach(obj -> addHours(hoursByDate, new Activity((JSONObject) obj), errorHandler));
        return hoursByDate.values().stream().mapToInt(Integer::intValue).sum();
    }

    public static int getTotalHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        return calcul(jsonObject, errorHandler);
    }
}