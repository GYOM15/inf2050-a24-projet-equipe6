package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DateValidator{
    /**
     * Permet de parser les dates et renvoie une exception, gère automatiquement les années bissextiles .
     * @param dateValue
     * @param errorHandler
     * @return
     */
    public static boolean validate(String dateValue, ErrorHandler errorHandler) {
        try {
            LocalDate date = LocalDate.parse(dateValue);
            return true;
        } catch (DateTimeParseException e) {
            if(errorHandler != null){
                errorHandler.addError("Format incorrect ou année non bisextille : " + dateValue);
            }
            return false;
        }
    }
}