package GROUPE6_INF2050.Validators.HoursCalculators;


import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Validators.ValidatorsByOrderAndCycle.ActivityFilters.ActivityJsonBuilderByCategoriesConditions;
import net.sf.json.JSONArray;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Enums.ActivityCategory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateMinHoursByOrderCategoryConditions {

    // Listes des catégories d'activités spécifiques pour chaque ordre
    private static final List<String> LIST_GEOLOGUE = Arrays.asList(
            ActivityCategory.COURS.getCategoryFromJsonObj(),
            ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(),
            ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj()
    );

    private static final List<String> LIST_PSYCHOLOGUE = List.of(
            ActivityCategory.COURS.getCategoryFromJsonObj()
    );

    private static final List<String> LIST_ARCHITECTES = Arrays.asList(
            ActivityCategory.COURS.getCategoryFromJsonObj(),
            ActivityCategory.ATELIER.getCategoryFromJsonObj(),
            ActivityCategory.SEMINAIRE.getCategoryFromJsonObj(),
            ActivityCategory.COLLOQUE.getCategoryFromJsonObj(),
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj(),
            ActivityCategory.LECTURE_DIRIGEE.getCategoryFromJsonObj()
    );

    /**
     * Calcule le nombre total d'heures par catégorie, en fonction de l'ordre.
     *
     * @param order L'ordre d'activité (GEOLOGUES, PSYCHOLOGUES, ARCHITECTES)
     * @param activities Un tableau JSON des activités à évaluer
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs de validation
     * @return La somme des heures validées par rapport aux limites minimales pour chaque catégorie
     */
    public static int calculateMinHoursByCategoryConditions(ActivityOrder order, JSONArray activities, ErrorHandler errorHandler) {
        ValidationConfigForCategoriesWithMinHours config = createValidationConfig(order);
        if (config == null) {
            return 0;
        }
        return validateHoursForOrder(activities, errorHandler, config);
    }

    /**
     * Crée une configuration de validation des heures minimales par ordre.
     *
     * @param order L'ordre d'activité (GEOLOGUES, PSYCHOLOGUES, ARCHITECTES)
     * @return Un objet ValidationConfig contenant les heures minimales par catégorie et la liste de catégories à valider
     */
    private static ValidationConfigForCategoriesWithMinHours createValidationConfig(ActivityOrder order) {
        return switch (order) {
            case GEOLOGUES -> new ValidationConfigForCategoriesWithMinHours(Map.of("cours", 22, "projets de recherche", 3, "groupe de discussion", 1), LIST_GEOLOGUE);
            case PSYCHOLOGUES -> new ValidationConfigForCategoriesWithMinHours(Map.of("cours", 25), LIST_PSYCHOLOGUE);
            case ARCHITECTES -> new ValidationConfigForCategoriesWithMinHours(Map.of("cours", 17), LIST_ARCHITECTES);
            default -> null;
        };
    }

    /**
     * Valide les heures par catégorie en fonction de la configuration, pour un ordre donné.
     *
     * @param activities   Liste JSON des activités
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs de validation
     * @param config       Configuration contenant les limites d'heures minimales et les catégories à valider
     * @return La somme des heures validées par rapport aux limites minimales pour chaque catégorie
     */
    private static int validateHoursForOrder(JSONArray activities, ErrorHandler errorHandler, ValidationConfigForCategoriesWithMinHours config) {
        ActivityJsonBuilderByCategoriesConditions.filterByCategorieCondition(activities, config.categoryList());
        Map<String, Integer> actualHoursByCategory = createActualHoursByCategory();
        checkAndReportMissingHours(errorHandler, config.minHoursByCategory(), actualHoursByCategory);
        return actualHoursByCategory.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Calcule le nombre d'heures total pour chaque catégorie filtrée et les regroupe dans une map.
     *
     * @return Une map contenant le nombre d'heures calculé pour chaque catégorie
     */
    private static Map<String, Integer> createActualHoursByCategory() {
        Map<String, Integer> actualHoursByCategory = new HashMap<>();
        actualHoursByCategory.put("cours", ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getCoursJsonObject(), null));
        actualHoursByCategory.put("projets de recherche", ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getResearchProjectJsonObject(), null));
        actualHoursByCategory.put("groupe de discussion", ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getDiscussionGroupJsonObject(), null));
        return actualHoursByCategory;
    }

    /**
     * Vérifie que les heures calculées par catégorie atteignent le minimum requis et enregistre les erreurs si nécessaire.
     *
     * @param errorHandler         Gestionnaire d'erreurs pour enregistrer les erreurs de validation
     * @param minHoursByCategory   Map des heures minimales requises par catégorie
     * @param actualHoursByCategory Map des heures calculées pour chaque catégorie
     */
    private static void checkAndReportMissingHours(ErrorHandler errorHandler, Map<String, Integer> minHoursByCategory, Map<String, Integer> actualHoursByCategory) {
        for (Map.Entry<String, Integer> entry : minHoursByCategory.entrySet()) {
            String categoryName = entry.getKey();
            int minHours = entry.getValue();
            int actualHours = actualHoursByCategory.getOrDefault(categoryName, 0);
            if (actualHours < minHours) {
                ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (minHours - actualHours) + " heures dans la catégorie " + categoryName + ". Le minimum autorisé est de " + minHours + "h.");
            }
        }
    }
}