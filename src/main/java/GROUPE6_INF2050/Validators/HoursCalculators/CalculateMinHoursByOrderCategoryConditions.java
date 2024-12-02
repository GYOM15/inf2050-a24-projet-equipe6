package GROUPE6_INF2050.Validators.HoursCalculators;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.ValidatorsByOrderAndCycle.ActivityFilters.ActivityJsonBuilderByCategoriesConditions;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class CalculateMinHoursByOrderCategoryConditions {

    private static final List<String> architectCategories = List.of(
            ActivityCategory.COURS.getCategoryFromJsonObj(),
            ActivityCategory.ATELIER.getCategoryFromJsonObj(),
            ActivityCategory.SEMINAIRE.getCategoryFromJsonObj(),
            ActivityCategory.COLLOQUE.getCategoryFromJsonObj(),
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj(),
            ActivityCategory.LECTURE_DIRIGEE.getCategoryFromJsonObj()
    );

    public static int validateMinimumHours(JsonFileUtility obj, ActivityOrder order, ErrorHandler errorHandler) {
        switch (order) {
            case GEOLOGUES, PODIATRES -> validateGeologueAndPodiatreHours(obj, errorHandler);
            case ARCHITECTES -> validateArchitecteHours(obj, errorHandler);
            case PSYCHOLOGUES -> validatePsychologueHours(obj, errorHandler);
        }
        return 0;
    }

    private static void validateGeologueAndPodiatreHours(JsonFileUtility obj, ErrorHandler errorHandler) {
        validateCategories(obj, errorHandler, List.of(
                new CategoryRequirement(ActivityCategory.COURS.getCategoryFromJsonObj(), 22),
                new CategoryRequirement(ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(), 3),
                new CategoryRequirement(ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj(), 1)

        ));
    }

    private static void validateArchitecteHours(JsonFileUtility obj, ErrorHandler errorHandler) {
        int totalHours = validateCategories(obj, errorHandler, architectCategories.stream()
                .map(category -> new CategoryRequirement(category, 17))
                .collect(Collectors.toList()));
        handleArchitecteErrors(totalHours, errorHandler);
    }

    private static void validatePsychologueHours(JsonFileUtility obj, ErrorHandler errorHandler) {
        validateCategories(obj, errorHandler, List.of(
                new CategoryRequirement(ActivityCategory.COURS.getCategoryFromJsonObj(), 25)
        ));
    }

    private static void handleArchitecteErrors(int totalHours, ErrorHandler errorHandler) {
        if (totalHours < 17) {
            String categories = String.join(", ", architectCategories);
            ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (17 - totalHours) +
                    " heures dans les catégories : " + categories + ". Le minimum autorisé est de 17h.");
        }
    }

    private static int validateCategories(JsonFileUtility obj, ErrorHandler errorHandler, List<CategoryRequirement> requirements) {
        ActivityJsonBuilderByCategoriesConditions builder = new ActivityJsonBuilderByCategoriesConditions();
        List<String> categories = extractCategories(requirements);

        builder.filterByCategorieCondition(obj.getJsonArray(), categories);
        return calculateTotalHours(obj, errorHandler, requirements, builder);
    }

    private static List<String> extractCategories(List<CategoryRequirement> requirements) {
        return requirements.stream()
                .map(CategoryRequirement::category)
                .collect(Collectors.toList());
    }

    private static int calculateTotalHours(JsonFileUtility obj, ErrorHandler errorHandler, List<CategoryRequirement> requirements, ActivityJsonBuilderByCategoriesConditions builder) {
        int totalHours = 0;
        for (CategoryRequirement requirement : requirements) {
            JSONArray activities = builder.getActivitiesByCategory(requirement.category());
            JSONObject categoryJsonObject = convertToJsonObject(activities);
            totalHours += processCategory(obj, errorHandler, requirement, categoryJsonObject);
        }
        return totalHours;
    }

    private static int processCategory(JsonFileUtility obj, ErrorHandler errorHandler, CategoryRequirement requirement, JSONObject categoryJsonObject) {
        int actualHours = ActivityHoursCalculator.getTotalHours(categoryJsonObject, errorHandler);
        logMissingHours(obj, errorHandler, requirement, actualHours);
        return actualHours;
    }

    private static void logMissingHours(JsonFileUtility obj, ErrorHandler errorHandler, CategoryRequirement requirement, int actualHours) {
        if (ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre")) != ActivityOrder.ARCHITECTES){
            if (actualHours < requirement.minimumHours()) {
                ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (requirement.minimumHours() - actualHours) +
                        " heures dans la catégorie '" + requirement.category() + "'. Le minimum autorisé est de " + requirement.minimumHours() + "h.");
            }
        }
    }

    private static JSONObject convertToJsonObject(JSONArray activities) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", activities);
        return jsonObject;
    }

    private record CategoryRequirement(String category, int minimumHours) {
    }
}