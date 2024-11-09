package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.ActivityHoursCalculator;
import main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.CalculateMinHoursByOrderCategoryConditions;

public class JsonHandler {

    public static void handleJson(JsonFileUtility obj) throws Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        if(HandleGeneralRulesValidator.handleGeneralsRules(obj, errorHandler)){ System.out.println("Les validations préalables avant traitement sont correctes");};
        performValidationSequence(obj, errorHandler);
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHoursByCategory.handleHoursTotal(obj, totalHours, errorHandler);

        obj.save(errorHandler);
    }

    // Regroupe les méthodes principales de validation du contenu JSON
    private static void performValidationSequence(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"), errorHandler);
        checkOrderAndCycle(obj, errorHandler, order);
        CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(order, obj.getJsonArray(), errorHandler);
    }

    // Vérifie l'ordre et le cycle
    private static void checkOrderAndCycle(JsonFileUtility obj, ErrorHandler errorHandler, ActivityOrder order) throws Groupe6INF2050Exception {
        if (order == ActivityOrder.ORDER_NON_VALIDE) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Order non valide.");
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Ordre non valide");
        }
        Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
        if (ActivityOrder.isCycleValidByOrder(cycle, order)) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Cycle invalide pour l'ordre sélectionné.");
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Cycle invalide pour l'ordre sélectionné");
        }
    }

}