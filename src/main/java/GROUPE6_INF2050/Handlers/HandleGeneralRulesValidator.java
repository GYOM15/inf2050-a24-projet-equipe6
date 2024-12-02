package GROUPE6_INF2050.Handlers;


import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Utilities.JsonFieldsUtility;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.Person;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.DescriptionValidator;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.HoursValidator;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PermitNumberValidator;

public class HandleGeneralRulesValidator {

    public static boolean handleGeneralsRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        StringBuilder errorMessage = new StringBuilder("Échec de la validation pour les raisons suivantes :\n");
        Person person = createPersonFromJson(jsonFileUtility);
        boolean isValid = validatePermitNumber(jsonFileUtility, errorHandler, errorMessage) &
                validateDescription(jsonFileUtility, errorHandler, errorMessage) &
                validateHours(jsonFileUtility, errorHandler, errorMessage) &
                validateJsonFields(jsonFileUtility, errorHandler, errorMessage) &
                person.validate(errorHandler, errorMessage); // Validation complète de la personne

        if (!isValid) {
            jsonFileUtility.save(errorHandler);
            throw new Groupe6INF2050Exception(errorMessage.toString());
        }
        return true;
    }

    private static Person createPersonFromJson(JsonFileUtility jsonFileUtility) {
        return Person.fromJson(jsonFileUtility.getJsonObject());
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