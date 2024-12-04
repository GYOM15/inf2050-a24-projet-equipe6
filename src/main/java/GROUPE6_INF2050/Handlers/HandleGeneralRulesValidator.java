package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.*;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire centralisé pour appliquer les règles générales de validation.
 */
public class HandleGeneralRulesValidator {
    private static boolean isGeneralRuleValid = false;
    private final List<ValidationRule> validationRules;

    public boolean isGeneralRuleValid() {
        return isGeneralRuleValid;
    }

    /**
     * Constructeur qui initialise et enregistre les règles générales de validation.
     */
    public HandleGeneralRulesValidator() {
        validationRules = new ArrayList<>();
        registerValidationRules();
    }

    /**
     * Enregistre toutes les règles générales de validation.
     */
    private void registerValidationRules() {
        validationRules.add(new PermitNumberValidatorRule());
        validationRules.add(new DescriptionValidatorRule());
        validationRules.add(new HoursValidatorRule());
        validationRules.add(new JsonFieldsValidatorRule());
        validationRules.add(new OrderValidatorRule());
        validationRules.add(new CycleValidatorRule());
        validationRules.add(new PersonValidatorRule());
    }

    /**
     * Applique toutes les règles de validation sur un fichier JSON.
     *
     * @param jsonFileUtility Utilitaire pour manipuler le fichier JSON.
     * @param errorHandler    Gestionnaire des erreurs de validation.
     * @return true si toutes les validations passent, sinon false.
     * @throws Groupe6INF2050Exception Si une ou plusieurs règles échouent.
     */
    public boolean handleGeneralsRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler) throws Groupe6INF2050Exception {
        StringBuilder errorMessage = new StringBuilder("Échec de la validation pour les raisons suivantes :\n");
        boolean isValid = true;
        for (ValidationRule rule : validationRules) {
            isValid &= rule.validate(jsonFileUtility, errorHandler, errorMessage);
        }
        if (!isValid) {
            isGeneralRuleValid = false;
            jsonFileUtility.save(errorHandler);
            throw new Groupe6INF2050Exception(errorMessage.toString());
        }
        isGeneralRuleValid = true;
        return true;
    }


}