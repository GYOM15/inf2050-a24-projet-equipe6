package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;
import net.sf.json.JSONObject;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Cette classe permet de valider le numéro de permis.
 */
public class PermitNumberValidatorRule implements ValidationRule {


    /**
     * Variable thread-safe indiquant si le numéro de permis est valide.
     */
    private static final AtomicBoolean isValidPermitNumber = new AtomicBoolean();

    /**
     * Retourne l'état actuel de la validation du numéro de permis.
     *
     * @return true si le numéro de permis est valide, false sinon.
     */
    public static boolean isPermitNumberState() {
        return isValidPermitNumber.get();
    }

    /**
     * Valide le numéro de permis en fonction de l'ordre spécifié.
     *
     * @param jsonObject   L'objet JSON contenant les informations de validation.
     * @param errorMessage L'accumulateur pour les messages d'erreur.
     * @return true si le numéro de permis est valide, false sinon.
     */
    public static boolean isPermitNumberValid(JSONObject jsonObject, StringBuilder errorMessage) {
        boolean isValid = switch (ActivityOrder.searchFromJsonOrder(jsonObject.optString("ordre", null)).getOrderString()) {
            case "architectes" -> validateArchitectesPermit(jsonObject, errorMessage);
            case "psychologues" -> validatePsychologuesPermit(jsonObject, errorMessage);
            case "géologues" -> validateGeologuesPermit(jsonObject, errorMessage);
            case "podiatres" -> validatePodiatresPermit(jsonObject, errorMessage);
            default -> {
                errorMessage.append("- Le numéro de permis n'est pas valide pour l'ordre.\n");
                yield false;
            }
        };

        isValidPermitNumber.set(isValid);
        return isValid;
    }

    private static boolean validateArchitectesPermit(JSONObject jsonObject, StringBuilder errorMessage) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (isPermitInvalid(permitNumber, "[AT][0-9]{4}")) {
            appendError(
                    "Le numéro de permis pour l'ordre des architectes doit être non-null, sans espaces, commencer par A ou T, et être suivi de 4 chiffres.",
                    errorMessage
            );
            return false;
        }
        return true;
    }

    private static boolean validatePsychologuesPermit(JSONObject jsonObject, StringBuilder errorMessage) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (isPermitInvalid(permitNumber, "[0-9]{5}-[0-9]{2}")) {
            appendError(
                    "Le numéro de permis pour l'ordre des psychologues doit être non-null, sans espaces, commencer par 5 chiffres suivi d'un - et fini par 2 chiffres.",
                    errorMessage
            );
            return false;
        }
        return true;
    }

    private static boolean validateGeologuesPermit(JSONObject jsonObject, StringBuilder errorMessage) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        String lastName = jsonObject.optString("nom", "");
        String firstName = jsonObject.optString("prenom", "");

        if (isPermitInvalid(permitNumber, "[A-Z]{2}[0-9]{4}")) {
            appendError(
                    "Le numéro de permis pour l'ordre des géologues doit être non-null, sans espaces, commencer par 2 lettres majuscules et suivi par 4 chiffres.",
                    errorMessage
            );
            return false;
        }

        return validateInitialsForGeologues(errorMessage, permitNumber, lastName, firstName);
    }

    private static boolean validateInitialsForGeologues(StringBuilder errorMessage, String permitNumber, String lastName, String firstName) {
        if (!areInitialsValid(permitNumber, lastName, firstName)) {
            appendError(
                    "Le numéro de permis pour l'ordre des géologues doit commencer avec les premières lettres du nom et du prénom.",
                    errorMessage
            );
            return false;
        }
        return true;
    }

    private static boolean validatePodiatresPermit(JSONObject jsonObject, StringBuilder errorMessage) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (isPermitInvalid(permitNumber, "[0-9]{5}")) {
            appendError(
                    "Le numéro de permis pour l'ordre des podiatres doit être non-null, sans espaces, et contenir exactement 5 chiffres.",
                    errorMessage
            );
            return false;
        }
        return true;
    }

    private static boolean isPermitInvalid(String permitNumber, String pattern) {
        return permitNumber == null || permitNumber.contains(" ") || !permitNumber.matches(pattern);
    }

    private static boolean areInitialsValid(String permitNumber, String lastName, String firstName) {
        if (permitNumber == null || lastName.isEmpty() || firstName.isEmpty()) {
            return false;
        }
        char firstLetter = permitNumber.charAt(0);
        char secondLetter = permitNumber.charAt(1);
        return firstLetter == Character.toUpperCase(lastName.charAt(0)) &&
                secondLetter == Character.toUpperCase(firstName.charAt(0));
    }

    private static void appendError(String error, StringBuilder errorMessage) {
        errorMessage.append("- ").append(error).append("\n");
    }

    /**
     * Implémente la méthode de validation définie par l'interface `ValidationRule`.
     *
     * @param jsonFileUtility L'utilitaire pour accéder aux données JSON.
     * @param errorHandler    Le gestionnaire d'erreurs pour enregistrer les problèmes détectés.
     * @param errorMessage    L'accumulateur pour les messages d'erreur.
     * @return true si le numéro de permis est valide, false sinon.
     */
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        return isPermitNumberValid(jsonFileUtility.getJsonObject(), errorMessage);
    }
}
