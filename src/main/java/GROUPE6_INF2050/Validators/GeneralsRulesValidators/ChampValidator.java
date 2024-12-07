package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import net.sf.json.JSONObject;

public class ChampValidator {
    public static boolean isLastName(JSONObject jsonObject, ErrorHandler errorHandler) {
        String lastName = jsonObject.optString("nom", null);
        if (lastName == null || lastName.contains(" ") || !lastName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ-]+$")) {
            errorHandler.addError("Le nom n'est pas valide.");
            return false;
        }
        return true;
    }

    public static boolean isFirtsName(JSONObject jsonObject, ErrorHandler errorHandler) {
        String firstName = jsonObject.optString("prenom", null);
        if (firstName == null || firstName.contains(" ") || !firstName.matches("^[a-zA-ZÀ-ÖØ-öø-ÿ-]+$")) {
            errorHandler.addError("Le prénom n'est pas valide.");
            return false;
        }
        return true;
    }

    public static boolean isGender(JSONObject jsonObject, ErrorHandler errorHandler) {
        String gender = jsonObject.optString("sexe", null);
        if (gender == null || !gender.matches("[0-2]")) {
            errorHandler.addError("Le sexe n'est pas valide.");
            return false;
        }
        return true;
    }


}
