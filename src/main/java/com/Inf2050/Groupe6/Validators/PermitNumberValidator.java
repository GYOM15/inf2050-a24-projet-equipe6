package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;

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
    public static boolean isPermitNumberValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        String permitNumber = jsonObject.optString("numero_de_permis", null);
        if (permitNumber == null || permitNumber.contains(" ") || !permitNumber.matches("[ATMK][0-9]{4}")) {
            errorHandler.addPermitError(permitNumber, "Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres.");
            return false;
        }
        return true;
    }

}
