package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Enums.ActivityCategory;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.ArrayList;
import java.util.List;

public class CategoryMin17HoursValidator {

    /**
     * Vérifie si le nombre total d'heures des catégoties précisés ci-dessous est inférieur à 17
     * et on stocke les catégories qui ne vérifie pas cette condition dans un tableau
     * @param jsonObject Le JSON contenant les catégories concernées
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs si le total des heures est en dessous de 17
     */
    public static void isCategoryMin17Hours(JSONObject jsonObject, ErrorHandler errorHandler) {
        int requiredHoursTotal = TransferredHoursValidator.validateTransferredHours(jsonObject, null);
        List<String> concernedCategories = new ArrayList<>();
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        requiredHoursTotal = calculateRequiredHours(activities, concernedCategories, requiredHoursTotal);
        handleCategoryErrorIfInvalid(errorHandler, concernedCategories, requiredHoursTotal);
    }

    /**
     * Calcule le nombre total d'heures requis pour les catégories spécifiques.
     *
     * @param activities Le tableau JSON des activités.
     * @param concernedCategories La liste des catégories concernées par la validation.
     * @param requiredHoursTotal Le nombre total d'heures requis (initialisé à partir des heures transférées).
     * @return Le nombre total d'heures pour les catégories valides.
     */
    private static int calculateRequiredHours(JSONArray activities, List<String> concernedCategories, int requiredHoursTotal) {
        for (int i = 0; i < activities.size(); i++) {
            String category = activities.getJSONObject(i).getString("categorie");
            if (isValidCategory(category)) {
                requiredHoursTotal += activities.getJSONObject(i).getInt("heures");
                addCategory(concernedCategories, category);
            }
        }
        return requiredHoursTotal;
    }

    /**
     * Vérifie si une catégorie donnée est valide en fonction de l'énumération des catégories autorisées.
     *
     * @param category La catégorie de l'activité à vérifier.
     * @return true si la catégorie est valide, false sinon.
     */
    private static boolean isValidCategory(String category) {
        return switch (ActivityCategory.searchFromJsonCategory(category, null)) {
            case COURS, ATELIER, SEMINAIRE, COLLOQUE, CONFERENCE, LECTURE_DIRIGEE -> true;
            default -> false;
        };
    }

    /**
     * Ajoute une catégorie à la liste des catégories concernées si elle n'est pas déjà présente.
     *
     * @param concernedCategories La liste des catégories concernées.
     * @param category La catégorie à ajouter.
     */
    private static void addCategory(List<String> concernedCategories, String category) {
        if (!concernedCategories.contains(category)) {
            concernedCategories.add(category);
        }
    }

    /**
     * Enregistre une erreur dans l'ErrorHandler si le total d'heures pour les catégories concernées
     * est inférieur à 17 heures.
     *
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs.
     * @param concernedCategories La liste des catégories concernées.
     * @param totalHours Le total des heures calculées pour ces catégories.
     */
    private static void handleCategoryErrorIfInvalid(ErrorHandler errorHandler, List<String> concernedCategories, int totalHours) {
        final int MIN_HOURS_REQUIRED = 17;
        if (totalHours < MIN_HOURS_REQUIRED) {
            String categoriesList = String.join(", ", concernedCategories);
            errorHandler.addError("Il manque " + (MIN_HOURS_REQUIRED - totalHours) +
                    " heures de formation dans les catégories suivantes : " + categoriesList);
        }
    }

}
