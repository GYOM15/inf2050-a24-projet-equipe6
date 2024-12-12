package GROUPE6_INF2050.Validators.Generics.Calculator;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.CycleValidator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

public class ActivityHoursCalculator {
    private static final int MAX_HOURS_PER_DAY = 10;

    /**
     * Classe interne représentant une activité avec une date et un nombre d'heures.
     */
    static class Activity {
        String date;
        int heures;
        String categorie;

        Activity(JSONObject activityJson) {
            this.date = activityJson.getString("date");
            this.heures = activityJson.getInt("heures");
            this.categorie = activityJson.optString("categorie");
        }
    }

    /**
     * Ajoute un message d'erreur au ErrorHandler s'il n'est pas nul.
     *
     * @param errorHandler L'instance ErrorHandler où ajouter le message d'erreur, si non nul
     * @param errorMessage Le message d'erreur à ajouter
     */
    private static void addErrorIfNotNull(ErrorHandler errorHandler, String errorMessage) {
        ErrorHandler.addErrorIfNotNull(errorHandler, errorMessage);
    }

    /**
     * Ajoute les heures d'une activité au total pour une date donnée, avec des vérifications sur le cycle et le maximum journalier.
     *
     * @param hoursByDate Map stockant le total d'heures par date
     * @param activity L'activité à ajouter
     * @param errorHandler Gestionnaire d'erreurs pour signaler les erreurs
     */
    private static void addHours(Map<String, Integer> hoursByDate, Activity activity, ErrorHandler errorHandler) {
        if (CycleValidator.isDateWithinCycle(activity.date, errorHandler)) {
            addErrorIfNotNull(errorHandler, "La date " + activity.date + " est invalide. Les heures ne seront pas ajoutées.");
            return;
        } int newTotal = hoursByDate.getOrDefault(activity.date, 0) + activity.heures;
        if (newTotal > MAX_HOURS_PER_DAY) {
            addErrorIfNotNull(errorHandler, "La somme des heures pour la date " + activity.date + " dépasse 10. Seulement 10 heures seront comptabilisées.");
            newTotal = MAX_HOURS_PER_DAY;
        } hoursByDate.put(activity.date, newTotal);
    }

    /**
     * Calcule le nombre total d'heures pour toutes les activités dans un objet JSON donné, en respectant les règles de validation.
     *
     * @param jsonObject L'objet JSON contenant les activités
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return Le nombre total d'heures validées
     */
    private static int calcul(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        Map<String, Integer> hoursByDate = new HashMap<>();
        checkIfValidCategory(errorHandler, activities, hoursByDate);
        return hoursByDate.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static void checkIfValidCategory(ErrorHandler errorHandler, JSONArray activities, Map<String, Integer> hoursByDate) {
        for (Object obj : activities) {
            JSONObject activityJson = (JSONObject) obj;
            Activity activity = new Activity(activityJson);
            // Vérifier si la catégorie est valide
            ActivityCategory category = ActivityCategory.searchFromJsonCategory(activity.categorie, errorHandler);
            if (category != ActivityCategory.CATEGORIE_NON_VALIDE) {
                addHours(hoursByDate, activity, errorHandler);
            }
        }
    }

    /**
     * Méthode publique pour obtenir le nombre total d'heures d'activités validées.
     *
     * @param jsonObject L'objet JSON contenant les activités
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return Le nombre total d'heures validées
     */
    public static int getTotalHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        return calcul(jsonObject, errorHandler);
    }
}