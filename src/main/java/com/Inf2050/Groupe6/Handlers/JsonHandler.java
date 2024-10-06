package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileUtility;
import main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.*;
import main.java.com.Inf2050.Groupe6.Validators.CycleValidator;
import main.java.com.Inf2050.Groupe6.Validators.PermitNumberValidator;

import static java.lang.System.exit;

/**
 * Cette classe est dédiée à la gestion du traitement du fichier JSON,
 * en appliquant les différentes étapes de validation et en sauvegardant les erreurs rencontrées.
 */
public class JsonHandler {
    /**
     * Cette méthode principale gère l'exécution des étapes de validation et de sauvegarde du fichier JSON.
     *
     * @param obj Le fichier JSON encapsulé dans une instance de JsonFileUtility.
     * @throws Groupe6INF2050Exception Si une erreur est rencontrée pendant le traitement.
     */
    public static void handleJson(JsonFileUtility obj) throws Groupe6INF2050Exception {
        ErrorHandler errorHandler = new ErrorHandler();
        obj.loadAndValid();
        validateJsonContent(obj, errorHandler);
        obj.save(errorHandler);
    }

    /**
     * Applique les différentes validations du contenu JSON, y compris la validation des heures transférées,
     * la catégorie, et les heures minimales pour les activités.
     *
     * @param obj L'objet JsonFileUtility contenant le fichier JSON à valider.
     * @param errorHandler L'instance de ErrorHandler pour stocker les erreurs rencontrées.
     */
    private static void validateJsonContent(JsonFileUtility obj, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        if (!PermitNumberValidator.isPermitNumberValid(obj.getJsonObject(), errorHandler)){
            obj.save(errorHandler);
            exit(-1);
        }
        getGeneralRulesValidators(obj, errorHandler);
        getCategoriesHoursValidators(obj, errorHandler);
    }

    /**
     * Valide les règles générales comme les heures transférées, la catégorie et les heures minimales.
     *
     * @param obj L'objet JsonFileUtility avec le contenu JSON à valider.
     * @param errorHandler Le gestionnaire d'erreurs.
     */
    private static void getGeneralRulesValidators(JsonFileUtility obj, ErrorHandler errorHandler) {
        TransferredHoursValidator.validateTransferredHours(obj.getJsonObject(), errorHandler);
        ActivityCategoryValidator.validateCategory(obj.getJsonObject(), errorHandler);
        ActivityHoursTotal40Validator.isActivitiesTotalHoursMin40(obj.getJsonObject(), errorHandler);
        CycleValidator.checkIfActivityDateInCycle(obj.getJsonObject(), errorHandler);
        CycleValidator.validateCycle(obj.getJsonObject(), errorHandler);
    }

    /**
     * Valide les règles spécifiques aux différentes catégories d'activités.
     *
     * @param obj L'objet JsonFileUtility avec le contenu JSON à valider.
     * @param errorHandler Le gestionnaire d'erreurs
     */
    private static void getCategoriesHoursValidators(JsonFileUtility obj, ErrorHandler errorHandler) {
        CategoryMin17HoursValidator.isCategoryMin17Hours(obj.getJsonObject(), errorHandler);
        CategoryMaxHoursValidator.validatePresentationHours(obj.getJsonObject(), errorHandler );
        CategoryMaxHoursValidator.validateRedactionProHours(obj.getJsonObject(), errorHandler );
        CategoryMaxHoursValidator.validateGroupDiscussionHours(obj.getJsonObject(), errorHandler );
        CategoryMaxHoursValidator.validateProjetDeRechercheHours(obj.getJsonObject(), errorHandler );
    }
}
