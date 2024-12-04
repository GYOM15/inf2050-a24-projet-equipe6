package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class PsychologueTotalHoursValidator {

    // Variable thread-safe pour stocker l'état de validation
    private static final AtomicBoolean isComplet = new AtomicBoolean();

    /**
     * Retourne l'état actuel de la variable isComplet.
     *
     * @return true si les heures sont complètes, false sinon.
     */
    public static boolean isComplet() {
        return isComplet.get();
    }

    /**
     * Valide si le total d'heures accumulées pour les psychologues est suffisant pour compléter le cycle donné.
     *
     * @param cycle       Le cycle d'évaluation (exemple : CYCLE_2023_2025)
     * @param totalHours  Le nombre total d'heures accumulées par le psychologue
     * @param errorHandler L'instance ErrorHandler pour enregistrer les erreurs éventuelles
     */
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = 90; // Le nombre d'heures requis pour tous les cycles
        if (totalHours < requiredHours) {
            isComplet.set(false); // Marque l'état comme incomplet
            ErrorHandler.addErrorIfNotNull(errorHandler,
                    "Il manque " + (requiredHours - totalHours) + " heures pour compléter le cycle " + cycle.getLabel());
        } else {
            isComplet.set(true); // Marque l'état comme complet
        }
    }
}