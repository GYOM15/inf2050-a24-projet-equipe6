package org.example.Inf2050.Groupe6.Validators.HoursCalculators;

import net.sf.json.JSONArray;
import org.example.Inf2050.Groupe6.Enums.ActivityCategory;
import org.example.Inf2050.Groupe6.Enums.ActivityOrder;
import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.example.Inf2050.Groupe6.Validators.ValidatorsByOrderAndCycle.ActivityFilters.ActivityJsonBuilderByCategoriesConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateMinHoursByOrderCategoryConditions {

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

    public static int calculateMinHoursByCategoryConditions(ActivityOrder order, JSONArray activities, ErrorHandler errorHandler) {
        ValidationConfigForCategoriesWithMinHours config = createValidationConfig(order);
        if (config == null) {
            return 0;
        }

        ActivityJsonBuilderByCategoriesConditions builder = new ActivityJsonBuilderByCategoriesConditions();
        builder.filterByCategorieCondition(activities, config.categoryList());

        return validateHoursForOrder(builder, errorHandler, config);
    }

    private static ValidationConfigForCategoriesWithMinHours createValidationConfig(ActivityOrder order) {
        return switch (order) {
            case GEOLOGUES -> new ValidationConfigForCategoriesWithMinHours(Map.of("cours", 22, "projets de recherche", 3, "groupe de discussion", 1), LIST_GEOLOGUE);
            case PSYCHOLOGUES -> new ValidationConfigForCategoriesWithMinHours(Map.of("cours", 25), LIST_PSYCHOLOGUE);
            case ARCHITECTES -> new ValidationConfigForCategoriesWithMinHours(Map.of("cours", 17), LIST_ARCHITECTES);
            default -> null;
        };
    }

    private static int validateHoursForOrder(ActivityJsonBuilderByCategoriesConditions builder, ErrorHandler errorHandler, ValidationConfigForCategoriesWithMinHours config) {
        Map<String, Integer> actualHoursByCategory = createActualHoursByCategory(builder);
        ValidationContextForMinHours context = new ValidationContextForMinHours(config.minHoursByCategory(), actualHoursByCategory);
        checkAndReportMissingHours(errorHandler, context);
        return actualHoursByCategory.values().stream().mapToInt(Integer::intValue).sum();
    }

    private static Map<String, Integer> createActualHoursByCategory(ActivityJsonBuilderByCategoriesConditions builder) {
        Map<String, Integer> actualHoursByCategory = new HashMap<>();
        actualHoursByCategory.put("cours", ActivityHoursCalculator.getTotalHours(builder.getCoursJsonObject(), null));
        actualHoursByCategory.put("projets de recherche", ActivityHoursCalculator.getTotalHours(builder.getResearchProjectJsonObject(), null));
        actualHoursByCategory.put("groupe de discussion", ActivityHoursCalculator.getTotalHours(builder.getDiscussionGroupJsonObject(), null));
        return actualHoursByCategory;
    }

    private static void checkAndReportMissingHours(ErrorHandler errorHandler, ValidationContextForMinHours context) {
        for (Map.Entry<String, Integer> entry : context.minHoursByCategory().entrySet()) {
            String categoryName = entry.getKey();
            int minHours = entry.getValue();
            int actualHours = context.actualHoursByCategory().getOrDefault(categoryName, 0);
            if (actualHours < minHours) {
                ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (minHours - actualHours) + " heures dans la catégorie " + categoryName + ". Le minimum autorisé est de " + minHours + "h.");
            }
        }
    }
}