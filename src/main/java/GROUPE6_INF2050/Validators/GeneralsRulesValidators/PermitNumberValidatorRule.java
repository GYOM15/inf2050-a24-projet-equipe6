package GROUPE6_INF2050.Validators.GeneralsRulesValidators;


import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;
import net.sf.json.JSONObject;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cette classe permet de valider le numéro de permis.
 * Références pour la manipulation des strings : https://www.w3schools.com/java/java_ref_string.asp
 */
public class PermitNumberValidatorRule implements ValidationRule {

    // Variable thread-safe pour stocker l'état de validation
    private static final AtomicBoolean isValidPermitNumber = new AtomicBoolean();

    /**
     * Retourne l'état actuel de la validation du numéro de permis.
     *
     * @return true si le numéro de permis est valide, false sinon.
     */
    public boolean isPermitNumberState() {
        return isValidPermitNumber.get();
    }

    public static boolean isPermitNumberValid(JSONObject jsonObject, ErrorHandler errorHandler){
        boolean isValid = switch (ActivityOrder.searchFromJsonOrder(jsonObject.optString("ordre", null)).getOrderString()) {
            case "architectes" -> isPermitNumberArchitectesValid(jsonObject, errorHandler);
            case "psychologues" -> isPermitNumberPsychologuesValid(jsonObject, errorHandler);
            case "géologues" -> isPermitNumberGeologuesValid(jsonObject, errorHandler);
            case "podiatres" -> isPermitNumberPodiatresValid(jsonObject, errorHandler);
            default -> false;
        };
        isValidPermitNumber.set(isValid);
        return isValid;
    }

    private static boolean isPermitNumberArchitectesValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (permitNumber == null || permitNumber.contains(" ") || !permitNumber.matches("[AT][0-9]{4}")) {
            errorHandler.addPermitError(permitNumber, "Le numéro de permis pour l'ordre des architectes doit être non-null, sans espaces, commencer par A ou T, et être suivi de 4 chiffres.");
            return false;
        }
        return true;
    }

    private static boolean isPermitNumberPsychologuesValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (permitNumber == null || permitNumber.contains(" ") || !permitNumber.matches("[0-9]{5}-[0-9]{2}")) {
            errorHandler.addPermitError(permitNumber, "Le numéro de permis pour l'ordre des psychologues doit être non-null, sans espaces, commencer par 5 chiffres suivi d'un - et fini par 2 chiffres.");
            return false;
        }
        return true;
    }

    private static boolean isPermitNumberGeologuesValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        String lastName = jsonObject.getString("nom");
        String firstName = jsonObject.getString("prenom");
        if (permitNumber == null || permitNumber.contains(" ") || !permitNumber.matches("[A-Z]{2}[0-9]{4}")) {
            errorHandler.addPermitError(permitNumber, "Le numéro de permis pour l'ordre des géologues doit être non-null, sans espaces, commencer par 2 lettres majuscules et suivi par 4 chiffres.");
            return false;
        }
        char firstLetter = permitNumber.charAt(0);
        char secondLetter = permitNumber.charAt(1);
        if (firstLetter != Character.toUpperCase(lastName.charAt(0)) ||
                secondLetter != Character.toUpperCase(firstName.charAt(0))) {
            errorHandler.addPermitError(permitNumber, "Le numéro de permis pour l'ordre des géologues doit commencer avec les première lettre du nom et du prénom");
            return false;
        }
        return true;
    }

    private static boolean isPermitNumberPodiatresValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (permitNumber == null || permitNumber.contains(" ") || !permitNumber.matches("[0-9]{5}")) {
            errorHandler.addPermitError(permitNumber, "Le numéro de permis pour l'ordre des podiatres doit être non-null, sans espaces,  doit contenir 5 chiffres.");
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        if (!isPermitNumberValid(jsonFileUtility.getJsonObject(), errorHandler)) {
            errorMessage.append("- Le numéro de permis n'est pas valide.\n");
            return false;
        }
        return true;
    }
}
