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
            initializeStatistics(statisticsData);
            ErrorHandler errorHandler = loadJsonAndValidate(obj, statisticsData);
            setOrderAndCycle(obj);
            processActivityHours(obj, errorHandler);
            updateStatistics(obj, statisticsData, errorHandler);
        }
    }

    private void initializeStatistics(StatisticsData statisticsData) {
        statisticsData.incrementTotalDeclarations(1);
    }

    private ErrorHandler loadJsonAndValidate(JsonFileUtility obj, StatisticsData statisticsData) throws IOException, Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        validateAndIncrementStatistics(obj, errorHandler, statisticsData);
        return errorHandler;
    }

    private void validateAndIncrementStatistics(JsonFileUtility obj, ErrorHandler errorHandler, StatisticsData statisticsData) throws IOException, Groupe6INF2050Exception {
        boolean isValid = generalRulesValidator.handleGeneralsRules(obj, errorHandler, statisticsData);
        if (isValid) {
            incrementValidDeclarations(obj, statisticsData);
        }
    }

    private void incrementValidDeclarations(JsonFileUtility obj, StatisticsData statisticsData) {
        String orderString = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().optString("ordre", null)).getOrderString();
        statisticsData.incrementValidDeclarationsByOrder(orderString, 1);
    }

    private void setOrderAndCycle(JsonFileUtility obj) {
        setCurrentOrder(obj);
        setCurrentCycle(obj);
    }

    private void setCurrentOrder(JsonFileUtility obj) {
        synchronized (ActivityOrder.class) {
            ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"));
            ActivityOrder.setCurrentOrder(order);
        }
    }

    private void setCurrentCycle(JsonFileUtility obj) {
        synchronized (CycleValidator.class) {
            Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
            CycleValidator.setCurrentCycle(cycle);
        }
    }

    private void processActivityHours(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        validateActivityHours(obj, errorHandler, totalHours);
        obj.save(errorHandler);
    }

    private void validateActivityHours(JsonFileUtility obj, ErrorHandler errorHandler, int totalHours) throws Groupe6INF2050Exception {
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(
                obj, ActivityOrder.getCurrentOrder(), errorHandler
        );
    }

    private void updateStatistics(JsonFileUtility obj, StatisticsData statisticsData, ErrorHandler errorHandler) throws IOException {
        Statistics statistics = new Statistics(obj, statisticsData, errorHandler);
        statistics.validateAndCalculateStatistics();
    }
}