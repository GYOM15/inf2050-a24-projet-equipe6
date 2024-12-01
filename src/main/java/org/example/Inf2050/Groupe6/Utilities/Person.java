package org.example.Inf2050.Groupe6.Utilities;

import net.sf.json.JSONObject;
import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;

public class Person {
    private final String lastName;
    private final String firstName;
    private final Integer gender;

    public Person(String lastName, String firstName, Integer gender) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
    }

    public static Person fromJson(JSONObject jsonObject) {
        String lastName = jsonObject.optString("nom", null);
        String firstName = jsonObject.optString("prenom", null);
        Integer gender = jsonObject.has("sexe") ? jsonObject.optInt("sexe") : null;
        return new Person(lastName, firstName, gender);
    }

    public boolean validate(ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean isValid = true;
        if (!validateLastName(errorHandler)) {
            errorMessage.append("- Le champ nom est requis ou invalide.\n");
            isValid = false;
        }
        if (!validateFirstName(errorHandler)) {
            errorMessage.append("- Le champ prénom est requis ou invalide.\n");
            isValid = false;
        }
        if (!validateGender(errorHandler)) {
            errorMessage.append("- Le champ sexe est requis ou invalide.\n");
            isValid = false;
        }
        return isValid;
    }

    private boolean validateLastName(ErrorHandler errorHandler) {
        if (lastName == null || lastName.contains(" ") || !lastName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ-]+$")) {
            ErrorHandler.addErrorIfNotNull(errorHandler, "Le champ nom est requis ou invalide.");
            return false;
        }
        return true;
    }

    private boolean validateFirstName(ErrorHandler errorHandler) {
        if (firstName == null || firstName.contains(" ") || !firstName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ-]+$")) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Le champ prénom est requis ou invalide.");
            return false;
        }
        return true;
    }

    private boolean validateGender(ErrorHandler errorHandler) {
        if (gender == null || !String.valueOf(gender).matches("[0-2]")) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Le champ sexe doit être conforme à la norme ISO 5218 (0, 1 ou 2).");
            return false;
        }
        return true;
    }
}