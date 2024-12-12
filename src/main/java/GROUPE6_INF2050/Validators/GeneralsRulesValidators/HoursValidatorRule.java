package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

/**
 * Valide les heures des activités pour s'assurer qu'elles sont positives.
 */
public class HoursValidatorRule implements ValidationRule {

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


/**
 * Valide que toutes les activités contiennent un nombre d'heures positif.
 *
 * @param jsonFileUtility L'utilitaire JSON contenant les données des activités à valider.
 * @param errorHandler    Le gestionnaire d'erreurs pour enregistrer les erreurs détectées.
 * @param errorMessage    Un accumulateur pour enregistrer les messages d'erreur.
 * @return true si toutes les activités ont un nombre **/
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!areHoursPositive(jsonFileUtility, errorHandler)) {
            errorMessage.append("- Certaines activités contiennent un nombre d'heures négatif.\n");
            return false;
        }
        return true;
    }
}