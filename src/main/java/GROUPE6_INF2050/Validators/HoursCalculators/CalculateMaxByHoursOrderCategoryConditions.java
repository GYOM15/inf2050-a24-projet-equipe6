package GROUPE6_INF2050.Validators.HoursCalculators;

import GROUPE6_INF2050.Validators.Generics.Calculator.ActivityHoursCalculator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.Generics.ActivityFilter.ActivityJsonBuilderByCategoriesConditions;

import java.util.List;
import java.util.Map;


/**
 * Classe utilitaire pour calculer les heures maximales autorisées pour différentes catégories d'activités,
 * en fonction des ordres professionnels et des limites spécifiques, centralise la logique de validation des heures maximales et permet de gérer
 * les dépassements pour des catégories d'activités spécifiques.
 */
public class CalculateMaxByHoursOrderCategoryConditions {

    /**
     * Limites maximales d'heures pour chaque catégorie d'activité.
     */
    private static final Map<String, Integer> MAX_HOURS_LIMITS = Map.of(
            ActivityCategory.PRESENTATION.getCategoryFromJsonObj(), 23,
            ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj(), 17,
            ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(), 23,
            ActivityCategory.REDACTION_PROFESSIONNELLE.getCategoryFromJsonObj(), 17,
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj(), 15
    );

    private static final List<String> LIST_PSYCHOLOGUE = List.of(
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj()
    );

    private static final List<String> LIST_ARCHITECTES = List.of(
            ActivityCategory.PRESENTATION.getCategoryFromJsonObj(),
            ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj(),
            ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(),
            ActivityCategory.REDACTION_PROFESSIONNELLE.getCategoryFromJsonObj()
    );

    /**
     * Valide et retourne les heures dépassant les limites pour un psychologue.
     *
     * @param activities   Tableau JSON des activités à valider.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @return Total des heures excédant les limites.
     */
    public static int validatePsychologueMaxHours(JSONArray activities, ErrorHandler errorHandler) {
        return validateMaxHours(activities, LIST_PSYCHOLOGUE, errorHandler);
    }

    /**
     * Valide et retourne les heures dépassant les limites pour un architecte.
     *
     * @param activities   Tableau JSON des activités à valider.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @return Total des heures excédant les limites.
     */
    public static int validateArchitecteMaxHours(JSONArray activities, ErrorHandler errorHandler) {
        return validateMaxHours(activities, LIST_ARCHITECTES, errorHandler);
    }


    /**
     * Valide et retourne les heures dépassant les limites pour une liste de catégories d'activités.
     *
     * @param activities   Tableau JSON des activités à valider.
     * @param categories   Liste des catégories à vérifier.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @return Total des heures excédant les limites.
     */
    private static int validateMaxHours(JSONArray activities, List<String> categories, ErrorHandler errorHandler) {
        ActivityJsonBuilderByCategoriesConditions builder = new ActivityJsonBuilderByCategoriesConditions();
        builder.filterByCategorieCondition(activities, categories);
        return categories.stream()
                .mapToInt(category -> {
                    JSONObject categoryJsonObject = buildCategoryJsonObject(builder, category);
                    int totalHours = ActivityHoursCalculator.getTotalHours(categoryJsonObject, errorHandler);
                    return applyMaxLimit(totalHours, category);
                }).sum();
    }


    /**
     * Construit un objet JSON pour une catégorie spécifique.
     *
     * @param builder  Filtreur de catégories pour extraire les activités correspondantes.
     * @param category Catégorie pour laquelle le JSON doit être construit.
     * @return Un objet JSON contenant les activités de la catégorie donnée.
     */
    private static JSONObject buildCategoryJsonObject(ActivityJsonBuilderByCategoriesConditions builder, String category) {
        JSONArray categoryActivities = builder.getActivitiesByCategory(category);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", categoryActivities);
        return jsonObject;
    }

    /**
     * Applique la limite maximale d'heures pour une catégorie spécifique.
     *
     * @param hours    Total des heures calculées.
     * @param category Catégorie pour laquelle appliquer la limite.
     * @return Heures excédant la limite (ou zéro si aucune limite n'est dépassée).
     */
    private static int applyMaxLimit(int hours, String category) {
        Integer maxHours = MAX_HOURS_LIMITS.get(category);
        return (maxHours != null && hours > maxHours) ? hours - maxHours : 0;
    }
}