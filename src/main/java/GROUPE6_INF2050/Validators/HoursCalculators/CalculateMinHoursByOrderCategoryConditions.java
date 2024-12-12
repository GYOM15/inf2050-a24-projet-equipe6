package GROUPE6_INF2050.Validators.HoursCalculators;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.Generics.ActivityFilter.ActivityJsonBuilderByCategoriesConditions;

import GROUPE6_INF2050.Validators.Generics.Calculator.ActivityHoursCalculator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilitaire pour valider les heures minimales requises par ordre et catégorie d'activité,
 * centralise les validations spécifiques pour différents ordres professionnels,
 * en vérifiant si les activités soumises respectent les heures minimales requises pour certaines catégories.
 */
public class CalculateMinHoursByOrderCategoryConditions {

    private static final List<String> architectCategories = List.of(
            ActivityCategory.COURS.getCategoryFromJsonObj(),
            ActivityCategory.ATELIER.getCategoryFromJsonObj(),
            ActivityCategory.SEMINAIRE.getCategoryFromJsonObj(),
            ActivityCategory.COLLOQUE.getCategoryFromJsonObj(),
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj(),
            ActivityCategory.LECTURE_DIRIGEE.getCategoryFromJsonObj()
    );

    /**
     * Valide les heures minimales requises pour un ordre donné.
     *
     * @param obj          Objet JSON contenant les activités à valider.
     * @param order        Ordre professionnel pour lequel la validation doit être effectuée.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @return Le total des heures valides trouvées.
     */
    public static int validateMinimumHours(JsonFileUtility obj, ActivityOrder order, ErrorHandler errorHandler) {
        switch (order) {
            case GEOLOGUES, PODIATRES -> validateGeologueAndPodiatreHours(obj, errorHandler);
            case ARCHITECTES -> validateArchitecteHours(obj, errorHandler);
            case PSYCHOLOGUES -> validatePsychologueHours(obj, errorHandler);
        }
        return 0;
    }

    /**
     * Valide les heures minimales pour les géologues et podiatres.
     *
     * @param obj          Objet JSON contenant les activités à valider.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     */
    private static void validateGeologueAndPodiatreHours(JsonFileUtility obj, ErrorHandler errorHandler) {
        validateCategories(obj, errorHandler, List.of(
                new CategoryRequirement(ActivityCategory.COURS.getCategoryFromJsonObj(), 22),
                new CategoryRequirement(ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(), 3),
                new CategoryRequirement(ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj(), 1)

        ));
    }

    /**
     * Valide les heures minimales pour les architectes.
     *
     * @param obj          Objet JSON contenant les activités à valider.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     */
    private static void validateArchitecteHours(JsonFileUtility obj, ErrorHandler errorHandler) {
        int totalHours = validateCategories(obj, errorHandler, architectCategories.stream()
                .map(category -> new CategoryRequirement(category, 17))
                .collect(Collectors.toList()));
        handleArchitecteErrors(totalHours, errorHandler);
    }


    /**
     * Valide les heures minimales pour les psychologues.
     *
     * @param obj          Objet JSON contenant les activités à valider.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     */
    private static void validatePsychologueHours(JsonFileUtility obj, ErrorHandler errorHandler) {
        validateCategories(obj, errorHandler, List.of(
                new CategoryRequirement(ActivityCategory.COURS.getCategoryFromJsonObj(), 25)
        ));
    }

