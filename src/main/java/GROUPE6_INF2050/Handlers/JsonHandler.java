package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.HoursCalculators.ActivityHoursCalculator;
import GROUPE6_INF2050.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;
import GROUPE6_INF2050.Validators.ValidatorsByOrderAndCycle.ActivityFilters.ActivityJsonBuilderByCategoriesConditions;

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
        validateJsonContent(obj, errorHandler);
        validateGeneralRules(obj, errorHandler);
//        ActivityJsonBuilderByCategoriesConditions activityBuilder = filterActivities(obj);
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
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"));
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(obj,order,errorHandler);
        obj.save(errorHandler);
    }

    /**
     * Valide le contenu JSON en vérifiant l'ordre, le cycle
     */
    private static void validateJsonContent(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = extractAndValidateOrder(obj, errorHandler);
        extractAndValidateCycle(obj, errorHandler, order);
    }

    /**
     * Extrait et valide l'ordre des activités du fichier JSON.
     */
    private static ActivityOrder extractAndValidateOrder(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"));
        if (order == ActivityOrder.ORDER_NON_VALIDE) {
            handleValidationError(errorHandler, obj, "L'ordre "+obj.getJsonObject().getString("ordre") + " n'est pas valide");
        }
        return order;
    }

    /**
     * Extrait et valide le cycle associé à l'ordre.
     */
    private static void extractAndValidateCycle(JsonFileUtility obj, ErrorHandler errorHandler, ActivityOrder order) throws Groupe6INF2050Exception {
        Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
        if (ActivityOrder.isCycleValidByOrder(cycle, order)) {
            handleValidationError(errorHandler, obj, "Cycle invalide pour l'ordre sélectionné");
        }
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