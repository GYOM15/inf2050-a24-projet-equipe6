package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

/**
 * Valide le total des heures nécessaires pour les podiatres afin de compléter un cycle donné.
 */
public class PodiatresTotalHoursValidator {

    /**
     *
     * @param cycle        le cycle pour lequel la validation est effectuée
     * @param totalHours   le nombre total d'heures effectuées
     * @param errorHandler le gestionnaire d'erreurs pour collecter les erreurs de validation
     */
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = 60;
        if (totalHours < requiredHours) {
            ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (requiredHours - totalHours) +
                    " heures pour compléter le cycle " + cycle.getLabel());
        }
    }
}