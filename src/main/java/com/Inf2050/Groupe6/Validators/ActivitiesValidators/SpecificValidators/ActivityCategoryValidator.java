package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Enums.ActivityCategory;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Cette classe est chargée de valider les catégories des activités.
 */
public class ActivityCategoryValidator {
    /**
     * Vérifie si la catégorie du fichier JSON correspond à l'une des catégories autorisées.
     *
     * @param jsonObject Le JSON contenant les catégories des activités à valider.
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs de validation des catégories.
     */
    public static void validateCategory(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        for (int i = 0; i < activities.size(); i++) {
            String category = activities.getJSONObject(i).getString("categorie");
            ActivityCategory.searchFromJsonCategory(category, errorHandler);
        }
    }
}
