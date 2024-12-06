package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.*;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces.ValidationRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire centralisé pour appliquer les règles générales de validation.
 */
public class HandleGeneralRulesValidator {
    private final List<ValidationRule> validationRules;
    private final StatisticsFileManager statisticsFileManager;

    /**
     * Constructeur qui initialise et enregistre les règles générales de validation.
     */
    public HandleGeneralRulesValidator(StatisticsFileManager statisticsFileManager) {
        this.statisticsFileManager = statisticsFileManager;
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
     * @throws Groupe6INF2050Exception Si une ou plusieurs règles échouent.
     */
    public void handleGeneralsRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StatisticsData statisticsData) throws Groupe6INF2050Exception, IOException {
        StringBuilder errorMessage = new StringBuilder("Échec de la validation pour les raisons suivantes :\n");
        boolean isValid = true;
        for (ValidationRule rule : validationRules) {
            isValid &= rule.validate(jsonFileUtility, errorHandler, errorMessage);
        }
        if (!isValid) {
            jsonFileUtility.save(errorHandler);
            statisticsFileManager.saveStatistics(statisticsData);
            throw new Groupe6INF2050Exception(errorMessage.toString());
        }
    }


}