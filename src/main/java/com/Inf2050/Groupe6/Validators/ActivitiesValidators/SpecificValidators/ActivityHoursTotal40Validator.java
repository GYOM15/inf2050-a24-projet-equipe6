package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Cette classe est chargée de valider le nombre d'heures pour les activités.
 */
public class ActivityHoursTotal40Validator {
    /**
     * Vérifie si le nombre total d'heures des activités est au minimum 40 heures.
     *
     * @param jsonObject Le JSON contenant les activités à valider.
     */
    public static void  isActivitiesTotalHoursMin40(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        int totalHours = 0;
        for (int i = 0; i < activities.size(); i++) {
            totalHours += addActivityHours(activities.getJSONObject(i), errorHandler);
        }
        if (totalHours < 40) {
            errorHandler.addError("Erreur sur le nombnre d'heures total des Activités : Il manque " + (40 - totalHours) + " heures pour compléter 40 heures dans le cycle.");
        }
    }

    /**
     * Ajoute les heures d'une activité et vérifie si elles sont inférieures à 1
     *
     * @param activity     L'objet JSON représentant l'activité.
     * @param errorHandler Le gestionnaire d'erreurs pour enregistrer les erreurs
     * @return Le nombre d'heures de l'activité.
     */
    private static int addActivityHours(JSONObject activity, ErrorHandler errorHandler) {
        int hours = activity.getInt("heures");
        if (hours < 1) {
            errorHandler.addActivityError(activity.getString("description"), "Les heures d'une catégorie ne peuvent pas être inférieures à 1");
        }
        return hours;
    }
}
