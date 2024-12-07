package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Enums.ActivityCategory;
import GROUPE6_INF2050.Enums.ActivityOrder;
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

    public Statistics(JsonFileUtility jsonFileUtility, HandleGeneralRulesValidator handleGeneralRulesValidator) {
        this.jsonFileUtility = jsonFileUtility;
        this.handleGeneralRulesValidator = handleGeneralRulesValidator;
    }

    public void validateAndCalculateStatistics() {
        StatisticsData.incrementTotalDeclarations();

        processGenderStatistics();
        if (!handleGeneralRulesValidator.isGeneralRuleValid()) {
            StatisticsData.incrementIncompleteOrInvalidDeclarations();
            return;
        }

        StatisticsData.incrementValidDeclarations();

        int activities = ActivityStatistics.getTotalValidActivities(jsonFileUtility);
        StatisticsData.incrementTotalActivities(activities);

        processActivitiesByCategory();
        processOrderSpecificStatistics();
        processInvalidPermitStatistics();
    }

    private void processGenderStatistics() {
        int gender = new PersonValidatorRule().getGender();
        switch (gender) {
            case 1 -> StatisticsData.incrementMaleDeclarations();
            case 2 -> StatisticsData.incrementFemaleDeclarations();
            default -> StatisticsData.incrementUnknownGenderDeclarations();
        }
    }

    private void processOrderSpecificStatistics() {
        ActivityOrder order = ActivityOrder.getCurrentOrder();
        StatisticsData.incrementValidDeclarationsByOrder(order.name());

        if (isDeclarationComplete(order)) {
            StatisticsData.incrementCompleteDeclarations();
            StatisticsData.incrementCompleteDeclarationsByOrder(order.name());
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
        Map<ActivityCategory, Integer> activitiesByCategory = ActivityStatistics.getTotalActivitiesByCategory(jsonFileUtility);
        activitiesByCategory.forEach((category, count) ->
                StatisticsData.incrementActivitiesByCategory(category.name())
        );
    }

    private void processInvalidPermitStatistics() {
        if (!new PermitNumberValidatorRule().isPermitNumberState()) {
            StatisticsData.incrementInvalidPermitDeclarations();
        }
    }
}