package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

public class OrderValidatorRule implements ValidationRule {
    /**
     * Valide que l'ordre spécifié dans le fichier JSON est valide.
     *
     * @param jsonFileUtility L'utilitaire pour accéder aux données JSON.
     * @param errorHandler    Le gestionnaire d'erreurs pour enregistrer les erreurs détectées.
     * @param errorMessage    Un accumulateur pour stocker les messages d'erreur détaillés.
     * @return true si l'ordre est valide, false sinon.
     */
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        String orderLabel = ActivityOrder.searchFromJsonOrder(jsonFileUtility.getJsonObject().optString("ordre",null)).getOrderString();
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(orderLabel);
        if (order == ActivityOrder.ORDER_NON_VALIDE) {
            errorMessage.append("- L'ordre ").append(orderLabel).append(" n'est pas valide.\n");
            return false;
        }
        return true;
    }
}