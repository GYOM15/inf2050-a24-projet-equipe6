package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe permet de valider le numéro de permis.
 * Références pour la manipulation des strings : https://www.w3schools.com/java/java_ref_string.asp
 */
public class PermitNumberValidator {

    /**
     * Valide le numéro de permis tout en vérifiant si la première lettre est parmi (A, T, M, ou K)
     *  et si les 4 caractères suivants sont des chiffres.
     *
     * @param jsonObject    Le JSON contenant le numéro de permis à vérifier.
     * @param errorHandler  L'objet ErrorHandler pour stocker les erreurs liées au numéro de permis.
     * @return true si le numéro de permis est valide, sinon false.
     */
    public static boolean validatePermitNumber(JSONObject jsonObject, ErrorHandler errorHandler) {
        /*
         * Stocke les erreurs spécifiques à la validation du permis pour enfin les stocker dans un seul index dans le errorHandler.
         * De ce fait on evite plusieurs messages d'erreurs dans le tableau pour les mêmes vérifications
         **/
        List<String> errors = new ArrayList<>();

        String permitNumber = jsonObject.optString("numero_de_permis", null);

        if (permitNumber == null) {
            errors.add("Le numéro de permis ne peut pas être null.");
        } else {
            if (permitNumber.contains(" ")) {
                errors.add("Le numéro de permis ne doit pas contenir d'espaces.");
            }

            char firstChar = permitNumber.charAt(0);

            if ("ATMK".indexOf(firstChar) == -1 || !Character.isUpperCase(firstChar)) {
                errors.add("Le numéro de permis doit commencer par une lettre majuscule (A, T, M, ou K).");
            }

            if (!permitNumber.substring(1).chars().allMatch(Character::isDigit)) {
                errors.add("Les quatre caractères suivants la première lettre doivent être des chiffres.");
            }
        }

        if (!errors.isEmpty()) {
            errorHandler.addPermitError(permitNumber, String.join(" Aussi, ", errors));
            return false;
        }

        return true;
    }
}