    /**
     * Gère les erreurs spécifiques liées aux heures minimales pour les architectes.
     *
     * @param totalHours   Total des heures valides trouvées.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     */
    private static void handleArchitecteErrors(int totalHours, ErrorHandler errorHandler) {
        if (totalHours < 17) {
            String categories = String.join(", ", architectCategories);
            ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (17 - totalHours) +
                    " heures dans les catégories : " + categories + ". Le minimum autorisé est de 17h.");
        }
    }

    /**
     * Valide les heures par catégories d'activités et enregistre les erreurs si nécessaire.
     *
     * @param obj          Objet JSON contenant les activités à valider.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @param requirements Liste des catégories avec leurs heures minimales requises.
     * @return Total des heures valides trouvées.
     */
    private static int validateCategories(JsonFileUtility obj, ErrorHandler errorHandler, List<CategoryRequirement> requirements) {
        ActivityJsonBuilderByCategoriesConditions builder = new ActivityJsonBuilderByCategoriesConditions();
        List<String> categories = extractCategories(requirements);
        builder.filterByCategorieCondition(obj.getJsonArray(), categories);
        return calculateTotalHours(obj, errorHandler, requirements, builder);
    }


    /**
     * Extrait les catégories des exigences fournies.
     *
     * @param requirements Liste des exigences contenant les catégories et leurs heures minimales.
     * @return Liste des catégories extraites.
     */
    private static List<String> extractCategories(List<CategoryRequirement> requirements) {
        return requirements.stream()
                .map(CategoryRequirement::category)
                .collect(Collectors.toList());
    }

    /**
     * Calcule le total des heures valides en fonction des catégories et exigences.
     *
     * @param obj          Objet JSON contenant les activités.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @param requirements Liste des exigences contenant les catégories et leurs heures minimales.
     * @param builder      Constructeur JSON pour filtrer les catégories.
     * @return Total des heures valides trouvées.
     */
    private static int calculateTotalHours(JsonFileUtility obj, ErrorHandler errorHandler, List<CategoryRequirement> requirements, ActivityJsonBuilderByCategoriesConditions builder) {
        int totalHours = 0;
        for (CategoryRequirement requirement : requirements) {
            JSONArray activities = builder.getActivitiesByCategory(requirement.category());
            JSONObject categoryJsonObject = convertToJsonObject(activities);
            totalHours += processCategory(obj, errorHandler, requirement, categoryJsonObject);
        }
        return totalHours;
    }

    /**
     * Traite une catégorie spécifique pour calculer les heures et enregistrer les erreurs si nécessaire.
     *
     * @param obj              Objet JSON contenant les activités.
     * @param errorHandler     Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @param requirement      Exigence contenant la catégorie et les heures minimales requises.
     * @param categoryJsonObject Objet JSON représentant les activités de la catégorie.
     * @return Total des heures trouvées pour cette catégorie.
     */
    private static int processCategory(JsonFileUtility obj, ErrorHandler errorHandler, CategoryRequirement requirement, JSONObject categoryJsonObject) {
        int actualHours = ActivityHoursCalculator.getTotalHours(categoryJsonObject, errorHandler);
        logMissingHours(obj, errorHandler, requirement, actualHours);
        return actualHours;
    }


    /**
     * Enregistre une erreur si les heures trouvées pour une catégorie sont inférieures aux heures minimales requises.
     *
     * @param obj          Objet JSON contenant les activités.
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles.
     * @param requirement  Exigence contenant la catégorie et les heures minimales requises.
     * @param actualHours  Heures trouvées pour cette catégorie.
     */
    private static void logMissingHours(JsonFileUtility obj, ErrorHandler errorHandler, CategoryRequirement requirement, int actualHours) {
        if (ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre")) != ActivityOrder.ARCHITECTES){
            if (actualHours < requirement.minimumHours()) {
                ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (requirement.minimumHours() - actualHours) +
                        " heures dans la catégorie '" + requirement.category() + "'. Le minimum autorisé est de " + requirement.minimumHours() + "h.");
            }
        }
    }

    /**
     * Convertit une liste d'activités en objet JSON.
     *
     * @param activities Liste des activités à convertir.
     * @return Objet JSON contenant les activités.
     */
    private static JSONObject convertToJsonObject(JSONArray activities) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        return jsonObject;
    }

    /**
     * Représente une exigence de catégorie avec le nom de la catégorie et le nombre d'heures minimales requises.
     */
    private record CategoryRequirement(String category, int minimumHours) {
    }
}