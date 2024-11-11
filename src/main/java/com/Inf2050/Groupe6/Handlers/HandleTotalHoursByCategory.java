package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;
import main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.TransferredHoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.CalculateMaxByHoursOrderCategoryConditions;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.OrdersHoursTotalCalculators.ArchitectesTotalHoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.OrdersHoursTotalCalculators.GeologueTotalHoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.OrdersHoursTotalCalculators.PsychologueTotalHoursValidator;

public class HandleTotalHoursByCategory {

    /**
     * Méthode principale pour gérer la validation des heures totales en fonction de l'ordre
     * et du cycle
     * @param obj L'objet JsonFileUtility contenant les données JSON
     * @param totalHours Le nombre total d'heures à valider
     * @param errorHandler L'instance ErrorHandler pour gérer les erreurs éventuelles
     */
    public static void handleHoursTotal(JsonFileUtility obj, int totalHours, ErrorHandler errorHandler) {
        if (getCycleFromJson(obj) == null || getOrderFromJson(obj) == null) return;
        switch (getOrderFromJson(obj)) {
            case ARCHITECTES -> {
                totalHours -= CalculateMaxByHoursOrderCategoryConditions.validateArchitecteMaxHours(obj.getJsonArray(), errorHandler);
                handleArchitectes(obj, getCycleFromJson(obj), totalHours, errorHandler);
            }
            case PSYCHOLOGUES -> {
                totalHours -= CalculateMaxByHoursOrderCategoryConditions.validatePsychologueMaxHours(obj.getJsonArray(), errorHandler);
                PsychologueTotalHoursValidator.validateByCycle(getCycleFromJson(obj), totalHours, errorHandler);
            }
            case GEOLOGUES -> GeologueTotalHoursValidator.validateByCycle(getCycleFromJson(obj), totalHours, errorHandler);
        }
    }

    /**
     * @param obj L'objet JsonFileUtility contenant les données JSON
     * @return L'ordre d'activité extrait du JSON
     */
    private static ActivityOrder getOrderFromJson(JsonFileUtility obj) {
        return ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"), null);
    }

    /**
     * @param obj L'objet JsonFileUtility contenant les données JSON
     * @return Le cycle d'activité extrait du JSON
     */
    private static Cycle getCycleFromJson(JsonFileUtility obj) {
        return Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
    }

    /**
     * Gère la validation spécifique pour les architectes, en ajoutant les heures transférées
     * si le total minimum requis n'est pas atteint.
     *
     * @param obj L'objet JsonFileUtility contenant les données JSON
     * @param cycle Le cycle à valider
     * @param totalHours Le nombre total d'heures à valider
     * @param errorHandler L'instance ErrorHandler pour gérer les erreurs éventuelles
     */
    private static void handleArchitectes(JsonFileUtility obj, Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        if (shouldAddTransferredHours(obj)) {
            totalHours += TransferredHoursValidator.validateTransferredHours(obj.getJsonObject(), errorHandler);
        }
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
    }

    /**
     * @param obj L'objet JsonFileUtility contenant les données JSON
     * @return true si les heures doivent être ajoutées, false sinon
     */
    private static boolean shouldAddTransferredHours(JsonFileUtility obj) {
        return CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(
                ActivityOrder.ARCHITECTES,
                obj.getJsonArray(),
                null
        ) < 17;
    }
}