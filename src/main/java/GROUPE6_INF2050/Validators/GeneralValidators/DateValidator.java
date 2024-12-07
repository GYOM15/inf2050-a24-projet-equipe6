package GROUPE6_INF2050.Validators.GeneralValidators;


import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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
    }
}