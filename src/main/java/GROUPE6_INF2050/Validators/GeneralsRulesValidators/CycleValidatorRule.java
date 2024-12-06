package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

public class CycleValidatorRule implements ValidationRule {
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        Cycle cycle = Cycle.getCycleByLabel(jsonFileUtility.getJsonObject().optString("cycle",null));
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(jsonFileUtility.getJsonObject().optString("ordre",null));
        if (cycle == null || ActivityOrder.isCycleValidByOrder(cycle, order)) {
            errorMessage.append("- Le cycle ").append(cycle.getLabel()).append(" n'est pas valide pour l'ordre ").append(order.getOrderString()).append(".\n");
            return false;
        }
        return true;
    }
}