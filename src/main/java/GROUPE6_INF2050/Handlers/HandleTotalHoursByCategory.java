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

    /**
     * Gère la validation et l'ajustement du total des heures pour un ordre donné.
     *
     * @param obj           Instance de JsonFileUtility contenant les données JSON.
     * @param totalHours    Total des heures à valider.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs de validation.
     */
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

    /**
     * Traite et valide les heures pour les architectes.
     *
     * @param obj           Instance de JsonFileUtility contenant les données JSON.
     * @param cycle         Cycle associé à la validation.
     * @param totalHours    Total des heures pour validation.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs.
     */
    private static void processArchitecte(JsonFileUtility obj, Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        totalHours = adjustArchitecteHours(obj, totalHours, errorHandler);
        validateArchitecteHours(cycle, totalHours, errorHandler);
    }


    /**
     * Ajuste les heures des architectes en fonction des heures maximales et transférées.
     *
     * @param obj           Instance de JsonFileUtility contenant les données JSON.
     * @param totalHours    Total des heures avant ajustement.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs.
     * @return Total des heures ajusté.
     */
    private static int adjustArchitecteHours(JsonFileUtility obj, int totalHours, ErrorHandler errorHandler) {
        totalHours -= CalculateMaxByHoursOrderCategoryConditions.validateArchitecteMaxHours(obj.getJsonArray(), errorHandler);
        int architectMinHours = CalculateMinHoursByOrderCategoryConditions.validateMinimumHours(
                obj, ActivityOrder.ARCHITECTES, null);
        if (architectMinHours < 17) {
            totalHours += TransferredHoursValidator.validateTransferredHours(obj.getJsonObject(), errorHandler);
        }
        return totalHours;
    }


    /**
     * Valide les heures ajustées pour les architectes en fonction du cycle.
     *
     * @param cycle         Cycle associé à la validation.
     * @param totalHours    Total des heures ajusté.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs.
     */
    private static void validateArchitecteHours(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    /**
     * Traite et valide les heures pour les psychologues.
     *
     * @param obj           Instance de JsonFileUtility contenant les données JSON.
     * @param cycle         Cycle associé à la validation.
     * @param totalHours    Total des heures pour validation.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs.
     */
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

    /**
     * Valide les heures pour les géologues.
     *
     * @param cycle         Cycle associé à la validation.
     * @param totalHours    Total des heures pour validation.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs.
     */
    private static void processGeologue(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        validateGeologue(cycle, totalHours, errorHandler);
    }

    private static void validateGeologue(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }


    /**
     * Valide les heures pour les podiatres.
     *
     * @param cycle         Cycle associé à la validation.
     * @param totalHours    Total des heures pour validation.
     * @param errorHandler  Gestionnaire d'erreurs pour enregistrer les erreurs.
     */
    private static void validatePodiatre(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        PodiatresTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    private static void processPodiatre(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        validatePodiatre(cycle, totalHours, errorHandler);
    }

    /**
     * Récupère le cycle à partir du fichier JSON.
     *
     * @param obj Instance de JsonFileUtility contenant les données JSON.
     * @return Cycle correspondant à la chaîne dans le fichier JSON.
     */
    private static Cycle getCycleFromJson(JsonFileUtility obj) {
        return Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
    }
}