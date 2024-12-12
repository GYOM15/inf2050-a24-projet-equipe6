package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

/**
 * Valide la description des activités pour s'assurer qu'elle dépasse 20 caractères.
 */
public class DescriptionValidatorRule implements ValidationRule {

    /**
     * Vérifie que toutes les descriptions dans le fichier JSON ont plus de 20 caractères.
     *
     * @param jsonFileUtility Utilitaire JSON pour accéder aux activités.
     * @param errorHandler    Gestionnaire d'erreurs pour enregistrer les erreurs de validation.
     * @return true si toutes les descriptions ont plus de 20 caractères, sinon false.
     */
    public static boolean isDescriptionOver20Characters(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler) {
        for (int i = 0; i < jsonFileUtility.getJsonArray().size(); i++) {
            String description = jsonFileUtility.getJsonArray().getJSONObject(i).getString("description");
            if (description.length() < 20) {
                ErrorHandler.addErrorIfNotNull(errorHandler, "La description [ " + description + " ] doit contenir au moins 20 caractères. Le traitement du fichier ne peut pas être complété.");
                return false;
            }
        }
        return true;
    }

    /**
     * Valide si la description respecte une longueur minimale de 20 caractères.
     *
     * @param jsonFileUtility L'utilitaire JSON contenant les données à valider.
     * @param errorHandler    Le gestionnaire d'erreurs pour enregistrer les erreurs détectées.
     * @param errorMessage    Un accumulateur pour enregistrer les messages d'erreur.
     * @return true si la description contient plus de 20 caractères, false sinon.
     */
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!isDescriptionOver20Characters(jsonFileUtility, errorHandler)) {
            errorMessage.append("- La description doit contenir plus de 20 caractères.\n");
            return false;
        }
        return true;
    }
}