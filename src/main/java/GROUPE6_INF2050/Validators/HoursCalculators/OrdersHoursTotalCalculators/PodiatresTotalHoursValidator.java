package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Valide le total des heures nécessaires pour les podiatres afin de compléter un cycle donné.
 */
public class PodiatresTotalHoursValidator {

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
     * Valide si le total d'heures accumulées pour les podiatres est suffisant pour compléter le cycle donné.
     *
     * @param cycle        Le cycle pour lequel la validation est effectuée
     * @param totalHours   Le nombre total d'heures effectuées
     * @param errorHandler Le gestionnaire d'erreurs pour collecter les erreurs de validation
     */
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = 60; // Nombre d'heures requis pour compléter un cycle
        if (totalHours < requiredHours) {
            isComplet.set(false); // Marque l'état comme incomplet
            ErrorHandler.addErrorIfNotNull(errorHandler,
                    "Il manque " + (requiredHours - totalHours) + " heures pour compléter le cycle " + cycle.getLabel());
        } else {
            isComplet.set(true); // Marque l'état comme complet
        }
    }
}