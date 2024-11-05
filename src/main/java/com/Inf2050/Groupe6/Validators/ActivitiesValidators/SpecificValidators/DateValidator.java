package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

<<<<<<< HEAD
public class DateValidator{
    /**
     * Permet de parser les dates et renvoie une exception, gère automatiquement les années bissextiles .
     * @param dateValue la date reçu du Json
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs si le nombre d'heures est négatif.
     * @return true ou false
     */
    public static boolean validate(String dateValue, ErrorHandler errorHandler) {
        try {
            LocalDate date = LocalDate.parse(dateValue);
            return true;
        } catch (DateTimeParseException e) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Format incorrect ou année non bisextille : " + dateValue);
            return false;
        }
=======
public class DateValidator {

    public static LocalDate validateDate(String dateValue, ErrorHandler errorHandler) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateValue);
            if (date.getMonthValue() == 2 && date.getDayOfMonth() == 29 && !date.isLeapYear()) {
                if (errorHandler != null) {
                    errorHandler.addError("Date d'activité invalide : " + dateValue + " (année non bissextile)");
                }
                return null;
            }
        } catch (DateTimeParseException e) {
            if (errorHandler != null) {
                errorHandler.addError("Date d'activité invalide ou format incorrect (attendu: yyyy-MM-dd) : " + dateValue);
            }
        }
        return date;
>>>>>>> 473f43b (On stash la validation du cycle)
    }
}