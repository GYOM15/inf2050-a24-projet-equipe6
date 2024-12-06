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

import java.io.IOException;

public class JsonHandler {
    private final HandleGeneralRulesValidator generalRulesValidator;
    private final Object lock = new Object();

    public JsonHandler(HandleGeneralRulesValidator generalRulesValidator) {
        this.generalRulesValidator = generalRulesValidator;
    }

    public void handleJson(JsonFileUtility obj, StatisticsData statisticsData) throws Groupe6INF2050Exception, IOException {
        synchronized (lock) {
            ErrorHandler errorHandler = new ErrorHandler();
            obj.loadAndValid();
            validateGeneralRules(obj, errorHandler, statisticsData);
            calculateAndSaveTotalHours(obj, errorHandler);
            Statistics statistics = new Statistics(obj, statisticsData);
            statistics.validateAndCalculateStatistics();
        }
    }

    private void validateGeneralRules(JsonFileUtility obj, ErrorHandler errorHandler, StatisticsData statisticsData) throws Groupe6INF2050Exception, IOException {
        generalRulesValidator.handleGeneralsRules(obj, errorHandler, statisticsData);
        synchronized (ActivityOrder.class) {
            ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"));
            ActivityOrder.setCurrentOrder(order);
        }
        synchronized (CycleValidator.class) {
            Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
            CycleValidator.setCurrentCycle(cycle);
        }
    }

    private void calculateAndSaveTotalHours(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(
                obj, ActivityOrder.getCurrentOrder(), errorHandler
        );
        obj.save(errorHandler);
    }
}