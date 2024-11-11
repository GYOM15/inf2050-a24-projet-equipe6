package org.example.Inf2050.Groupe6.Handlers;

import net.sf.json.JSONArray;
import org.example.Inf2050.Groupe6.Enums.ActivityOrder;
import org.example.Inf2050.Groupe6.Enums.Cycle;
import org.example.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import org.example.Inf2050.Groupe6.Utilities.JsonFileUtility;
import org.example.Inf2050.Groupe6.Validators.HoursCalculators.ActivityHoursCalculator;
import org.example.Inf2050.Groupe6.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;
import org.example.Inf2050.Groupe6.Validators.ValidatorsByOrderAndCycle.ActivityFilters.ActivityJsonBuilderByCategoriesConditions;

import java.util.List;

public class JsonHandler {

    /**
     * Gère le traitement principal des validations JSON, vérifiant les règles générales,
     * l'ordre et le cycle, puis calculant et validant les heures par catégorie.
     *
     * @param obj L'instance JsonFileUtility contenant les données JSON
     * @throws Groupe6INF2050Exception Exception personnalisée en cas d'échec de validation
     */
    public static void handleJson(JsonFileUtility obj) throws Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        validateGeneralRules(obj, errorHandler);
        ActivityJsonBuilderByCategoriesConditions activityBuilder = filterActivities(obj);
        validateJsonContent(obj, errorHandler, activityBuilder);
        calculateAndSaveTotalHours(obj, errorHandler);
    }

    /**
     * Valide les règles générales de l'objet JSON.
     */
    private static void validateGeneralRules(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        if (HandleGeneralRulesValidator.handleGeneralsRules(obj, errorHandler)) {
            System.out.println("Les validations préalables avant traitement sont correctes");
        }
    }

    /**
     * Filtre les activités par catégories et retourne l'instance d'ActivityJsonBuilderByCategoriesConditions.
     */
    private static ActivityJsonBuilderByCategoriesConditions filterActivities(JsonFileUtility obj) {
        ActivityJsonBuilderByCategoriesConditions activityBuilder = new ActivityJsonBuilderByCategoriesConditions();
        List<String> categories = List.of("cours", "groupe de discussion", "projet de recherche", "conférence", "présentation", "rédaction professionnelle");
        activityBuilder.filterByCategorieCondition(obj.getJsonArray(), categories);
        return activityBuilder;
    }

    /**
     * Calcule et sauvegarde les heures totales.
     */
    private static void calculateAndSaveTotalHours(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);
        obj.save(errorHandler);
    }

    /**
     * Valide le contenu JSON en vérifiant l'ordre, le cycle et le calcul des heures minimales.
     */
    private static void validateJsonContent(JsonFileUtility obj, ErrorHandler errorHandler, ActivityJsonBuilderByCategoriesConditions activityBuilder) throws Groupe6INF2050Exception {
        ActivityOrder order = extractAndValidateOrder(obj, errorHandler);
        Cycle cycle = extractAndValidateCycle(obj, errorHandler, order);
        CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(order, activityBuilder.getFilteredActivities(), errorHandler);
    }

    /**
     * Extrait et valide l'ordre des activités du fichier JSON.
     */
    private static ActivityOrder extractAndValidateOrder(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"), errorHandler);
        if (order == ActivityOrder.ORDER_NON_VALIDE) {
            handleValidationError(errorHandler, obj, "Ordre non valide");
        }
        return order;
    }

    /**
     * Extrait et valide le cycle associé à l'ordre.
     */
    private static Cycle extractAndValidateCycle(JsonFileUtility obj, ErrorHandler errorHandler, ActivityOrder order) throws Groupe6INF2050Exception {
        Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
        if (ActivityOrder.isCycleValidByOrder(cycle, order)) {
            handleValidationError(errorHandler, obj, "Cycle invalide pour l'ordre sélectionné");
        }
        return cycle;
    }

    /**
     * Gère l'enregistrement et l'affichage d'une erreur de validation, puis lance une exception.
     */
    private static void handleValidationError(ErrorHandler errorHandler, JsonFileUtility obj, String message) throws Groupe6INF2050Exception {
        ErrorHandler.addErrorIfNotNull(errorHandler, message);
        obj.save(errorHandler);
        throw new Groupe6INF2050Exception(message);
    }
}