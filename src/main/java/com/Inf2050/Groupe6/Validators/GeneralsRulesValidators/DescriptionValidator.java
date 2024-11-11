package main.java.com.Inf2050.Groupe6.Validators.GeneralsRulesValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;

/**
 * Valide la description des activités pour s'assurer qu'elle dépasse 20 caractères.
 */
public class DescriptionValidator {

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
                ErrorHandler.addErrorIfNotNull(errorHandler,
                        "La description [ " + description + " ] doit contenir au moins 20 caractères. Le traitement du fichier ne peut pas être complété.");
                return false;
            }
        }
        return true;
    }
}