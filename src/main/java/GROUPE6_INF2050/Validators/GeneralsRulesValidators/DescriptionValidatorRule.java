package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
<<<<<<<< HEAD:src/main/java/GROUPE6_INF2050/Validators/GeneralsRulesValidators/DescriptionValidatorRule.java
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;
========
>>>>>>>> main:src/main/java/GROUPE6_INF2050/Validators/GeneralsRulesValidators/DescriptionValidator.java

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

    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!isDescriptionOver20Characters(jsonFileUtility, errorHandler)) {
            errorMessage.append("- La description doit contenir plus de 20 caractères.\n");
            return false;
        }
        return true;
    }
}