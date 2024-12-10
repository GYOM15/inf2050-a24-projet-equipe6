package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;
import net.sf.json.JSONObject;
import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.util.Arrays;
import java.util.List;

public class JsonFieldsValidatorRule implements ValidationRule {

    private static final List<String> requiredKeys = Arrays.asList(
            "numero_de_permis", "cycle", "ordre", "activites"
    );

    private static final List<String> requiredActivitiesKeys = Arrays.asList(
            "description", "categorie", "heures", "date"
    );

    /**
     * Validates all required fields in the JSON object and logs specific error messages.
     *
     * @param jsonObject   The JSON object to validate.
     * @param errorHandler The error handler to log validation errors.
     * @param errorMessage The error message accumulator for detailed validation results.
     * @return true if all fields are valid; false otherwise.
     */
    public static boolean areAllFieldsValid(JSONObject jsonObject, ErrorHandler errorHandler, StringBuilder errorMessage) {
        return checkRequiredKeys(jsonObject, errorHandler, errorMessage) &&
                checkArchitecteSpecificKey(jsonObject, errorHandler, errorMessage) &&
                validateCycleByOrder(jsonObject,errorHandler, errorMessage) &&
                checkActivitiesFields(jsonObject, errorHandler, errorMessage);
    }


    private static boolean checkRequiredKeys(JSONObject jsonObject, ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean isValid = true;
        for (String key : requiredKeys) {
            if (!jsonObject.containsKey(key)) {
                String error = "La clé obligatoire '" + key + "' est manquante dans le fichier JSON.";
                ErrorHandler.addErrorIfNotNull(errorHandler, error);
                errorMessage.append("- ").append(error).append("\n");
                isValid = false;
            }
        }return isValid;
    }

    private static boolean checkArchitecteSpecificKey(JSONObject jsonObject, ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean hasTransferHoursKey = jsonObject.containsKey("heures_transferees_du_cycle_precedent");
        if ("architectes".equalsIgnoreCase(jsonObject.optString("ordre")) != hasTransferHoursKey) {
            String error = hasTransferHoursKey ? "La clé 'heures_transferees_du_cycle_precedent' ne doit être utilisée que pour l'ordre 'architectes'."
                    : "La clé 'heures_transferees_du_cycle_precedent' est requise pour l'ordre 'architectes'.";
            ErrorHandler.addErrorIfNotNull(errorHandler, error);
            errorMessage.append("- ").append(error).append("\n");
            return false;
        }return true;
    }

    private static boolean validateCycleByOrder(JSONObject jsonObject, ErrorHandler errorHandler, StringBuilder errorMessage) {
        Cycle cycle = Cycle.getCycleByLabel(jsonObject.optString("cycle", null));
        if (cycle == null || ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.searchFromJsonOrder(jsonObject.optString("ordre",null)))) {
            String error = "Le cycle '" + (cycle != null ? cycle.getLabel() : "inconnu") + "' n'est pas valide pour l'ordre '" + ActivityOrder.searchFromJsonOrder(jsonObject.optString("ordre",null)).getOrderString() + "'.";
            ErrorHandler.addErrorIfNotNull(errorHandler, error);
            errorMessage.append("- ").append(error).append("\n");
            return false;
        }
        return true;
    }

    private static boolean checkActivitiesFields(JSONObject jsonObject, ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean isValid = true;
        for (int i = 0; i < jsonObject.getJSONArray("activites").size(); i++) {
            for (String key : requiredActivitiesKeys) {
                if (!jsonObject.getJSONArray("activites").getJSONObject(i).containsKey(key)) {
                    String error = "L'activité " + (i + 1) + " manque la clé obligatoire '" + key + "'.";
                    ErrorHandler.addErrorIfNotNull(errorHandler, error);
                    errorMessage.append("- ").append(error).append("\n");
                    isValid = false;
                }
            }
        }return isValid;
    }

    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        return areAllFieldsValid(jsonFileUtility.getJsonObject(), errorHandler, errorMessage);
    }
}