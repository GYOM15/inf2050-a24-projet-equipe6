package GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;

public interface ValidationRule {
     boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder stringBuilder);
}
