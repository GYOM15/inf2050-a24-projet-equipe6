package GROUPE6_INF2050.Validators.HoursCalculators;

import GROUPE6_INF2050.Validators.Generics.Calculator.ActivityHoursCalculator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.Generics.ActivityFilter.ActivityJsonBuilderByCategoriesConditions;

import java.util.List;
import java.util.Map;

public class CalculateMaxByHoursOrderCategoryConditions {

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

    public static int validatePsychologueMaxHours(JSONArray activities, ErrorHandler errorHandler) {
        return validateMaxHours(activities, LIST_PSYCHOLOGUE, errorHandler);
    }

    public static int validateArchitecteMaxHours(JSONArray activities, ErrorHandler errorHandler) {
        return validateMaxHours(activities, LIST_ARCHITECTES, errorHandler);
    }

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

    private static JSONObject buildCategoryJsonObject(ActivityJsonBuilderByCategoriesConditions builder, String category) {
        JSONArray categoryActivities = builder.getActivitiesByCategory(category);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("activites", categoryActivities);
        return jsonObject;
    }

    private static int applyMaxLimit(int hours, String category) {
        Integer maxHours = MAX_HOURS_LIMITS.get(category);
        return (maxHours != null && hours > maxHours) ? hours - maxHours : 0;
    }
}