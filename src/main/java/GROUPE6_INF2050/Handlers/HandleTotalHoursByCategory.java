package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralValidators.TransferredHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.CalculateMaxByHoursOrderCategoryConditions;
import GROUPE6_INF2050.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.ArchitectesTotalHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.GeologueTotalHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.PodiatresTotalHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.PsychologueTotalHoursValidator;

public class HandleTotalHoursByCategory {

    public static void handleHoursTotal(JsonFileUtility obj, int totalHours, ErrorHandler errorHandler) {
        Cycle cycle = getCycleFromJson(obj);
        if (ActivityOrder.getCurrentOrder() == null || cycle == null) return;
        switch (ActivityOrder.getCurrentOrder()) {
            case ARCHITECTES -> processArchitecte(obj, cycle, totalHours, errorHandler);
            case PSYCHOLOGUES -> processPsychologue(obj, cycle, totalHours, errorHandler);
            case GEOLOGUES -> processGeologue(cycle, totalHours, errorHandler);
            case PODIATRES -> processPodiatre(cycle, totalHours, errorHandler);
        }
    }

    private static void processArchitecte(JsonFileUtility obj, Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        totalHours = adjustArchitecteHours(obj, totalHours, errorHandler);
        validateArchitecteHours(cycle, totalHours, errorHandler);
    }

    private static int adjustArchitecteHours(JsonFileUtility obj, int totalHours, ErrorHandler errorHandler) {
        totalHours -= CalculateMaxByHoursOrderCategoryConditions.validateArchitecteMaxHours(obj.getJsonArray(), errorHandler);
        int architectMinHours = CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(
                obj, ActivityOrder.ARCHITECTES, null);
        if (architectMinHours < 17) {
            totalHours += TransferredHoursValidator.validateTransferredHours(obj.getJsonObject(), errorHandler);
        }
        return totalHours;
    }

    private static void validateArchitecteHours(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    private static void processPsychologue(JsonFileUtility obj, Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        totalHours = adjustPsychologueHours(obj, totalHours, errorHandler);
        validatePsychologueHours(cycle, totalHours, errorHandler);
    }

    private static int adjustPsychologueHours(JsonFileUtility obj, int totalHours, ErrorHandler errorHandler) {
        return totalHours - CalculateMaxByHoursOrderCategoryConditions.validatePsychologueMaxHours(obj.getJsonArray(), errorHandler);
    }

    private static void validatePsychologueHours(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        PsychologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    private static void processGeologue(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        validateGeologue(cycle, totalHours, errorHandler);
    }

    private static void validateGeologue(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    private static void validatePodiatre(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        PodiatresTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    private static void processPodiatre(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        validatePodiatre(cycle, totalHours, errorHandler);
    }

    private static Cycle getCycleFromJson(JsonFileUtility obj) {
        return Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
    }
}