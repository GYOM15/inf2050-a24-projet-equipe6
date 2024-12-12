package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

public class ArchitectesTotalHoursValidator {
    /**
     * Valide si le total d'heures accumulées pour les architectes est suffisant pour le cycle donné.
     * @param cycle Le cycle d'évaluation (exemple : CYCLE_2023_2025)
     * @param totalHours Le nombre total d'heures accumulées par l'architecte
     * @param errorHandler L'instance ErrorHandler pour enregistrer les erreurs éventuelles
     */
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = switch (cycle) {
            case CYCLE_2023_2025 -> 40;
            case CYCLE_2020_2022, CYCLE_2018_2020 -> 42;
            default -> 0;
        };
        shouldAddError(totalHours, errorHandler, requiredHours);
    }

    private static void shouldAddError(int totalHours, ErrorHandler errorHandler, int requiredHours) {
        if (requiredHours > 0 && totalHours < requiredHours) {
            ErrorHandler.addErrorIfNotNull(errorHandler, "Il manque " + (requiredHours - totalHours) + " heures pour compléter le cycle ");
        }
    }
}