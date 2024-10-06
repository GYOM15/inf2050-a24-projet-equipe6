package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Enums.ActivityCategory;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Cette classe est chargée de valider les heures pour les catégories spécifiques.
 */
public class CategoryMaxHoursValidator {

    /**
     * Valide le nombre d'heures pour une catégorie spécifique.
     *
     * @param jsonObject Le JSON contenant les informations de l'activité.
     * @param concernedCategory La catégorie à valider.
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs.
     */
    private static void validateHoursForCategory(JSONObject jsonObject, ActivityCategory concernedCategory, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        for (int i = 0; i < activities.size(); i++) {
            String category = activities.getJSONObject(i).getString("categorie");
            if (ActivityCategory.searchFromJsonCategory(category, null).equals(concernedCategory)
                    && activities.getJSONObject(i).getInt("heures") > concernedCategory.getMaxHours()) {
                errorHandler.addError("Le nombre d'heures pour la catégorie " + category + " dépasse le maximum autorisé.");
            }
        }
    }

    /** Valide le nombre d'heures pour la catégorie présentation. */
    public static void validatePresentationHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        validateHoursForCategory(jsonObject, ActivityCategory.PRESENTATION, errorHandler);
    }

    /** Valide le nombre d'heures pour la catégorie groupe de discussion. */
    public static void validateGroupDiscussionHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        validateHoursForCategory(jsonObject, ActivityCategory.GROUPE_DE_DISCUSSION, errorHandler);
    }

    /** Valide le nombre d'heures pour la catégorie projet de recherche. */
    public static void validateProjetDeRechercheHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        validateHoursForCategory(jsonObject, ActivityCategory.PROJET_DE_RECHERCHE, errorHandler);
    }

    /** Valide le nombre d'heures pour la catégorie rédaction professionnelle. */
    public static void validateRedactionProHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        validateHoursForCategory(jsonObject, ActivityCategory.REDACTION_PROFESSIONNELLE, errorHandler);
    }
}
