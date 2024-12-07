package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Handlers.ErrorHandler;

/**
 * Règle de validation pour les informations personnelles (nom, prénom, sexe).
 */
public class PersonValidatorRule implements ValidationRule {
    private static Integer gender;

    public static Integer getGender() {
        return gender;
    }

    /**
     * Valide les informations personnelles extraites d'un fichier JSON.
     *
     * @param jsonFileUtility Utilitaire pour lire les données JSON.
     * @param errorHandler    Gestionnaire d'erreurs pour enregistrer les problèmes.
     * @param errorMessage    StringBuilder pour accumuler les messages d'erreur.
     * @return true si les informations sont valides, sinon false.
     */
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean isValid = true;
        gender = jsonFileUtility.getJsonObject().has("sexe") ? jsonFileUtility.getJsonObject().optInt("sexe") : null;
        if (!validateLastName(jsonFileUtility.getJsonObject().optString("nom", null), errorHandler, errorMessage)) {
            isValid = false;
        }
        if (!validateFirstName(jsonFileUtility.getJsonObject().optString("prenom", null), errorHandler, errorMessage)) {
            isValid = false;
        }
        if (!validateGender(gender, errorHandler, errorMessage)) {
            isValid = false;
        }
        return isValid;
    }

    private boolean validateLastName(String lastName, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (lastName == null || !lastName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ-]+$")) {
            String error = "Le champ nom est requis ou invalide.";
            ErrorHandler.addErrorIfNotNull(errorHandler, error);
            errorMessage.append("- ").append(error).append("\n");
            return false;
        }
        return true;
    }

    private boolean validateFirstName(String firstName, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (firstName == null || !firstName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ-]+$")) {
            String error = "Le champ prénom est requis ou invalide.";
            ErrorHandler.addErrorIfNotNull(errorHandler, error);
            errorMessage.append("- ").append(error).append("\n");
            return false;
        }
        return true;
    }

    private boolean validateGender(Integer gender, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (gender == null || gender < 0 || gender > 2) {
            String error = "Le champ sexe doit être conforme à la norme ISO 5218 (0=Non spécifié, 1=Masculin, 2=Féminin).";
            ErrorHandler.addErrorIfNotNull(errorHandler, error);
            errorMessage.append("- ").append(error).append("\n");
            return false;
        }
        return true;
    }

}