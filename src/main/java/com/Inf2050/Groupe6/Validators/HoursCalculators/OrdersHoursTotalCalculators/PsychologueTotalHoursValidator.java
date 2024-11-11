package com.Inf2050.Groupe6.Validators.HoursCalculators.OrdersHoursTotalCalculators;


import org.example.Inf2050.Groupe6.Enums.Cycle;
import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;

public class PsychologueTotalHoursValidator {

    /**
     * Valide si le total d'heures accumulées pour les psychologues est suffisant pour compléter le cycle donné.
     *
     * @param cycle Le cycle d'évaluation (ex. : CYCLE_2023_2025)
     * @param totalHours Le nombre total d'heures accumulées par le psychologue
     * @param errorHandler L'instance ErrorHandler pour enregistrer les erreurs éventuelles
     */
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = 90;
        if (totalHours < requiredHours) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Il manque " + (requiredHours - totalHours) +
                    " heures pour compléter le cycle " + cycle.getLabel());
        }
    }
}