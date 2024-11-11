package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Utilities.JsonFieldsUtility;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;
import main.java.com.Inf2050.Groupe6.Validators.GeneralsRulesValidators.DescriptionValidator;
import main.java.com.Inf2050.Groupe6.Validators.GeneralsRulesValidators.HoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.GeneralsRulesValidators.PermitNumberValidator;
import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;

public class HandleGeneralRulesValidator {

    public static boolean handleGeneralsRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        StringBuilder errorMessage = new StringBuilder("Échec de la validation pour les raisons suivantes :\n");
        boolean isValid = validatePermitNumber(jsonFileUtility, errorHandler, errorMessage) & validateDescription(jsonFileUtility, errorHandler, errorMessage) &
                validateHours(jsonFileUtility, errorHandler, errorMessage) & validateJsonFields(jsonFileUtility, errorHandler, errorMessage);
        if (!isValid) {
            jsonFileUtility.save(errorHandler);
            throw new Groupe6INF2050Exception(errorMessage.toString());
        }
        return true;
    }

    private static boolean validatePermitNumber(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!PermitNumberValidator.isPermitNumberValid(jsonFileUtility.getJsonObject(), errorHandler)) {
            errorMessage.append("- Le numéro de permis n'est pas valide.\n");
            return false;
        }
        return true;
    }

    private static boolean validateDescription(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!DescriptionValidator.isDescriptionOver20Characters(jsonFileUtility, errorHandler)) {
            errorMessage.append("- La description doit contenir plus de 20 caractères.\n");
            return false;
        }
        return true;
    }

    private static boolean validateHours(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!HoursValidator.areHoursPositive(jsonFileUtility, errorHandler)) {
            errorMessage.append("- Certaines activités contiennent un nombre d'heures négatif.\n");
            return false;
        }
        return true;
    }

    private static boolean validateJsonFields(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!JsonFieldsUtility.areAllFieldsValid(jsonFileUtility.getJsonObject(), errorHandler)) {
            errorMessage.append("- Certains champs requis sont manquants ou invalides.\n");
            return false;
        }
        return true;
    }
}