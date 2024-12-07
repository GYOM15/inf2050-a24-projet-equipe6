package GROUPE6_INF2050.Validators.GeneralsRulesValidators;

import GROUPE6_INF2050.Enums.ActivityOrder;
import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.CycleValidator;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

public class CycleValidatorRule implements ValidationRule {
    @Override
    public boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        Cycle cycle = CycleValidator.getCycle();
        ActivityOrder order = ActivityOrder.getCurrentOrder();
        if (cycle == null || ActivityOrder.isCycleValidByOrder(cycle, order)) {
            errorMessage.append("- Le cycle ").append(cycle.getLabel()).append(" n'est pas valide pour l'ordre ").append(order.getOrder()).append(".\n");
            return false;
        }
        return true;
    }
}