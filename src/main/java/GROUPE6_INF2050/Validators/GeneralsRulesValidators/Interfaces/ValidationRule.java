package GROUPE6_INF2050.Validators.GeneralsRulesValidators.Interfaces;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Utilities.JsonFileUtility;

/**
 * Interface représentant une règle de validation générale.
 */
public interface ValidationRule {

     /**
      * Valide une règle spécifique en fonction des données fournies.
      *
      * @param jsonFileUtility L'utilitaire JSON contenant les données à valider.
      * @param errorHandler    Le gestionnaire d'erreurs pour enregistrer les erreurs détectées.
      * @param stringBuilder   Un accumulateur pour enregistrer les messages d'erreur.
      * @return true si la validation réussit, false sinon.
      */
     boolean validate(JsonFileUtility jsonFileUtility, ErrorHandler errorHandler, StringBuilder stringBuilder);
}
