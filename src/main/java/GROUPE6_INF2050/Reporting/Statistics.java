package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Handlers.HandleGeneralRulesValidator;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PermitNumberValidatorRule;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PersonValidatorRule;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.ArchitectesTotalHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.GeologueTotalHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.PodiatresTotalHoursValidator;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.PsychologueTotalHoursValidator;

import java.util.Map;

public class Statistics {
    private final JsonFileUtility jsonFileUtility;
    private final HandleGeneralRulesValidator handleGeneralRulesValidator;
    private final StatisticsData statisticsData;

    public Statistics(JsonFileUtility jsonFileUtility, HandleGeneralRulesValidator handleGeneralRulesValidator, StatisticsData statisticsData) {
        this.jsonFileUtility = jsonFileUtility;
        this.handleGeneralRulesValidator = handleGeneralRulesValidator;
        this.statisticsData = statisticsData;
    }

    public void validateAndCalculateStatistics() {
        statisticsData.incrementTotalDeclarations();

        processGenderStatistics();
        if (!ErrorHandler.errorHandlerInstance().hasErrors()) {
            statisticsData.incrementIncompleteOrInvalidDeclarations();
        }else {
            statisticsData.incrementValidDeclarations();
        }

        int activities = ActivityStatistics.getTotalValidActivities(jsonFileUtility);
        statisticsData.incrementTotalActivities(activities);

        processActivitiesByCategory();
        processOrderSpecificStatistics();
        processInvalidPermitStatistics();
    }

    private void processGenderStatistics() {
        int gender = PersonValidatorRule.getGender();
        switch (gender) {
            case 1 -> statisticsData.incrementMaleDeclarations();
            case 2 -> statisticsData.incrementFemaleDeclarations();
            default -> statisticsData.incrementUnknownGenderDeclarations();
        }
    }

    private void processOrderSpecificStatistics() {
        ActivityOrder order = ActivityOrder.getCurrentOrder();
        statisticsData.incrementValidDeclarationsByOrder(order.getOrder());
        if (isDeclarationComplete(order)) {
            statisticsData.incrementCompleteDeclarations();
            statisticsData.incrementCompleteDeclarationsByOrder(order.getOrder());
        }
    }

    private boolean isDeclarationComplete(ActivityOrder order) {
        return switch (order) {
            case ARCHITECTES -> ArchitectesTotalHoursValidator.isComplet();
            case PSYCHOLOGUES -> PsychologueTotalHoursValidator.isComplet();
            case GEOLOGUES -> GeologueTotalHoursValidator.isComplet();
            case PODIATRES -> PodiatresTotalHoursValidator.isComplet();
            default -> false;
        };
    }

    private void processActivitiesByCategory() {
        Map<ActivityCategory, Integer> activitiesByCategoryMap = ActivityStatistics.getTotalActivitiesByCategory(jsonFileUtility);

        activitiesByCategoryMap.forEach((category, count) -> {
            String categoryName = category.getCategoryFromJsonObj();
            statisticsData.incrementActivitiesByCategory(categoryName, count);
        });

    }

    private void processInvalidPermitStatistics() {
        if (!new PermitNumberValidatorRule().isPermitNumberState()) {
            statisticsData.incrementInvalidPermitDeclarations();
        }
    }
}