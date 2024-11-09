package main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.OrdersHoursTotalCalculators;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;

public class ArchitectesTotalHoursValidator{
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = switch (cycle) {
            case CYCLE_2023_2025 -> 40;
            case CYCLE_2020_2022, CYCLE_2018_2020 -> 42;
            default -> 0;
        };
        if (totalHours < requiredHours && requiredHours > 0) {
            errorHandler.addError("Il manque " + (requiredHours - totalHours) + " heures pour compléter le cycle " + cycle.getLabel());
        }
    }
}
