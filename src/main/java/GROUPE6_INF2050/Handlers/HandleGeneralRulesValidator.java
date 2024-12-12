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

public class HandleGeneralRulesValidator {

    private final List<ValidationRule> validationRules;
    private final StatisticsFileManager statisticsFileManager;

    public HandleGeneralRulesValidator(StatisticsFileManager statisticsFileManager) {
        this.statisticsFileManager = statisticsFileManager;
        this.validationRules = new ArrayList<>();
        registerValidationRules();
    }

    /**
     * Enregistre toutes les règles de validation générales applicables.
     */
    private void registerValidationRules() {
        validationRules.add(new CycleValidatorRule());
        validationRules.add(new OrderValidatorRule());
        validationRules.add(new PersonValidatorRule());
        validationRules.add(new PermitNumberValidatorRule());
        validationRules.add(new DescriptionValidatorRule());
        validationRules.add(new HoursValidatorRule());
        validationRules.add(new JsonFieldsValidatorRule());
    }


    /**
     * Applique toutes les règles générales sur les données JSON.
     * Met à jour les statistiques et les erreurs en fonction des résultats des validations.
     *
     * @param jsonFileUtility Instance pour manipuler le fichier JSON.
     * @param errorHandler    Gestionnaire d'erreurs pour enregistrer les erreurs.
     * @param statisticsData  Objet pour mettre à jour les statistiques.
     * @return true si toutes les règles sont validées, sinon false.
     * @throws IOException              En cas d'erreur lors de la sauvegarde des fichiers.
     * @throws Groupe6INF2050Exception  Si les validations échouent.
     */
    public boolean handleGeneralsRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StatisticsData statisticsData)
            throws IOException, Groupe6INF2050Exception {
        StringBuilder errorMessage = new StringBuilder("Échec de la validation pour les raisons suivantes :\n");
        boolean isValid = validateRules(jsonFileUtility, errorHandler, errorMessage);
        if (!isValid) {
            addErrorsToHandler(errorMessage, errorHandler);
            updateStatisticsOnFailure(statisticsData, jsonFileUtility, errorHandler);
            throwValidationException(errorMessage);
        } return true;
    }

    /**
     * Valide les règles en parcourant la liste des règles enregistrées.
     *
     * @param jsonFileUtility Instance pour manipuler le fichier JSON.
     * @param errorHandler    Gestionnaire d'erreurs pour enregistrer les erreurs.
     * @param errorMessage    Accumulateur pour collecter les messages d'erreurs.
     * @return true si toutes les règles sont validées, sinon false.
     */
    private boolean validateRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean isValid = true;
        for (ValidationRule rule : validationRules) {
            isValid &= rule.validate(jsonFileUtility, errorHandler, errorMessage);
        }
        return isValid;
    }


    /**
     * Met à jour les statistiques en cas d'échec des validations.
     *
     * @param statisticsData  Objet contenant les statistiques globales.
     * @param jsonFileUtility Instance pour manipuler le fichier JSON.
     * @param errorHandler    Gestionnaire d'erreurs.
     * @throws IOException              En cas d'erreur lors de la sauvegarde des fichiers.
     * @throws Groupe6INF2050Exception  Si les validations échouent.
     */
    private void updateStatisticsOnFailure(StatisticsData statisticsData, JsonFileUtility jsonFileUtility, ErrorHandler errorHandler)
            throws IOException, Groupe6INF2050Exception {
        if (!PermitNumberValidatorRule.isPermitNumberState()) {
            statisticsData.incrementInvalidPermitDeclarations(1);
        }
        statisticsData.incrementIncompleteOrInvalidDeclarations(1);
        saveErrorData(jsonFileUtility, errorHandler, statisticsData);
    }


    /**
     * Sauvegarde les données d'erreurs et les statistiques.
     *
     * @param jsonFileUtility Instance pour manipuler le fichier JSON.
     * @param errorHandler    Gestionnaire d'erreurs.
     * @param statisticsData  Objet contenant les statistiques globales.
     * @throws IOException En cas d'erreur de sauvegarde.
     */
    private void saveErrorData(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StatisticsData statisticsData)
            throws IOException, Groupe6INF2050Exception {
        jsonFileUtility.save(errorHandler);
        statisticsFileManager.saveStatistics(statisticsData);
    }


    private void throwValidationException(StringBuilder errorMessage) throws Groupe6INF2050Exception {
        throw new Groupe6INF2050Exception(errorMessage.toString());
    }


    /**
     * Ajoute les erreurs accumulées dans le gestionnaire d'erreurs.
     *
     * @param errorMessage Messages d'erreurs accumulés.
     * @param errorHandler Gestionnaire d'erreurs.
     */
    private void addErrorsToHandler(StringBuilder errorMessage, ErrorHandler errorHandler) {
        String[] errorLines = errorMessage.toString().split("\n");
        for (String line : errorLines) {
            if (!line.isBlank()) {
                errorHandler.addError(line);
            }
        }
    }
}
