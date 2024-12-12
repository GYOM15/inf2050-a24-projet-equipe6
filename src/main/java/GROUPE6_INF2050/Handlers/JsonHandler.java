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

    /**
     * Gère le traitement complet d'un fichier JSON, de la validation des règles aux mises à jour des statistiques.
     *
     * @param obj            Instance de JsonFileUtility pour charger et manipuler le fichier JSON.
     * @param statisticsData Instance de StatisticsData pour mettre à jour les statistiques globales.
     * @throws Groupe6INF2050Exception En cas d'erreur de validation.
     * @throws IOException             En cas d'erreur de lecture ou d'écriture de fichier.
     */
    public void handleJson(JsonFileUtility obj, StatisticsData statisticsData) throws Groupe6INF2050Exception, IOException {
        synchronized (lock) {
            initializeStatistics(statisticsData);
            ErrorHandler errorHandler = loadJsonAndValidate(obj, statisticsData);
            setOrderAndCycle(obj);
            processActivityHours(obj, errorHandler);
            updateStatistics(obj, statisticsData, errorHandler);
        }
    }

    /**
     * Initialise les statistiques en incrémentant le nombre total de déclarations.
     *
     * @param statisticsData Instance des statistiques globales.
     */
    private void initializeStatistics(StatisticsData statisticsData) {
        statisticsData.incrementTotalDeclarations(1);
    }


    /**
     * Charge le fichier JSON, valide les données et met à jour les statistiques.
     *
     * @param obj            Instance de JsonFileUtility pour manipuler le fichier JSON.
     * @param statisticsData Instance des statistiques globales.
     * @return Une instance d'ErrorHandler contenant les erreurs rencontrées.
     * @throws IOException             En cas d'erreur de lecture de fichier.
     * @throws Groupe6INF2050Exception En cas d'erreur de validation.
     */
    private ErrorHandler loadJsonAndValidate(JsonFileUtility obj, StatisticsData statisticsData) throws IOException, Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        validateAndIncrementStatistics(obj, errorHandler, statisticsData);
        return errorHandler;
    }


    /**
     * Valide les données JSON en appliquant les règles générales et met à jour les statistiques en cas de succès.
     *
     * @param obj            Instance de JsonFileUtility pour manipuler le fichier JSON.
     * @param errorHandler   Instance pour enregistrer les erreurs.
     * @param statisticsData Instance des statistiques globales.
     * @throws IOException             En cas d'erreur de lecture ou d'écriture de fichier.
     * @throws Groupe6INF2050Exception En cas d'erreur de validation.
     */
    private void validateAndIncrementStatistics(JsonFileUtility obj, ErrorHandler errorHandler, StatisticsData statisticsData) throws IOException, Groupe6INF2050Exception {
        boolean isValid = generalRulesValidator.handleGeneralsRules(obj, errorHandler, statisticsData);
        if (isValid) {
            incrementValidDeclarations(obj, statisticsData);
        }
    }

    /**
     * Incrémente les déclarations valides pour l'ordre en cours.
     *
     * @param obj            Instance de JsonFileUtility pour accéder aux données JSON.
     * @param statisticsData Instance des statistiques globales.
     */
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

    /**
     * Définit le cycle actuel à partir des données JSON.
     *
     * @param obj Instance de JsonFileUtility pour accéder aux données JSON.
     */
    private void setCurrentCycle(JsonFileUtility obj) {
        synchronized (CycleValidator.class) {
            Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
            CycleValidator.setCurrentCycle(cycle);
        }
    }

    /**
     * Traite les heures d'activités, effectue des validations et enregistre les résultats.
     *
     * @param obj           Instance de JsonFileUtility pour accéder aux données JSON.
     * @param errorHandler  Instance pour enregistrer les erreurs.
     * @throws Groupe6INF2050Exception En cas d'erreur de validation.
     */
    private void processActivityHours(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        validateActivityHours(obj, errorHandler, totalHours);
        obj.save(errorHandler);
    }


    /**
     * Valide le total des heures d'activités pour les catégories et les ordres.
     *
     * @param obj           Instance de JsonFileUtility pour accéder aux données JSON.
     * @param errorHandler  Instance pour enregistrer les erreurs.
     * @param totalHours    Total des heures calculé pour validation.
     */
    private void validateActivityHours(JsonFileUtility obj, ErrorHandler errorHandler, int totalHours) {
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);
        CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(
                obj, ActivityOrder.getCurrentOrder(), errorHandler
        );
    }

    /**
     * Met à jour les statistiques après les validations et calculs des heures.
     *
     * @param obj            Instance de JsonFileUtility pour accéder aux données JSON.
     * @param statisticsData Instance des statistiques globales.
     * @param errorHandler   Instance pour enregistrer les erreurs.
     */
    private void updateStatistics(JsonFileUtility obj, StatisticsData statisticsData, ErrorHandler errorHandler) {
        Statistics statistics = new Statistics(obj, statisticsData, errorHandler);
        statistics.validateAndCalculateStatistics();
    }
}