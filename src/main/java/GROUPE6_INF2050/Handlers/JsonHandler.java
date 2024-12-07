package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.CycleValidator;
import GROUPE6_INF2050.Validators.Generics.Calculator.ActivityHoursCalculator;
import GROUPE6_INF2050.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;
import GROUPE6_INF2050.Enums.ActivityOrder;

public class JsonHandler {
    private final HandleGeneralRulesValidator generalRulesValidator;

    /**
     * Constructeur avec injection de dépendances.
     *
     * @param generalRulesValidator Instance de HandleGeneralRulesValidator.
     */
    public JsonHandler(HandleGeneralRulesValidator generalRulesValidator) {
        this.generalRulesValidator = generalRulesValidator;
    }

    /**
     * Point d'entrée pour gérer le traitement JSON.
     *
     * @param obj L'instance JsonFileUtility contenant les données JSON.
     * @throws Groupe6INF2050Exception Si une validation échoue.
     */
    public void handleJson(JsonFileUtility obj) throws Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        validateGeneralRules(obj, errorHandler);
        calculateAndSaveTotalHours(obj, errorHandler);
    }

    /**
     * Valide les règles générales en utilisant le gestionnaire injecté.
     *
     * @param obj           Utilitaire JSON.
     * @param errorHandler  Gestionnaire d'erreurs.
     * @throws Groupe6INF2050Exception Si les validations échouent.
     */
    private void validateGeneralRules(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"));
        Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
        CycleValidator.setCurrentCycle(cycle);
        generalRulesValidator.handleGeneralsRules(obj, errorHandler);
    }

    /**
     * Calcule les heures totales et sauvegarde les données JSON.
     *
     * @param obj           Utilitaire JSON.
     * @param errorHandler  Gestionnaire d'erreurs.
     * @throws Groupe6INF2050Exception Si une validation des heures échoue.
     */
    private void calculateAndSaveTotalHours(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(obj, ActivityOrder.getCurrentOrder(), errorHandler);
        obj.save(errorHandler);
    }
}