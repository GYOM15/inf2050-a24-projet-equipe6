package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PersonValidatorRule;
import GROUPE6_INF2050.Enums.ActivityCategory;

import java.util.Map;

public class Statistics {
    private final JsonFileUtility jsonFileUtility;
    private final StatisticsData statisticsData;
    private final ErrorHandler errorHandler;

    public Statistics(JsonFileUtility jsonFileUtility, StatisticsData statisticsData, ErrorHandler errorHandler) {
        this.jsonFileUtility = jsonFileUtility;
        this.statisticsData = statisticsData;
        this.errorHandler = errorHandler;
    }

    /**
     * Valide les données et met à jour les statistiques globales.
     * Cette méthode synchronise l'accès à l'objet `statisticsData` pour garantir
     * la cohérence des mises à jour dans un environnement multi-threadé.
     */
    public void validateAndCalculateStatistics() {
        synchronized (statisticsData) {
            processGenderStatistics();
            processDeclarationValidity();
            processActivities();
        }
    }

    /**
     * Met à jour les statistiques liées à la validité des déclarations.
     * Incrémente les déclarations complètes ou incomplètes/invalides
     * en fonction des erreurs détectées.
     */
    private void processDeclarationValidity() {
        if (!errorHandler.hasErrors()) {
            statisticsData.incrementIncompleteOrInvalidDeclarations(1);
        }
        else {
            statisticsData.incrementCompleteDeclarations(1);
            statisticsData.incrementCompleteDeclarationsByOrder(
                    ActivityOrder.getCurrentOrder().getOrderString(), 1);
        }
    }

    /**
     * Met à jour les statistiques liées au genre des déclarants,
     * basées sur la valeur extraite via la règle de validation des personnes.
     */
    private void processGenderStatistics() {
        int gender = PersonValidatorRule.getGender();
        switch (gender) {
            case 1 -> statisticsData.incrementMaleDeclarations(1);
            case 2 -> statisticsData.incrementFemaleDeclarations(1);
            default -> statisticsData.incrementUnknownGenderDeclarations(1);
        }
    }

    /**
     * Met à jour les statistiques globales pour les activités, notamment
     * le nombre total d'activités valides.
     */
    private void processActivities() {
        int totalValidActivities = ActivityStatistics.getTotalValidActivities(jsonFileUtility);
        statisticsData.incrementTotalActivities(totalValidActivities);
        processActivitiesByCategory();
    }
    /**
     * Met à jour les statistiques des activités en fonction de leurs catégories.
     * Les catégories sont calculées et comptées à l'aide d'ActivityStatistics.
     */
    private void processActivitiesByCategory() {
        Map<ActivityCategory, Integer> activitiesByCategory = ActivityStatistics.getTotalActivitiesByCategory(jsonFileUtility);
        activitiesByCategory.forEach((category, count) -> {
                statisticsData.incrementActivitiesByCategory(category.getCategoryFromJsonObj(), count);
        });
    }
}