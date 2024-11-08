package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFieldsUtility;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;
import main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.*;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.ActivityHoursCalculator;
import main.java.com.Inf2050.Groupe6.Validators.PermitNumberValidator;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.Façade.HandleTotalHours;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.Façade.ValidateMinHoursByOrder;


public class JsonHandler {

    public static void handleJson(JsonFileUtility obj) throws Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        if (!JsonFieldsUtility.checkJsonFields(obj.getJsonObject(), errorHandler)) {
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Il y'a un champ manquant dans le JSON");
        }
        if (!JsonFieldsUtility.checkActivitiesFields(obj.getJsonObject(), errorHandler)) {
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Il y'a un champ manquant dans les Activités");
        }
        // On lui passe une seule fois le tableauJson afin de ne pas avoir à chaque appel un nouvel ajout
        CategoryMin17HoursValidator.addActivitiesIfCategoryInList(obj.getJsonObject().getJSONArray("activites"));

        validateJsonContent(obj, errorHandler);
        int totalHours = ActivityHoursCalculator.getTotalHours(obj.getJsonObject(), errorHandler);
        HandleTotalHours.handleHoursTotal(obj.getJsonObject(), totalHours, errorHandler);
        obj.save(errorHandler);
    }

    private static void validateJsonContent(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(obj.getJsonObject().getString("ordre"), errorHandler);
        int h = ValidateMinHoursByOrder.calculer(order, obj.getJsonObject().getJSONArray("activites"),errorHandler);
        System.out.println(h);
        checkIfPermitAndCycleValid(obj, errorHandler, order);
        getGeneralRulesValidators(obj, errorHandler);
        getCategoriesHoursValidators(obj, errorHandler);
    }

    private static void checkIfPermitAndCycleValid(JsonFileUtility obj, ErrorHandler errorHandler, ActivityOrder order) throws Groupe6INF2050Exception {
        if (!PermitNumberValidator.isPermitNumberValid(obj.getJsonObject(), errorHandler)) {
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Numero de permit non valide");
        }
        if(order == ActivityOrder.ORDER_NON_VALIDE){
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Ordre non valide");
        }

        Cycle cycle = Cycle.getCycleByLabel(obj.getJsonObject().getString("cycle"));
        if (!ActivityOrder.isCycleValidByOrder(cycle, order)) {
            errorHandler.addError("Cycle invalide pour l'ordre sélectionné.");
            obj.save(errorHandler);
            throw new Groupe6INF2050Exception("Cycle invalide pour l'ordre sélectionné");

        }
    }

    private static void getGeneralRulesValidators(JsonFileUtility obj, ErrorHandler errorHandler) {
        TransferredHoursValidator.validateTransferredHours(obj.getJsonObject(), errorHandler);
        ActivityCategoryValidator.validateCategory(obj.getJsonObject(), errorHandler);
        ActivityHoursTotal40Validator.calculateAndValidateTotalHours(obj.getJsonObject(), errorHandler);
    }

    private static void getCategoriesHoursValidators(JsonFileUtility obj, ErrorHandler errorHandler) {
        CategoryMin17HoursValidator.calculateAndValidateMin17Hours(obj.getJsonObject(), errorHandler);
    }
}