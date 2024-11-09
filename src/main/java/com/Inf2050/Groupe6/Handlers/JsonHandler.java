package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFieldsUtility;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.ActivityHoursCalculator;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;

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

        if (HandleGeneralRulesValidator.handleGeneralsRules(obj, errorHandler)) {
            System.out.println("Les validations préalables avant traitement sont correctes");
        }

        validateJsonContent(obj, errorHandler);

        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);

        obj.save(errorHandler);
    }

    /**
     * Valide le contenu JSON en vérifiant l'ordre, le cycle et le calcul des heures minimales.
     *
     * @param obj L'instance JsonFileUtility contenant les données JSON
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs de validation
     * @throws Groupe6INF2050Exception Exception personnalisée en cas d'échec de validation
     */
    private static void validateJsonContent(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = extractAndValidateOrder(obj, errorHandler);
        Cycle cycle = extractAndValidateCycle(obj, errorHandler, order);
        CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(order, obj.getJsonArray(), errorHandler);
    }

    /**
     * Extrait et valide l'ordre des activités du fichier JSON.
     *
     * @param obj L'instance JsonFileUtility contenant les données JSON
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs de validation
     * @return L'ordre des activités
     * @throws Groupe6INF2050Exception Exception personnalisée si l'ordre est non valide
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
     *
     * @param obj L'instance JsonFileUtility contenant les données JSON
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs de validation
     * @param order L'ordre des activités
     * @return Le cycle des activités
     * @throws Groupe6INF2050Exception Exception personnalisée si le cycle est non valide pour l'ordre
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
     *
     * @param errorHandler Gestionnaire d'erreurs
     * @param obj L'instance JsonFileUtility contenant les données JSON
     * @param message Message d'erreur à enregistrer et afficher
     * @throws Groupe6INF2050Exception Exception contenant le message d'erreur
     */
    private static void handleValidationError(ErrorHandler errorHandler, JsonFileUtility obj, String message) throws Groupe6INF2050Exception {
        ErrorHandler.addErrorIfNotNull(errorHandler, message);
        obj.save(errorHandler);
        throw new Groupe6INF2050Exception(message);
    }
}