package main.java.com.Inf2050.Groupe6.Handlers;

import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileTreatment;
import main.java.com.Inf2050.Groupe6.Validators.ActivityValidator;

/**
 * Cette classe est dédiée à la gestion du traitement du fichier JSON,
 * en appliquant les différentes étapes de validation et en sauvegardant les erreurs rencontrées.
 */
public class JsonHandler {

    /**
     * Cette méthode s'occupe de traiter un fichier JSON avec les différentes étapes de validation.
     *
     * @param obj Le fichier JSON contenu dans une instance de JsonFileTreatment.
     * @throws Groupe6INF2050Exception ramène une exception personnalisée en cas d'erreur avec le fichier.
     */
    public static void handleJson(JsonFileTreatment obj) throws Groupe6INF2050Exception {
        // Création de l'objet ErrorHandler pour stocker les erreurs de validation
        ErrorHandler errorHandler = new ErrorHandler();

        // Validation du fichier JSON
        obj.loadAndValid();

        // Création d'un objet d'ActivityValidator pour les différentes validations
        ActivityValidator validator = new ActivityValidator();

        // Validation des heures transférées du cycle précédent
        validator.validateTransferredHours(obj.getJsonObject(), errorHandler);

        // Validation des catégories présentes dans le fichier JSON
        validator.validateCategory(obj.getJsonObject(), errorHandler);

        // Validation du nombre minimal d'heures pour le cycle
        validator.isCycleMinHours40(obj.getJsonObject(), errorHandler);

        // Valide que les catégories spécifiées ont au moins 17 heures
        validator.isCategoryMin17Hours(obj.getJsonObject(), errorHandler);

        // Validation d'heures pour les catégories spécifiques
        validator.validatePresentationHours(obj.getJsonObject());
        validator.validateRedactionProHours(obj.getJsonObject());
        validator.validateGroupDiscussionHours(obj.getJsonObject());
        validator.validateProjetDeRechercheHours(obj.getJsonObject());

        // Ecrit les erreurs et la validité des données dans le fichier de sortie
        obj.save(errorHandler);
    }
}
