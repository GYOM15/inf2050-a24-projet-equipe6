package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PermitNumberValidatorRule;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PersonValidatorRule;
import GROUPE6_INF2050.Enums.ActivityCategory;

import java.util.Map;

public class Statistics {
    private final JsonFileUtility jsonFileUtility;
    private final StatisticsData statisticsData;

    public Statistics(JsonFileUtility jsonFileUtility, StatisticsData statisticsData) {
        this.jsonFileUtility = jsonFileUtility;
        this.statisticsData = statisticsData;
    }

    /**
     * Effectue les validations et calcule les statistiques en se basant sur le fichier JSON.
     */
    public synchronized void validateAndCalculateStatistics() {
        statisticsData.incrementTotalDeclarations(1);
        processGenderStatistics();
        if (ErrorHandler.errorHandlerInstance().hasErrors()) {
            statisticsData.incrementIncompleteOrInvalidDeclarations(1);
        } else {
            statisticsData.incrementCompleteDeclarations(1);
            statisticsData.incrementCompleteDeclarationsByOrder(ActivityOrder.getCurrentOrder().getOrderString(), 1);
        }
        int activities = ActivityStatistics.getTotalValidActivities(jsonFileUtility);
        statisticsData.incrementTotalActivities(activities);
        processActivitiesByCategory();
        processInvalidPermitStatistics();
    }

    /**
     * Traite les statistiques de genre (homme, femme, genre inconnu).
     */
    private synchronized void processGenderStatistics() {
        int gender = PersonValidatorRule.getGender();
        switch (gender) {
            case 1 -> statisticsData.incrementMaleDeclarations(1);
            case 2 -> statisticsData.incrementFemaleDeclarations(1);
            default -> statisticsData.incrementUnknownGenderDeclarations(1);
        }
    }

    /**
     * Traite les activités par catégorie.
     */
    private synchronized void processActivitiesByCategory() {
        Map<ActivityCategory, Integer> activitiesByCategory = ActivityStatistics.getTotalActivitiesByCategory(jsonFileUtility);
        activitiesByCategory.forEach((category, count) -> {
            if (category != ActivityCategory.CATEGORIE_NON_VALIDE) {
                String categoryName = category.getCategoryFromJsonObj();
                statisticsData.incrementActivitiesByCategory(categoryName, count);
            } else {
                System.out.println("Invalid activity category encountered: " + category);
            }
        });
    }

    /**
     * Traite les permis invalides et incrémente les statistiques correspondantes.
     */
    private synchronized void processInvalidPermitStatistics() {
        if (!new PermitNumberValidatorRule().isPermitNumberState()) {
            statisticsData.incrementInvalidPermitDeclarations(1);
        }
    }
}