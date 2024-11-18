package GROUPE6_INF2050.Validators.GeneralsRulesValidators;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;

/**
 * Valide les heures des activités pour s'assurer qu'elles sont positives.
 */
public class HoursValidator {

    /**
     * Vérifie que toutes les activités dans le fichier JSON ont un nombre d'heures positif.
     *
     * @param jsonFileUtility Utilitaire JSON pour accéder aux activités
     * @return true si toutes les heures sont positives, sinon false
     */
    public static boolean areHoursPositive(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler) {
        for (int i = 0; i < jsonFileUtility.getJsonArray().size(); i++) {
            if (jsonFileUtility.getJsonArray().getJSONObject(i).getInt("heures") < 0) {
                ErrorHandler.addErrorIfNotNull(errorHandler, "Le champ " +jsonFileUtility.getJsonArray().getJSONObject(i).getString("description")
                        + "contient des heures négatives, le traitement du fichier ne peut pas être complété" );
                return false;
            }
        }
        return true;
    }
}