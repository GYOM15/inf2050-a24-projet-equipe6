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

    private void registerValidationRules() {
        validationRules.add(new CycleValidatorRule());
        validationRules.add(new OrderValidatorRule());
        validationRules.add(new PersonValidatorRule());
        validationRules.add(new PermitNumberValidatorRule());
        validationRules.add(new DescriptionValidatorRule());
        validationRules.add(new HoursValidatorRule());
        validationRules.add(new JsonFieldsValidatorRule());
    }

    public boolean handleGeneralsRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StatisticsData statisticsData)
            throws IOException, Groupe6INF2050Exception {
        StringBuilder errorMessage = new StringBuilder("Échec de la validation pour les raisons suivantes :\n");
        boolean isValid = validateRules(jsonFileUtility, errorHandler, errorMessage);

        if (!isValid) {
            addErrorsToHandler(errorMessage, errorHandler);
            updateStatisticsOnFailure(statisticsData, jsonFileUtility, errorHandler);
            throwValidationException(errorMessage);
        }

        return true;
    }

    private boolean validateRules(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder errorMessage) {
        boolean isValid = true;
        for (ValidationRule rule : validationRules) {
            isValid &= rule.validate(jsonFileUtility, errorHandler, errorMessage);
        }
        return isValid;
    }

    private void updateStatisticsOnFailure(StatisticsData statisticsData, JsonFileUtility jsonFileUtility, ErrorHandler errorHandler)
            throws IOException, Groupe6INF2050Exception {
        if (!PermitNumberValidatorRule.isPermitNumberState()) {
            statisticsData.incrementInvalidPermitDeclarations(1);
        }
        statisticsData.incrementIncompleteOrInvalidDeclarations(1);
        saveErrorData(jsonFileUtility, errorHandler, statisticsData);
    }

    private void saveErrorData(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StatisticsData statisticsData)
            throws IOException, Groupe6INF2050Exception {
        jsonFileUtility.save(errorHandler);
        statisticsFileManager.saveStatistics(statisticsData);
    }

    private void throwValidationException(StringBuilder errorMessage) throws Groupe6INF2050Exception {
        throw new Groupe6INF2050Exception(errorMessage.toString());
    }

    private void addErrorsToHandler(StringBuilder errorMessage, ErrorHandler errorHandler) {
        String[] errorLines = errorMessage.toString().split("\n");
        for (String line : errorLines) {
            if (!line.isBlank()) {
                errorHandler.addError(line);
            }
        }
    }
}
