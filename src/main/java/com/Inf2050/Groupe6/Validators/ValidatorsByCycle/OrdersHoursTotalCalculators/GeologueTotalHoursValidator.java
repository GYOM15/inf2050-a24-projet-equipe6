package main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.OrdersHoursTotalCalculators;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;

public class GeologueTotalHoursValidator{
    public static void validateByCycle(Cycle cycle, int totalHours, ErrorHandler errorHandler) {
        int requiredHours = 53;
        if (totalHours < requiredHours) {
            errorHandler.addError("Il manque " + (requiredHours - totalHours) + " heures pour compléter le cycle " + cycle.getLabel());
        }
    }
}
