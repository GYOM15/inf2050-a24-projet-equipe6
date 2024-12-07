package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Valide le total des heures nécessaires pour les géologues afin de compléter un cycle donné.
 */
public class GeologueTotalHoursValidator {

    // Variable thread-safe pour stocker l'état de validation
    private static final AtomicBoolean isComplet = new AtomicBoolean();

    /**
     * Retourne l'état actuel de la validation.
     *
     * @return true si les heures nécessaires sont complètes, false sinon.
     */
    public static boolean isComplet() {
        return isComplet.get();
    }

    /**
     * Valide si le total d'heures accumulées pour les géologues est suffisant pour compléter le cycle donné.
     *
     * @param cycle       Le cycle d'évaluation (exemple : CYCLE_2023_2025)
     * @param totalHours  Le nombre total d'heures accumulées par le géologue
     * @param errorHandler L'instance ErrorHandler pour enregistrer les erreurs éventuelles
     */
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = 55; // Nombre d'heures requis pour compléter un cycle
        if (totalHours < requiredHours) {
            isComplet.set(false); // Marque l'état comme incomplet
            ErrorHandler.addErrorIfNotNull(errorHandler,
                    "Il manque " + (requiredHours - totalHours) + " heures pour compléter le cycle " + cycle.getLabel());
        } else {
            isComplet.set(true); // Marque l'état comme complet
        }
    }
}