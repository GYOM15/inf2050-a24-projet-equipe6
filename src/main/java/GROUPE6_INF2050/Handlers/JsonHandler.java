package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.Statistics;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.CycleValidator;
import GROUPE6_INF2050.Validators.Generics.Calculator.ActivityHoursCalculator;
import GROUPE6_INF2050.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;
import GROUPE6_INF2050.Enums.ActivityOrder;

public class JsonHandler {
    private final HandleGeneralRulesValidator generalRulesValidator;

    /**
     * Constructor with dependency injection.
     *
     * @param generalRulesValidator Instance of HandleGeneralRulesValidator.
     */
    public JsonHandler(HandleGeneralRulesValidator generalRulesValidator) {
        this.generalRulesValidator = generalRulesValidator;
    }

    /**
     * Entry point for handling JSON processing.
     *
     * @param obj          The JsonFileUtility instance containing the JSON data.
     * @param statisticsData The StatisticsData instance to track statistics.
     * @throws Groupe6INF2050Exception If a validation fails.
     */
    public void handleJson(JsonFileUtility obj, StatisticsData statisticsData) throws Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid(); // Validate JSON structure
        validateGeneralRules(obj, errorHandler); // Apply general rules
        calculateAndSaveTotalHours(obj, errorHandler); // Calculate total hours

        // Process statistics using instance-based StatisticsData
        Statistics statistics = new Statistics(obj, generalRulesValidator, statisticsData);
        statistics.validateAndCalculateStatistics();
    }

    /**
     * Validates general rules using the injected general rules handler.
     *
     * @param obj          JSON Utility object.
     * @param errorHandler Error handler for validation errors.
     * @throws Groupe6INF2050Exception If validation fails.
     */
    private void validateGeneralRules(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"));
        ActivityOrder.setCurrentOrder(order); // Set current order globally
        Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
        CycleValidator.setCurrentCycle(cycle); // Set current cycle globally
        generalRulesValidator.handleGeneralsRules(obj, errorHandler); // Apply general rules
    }

    /**
     * Calculates total hours and validates against conditions.
     *
     * @param obj          JSON Utility object.
     * @param errorHandler Error handler for validation errors.
     * @throws Groupe6INF2050Exception If a validation fails.
     */
    private void calculateAndSaveTotalHours(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler); // Process total hours by category
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(
                obj, ActivityOrder.getCurrentOrder(), errorHandler
        ); // Validate minimum hours for order
        obj.save(errorHandler); // Save validated JSON data
    }
}