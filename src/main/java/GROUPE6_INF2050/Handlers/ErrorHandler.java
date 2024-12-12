package GROUPE6_INF2050.Handlers;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est dédiée uniquement à l'enregistrement des érreurs que nous rencontreront tout au long
 * du traitement du fichier Json, elle nous permet de personaliser les érreurs en fonction de leur type
 * et de les stocker dans une liste qui sera affichée quand on le souhaitera
 * */
public class ErrorHandler {
    // Liste pour stocker les erreurs rencontrées
    private final List<String> errors;

    /**
     * Constructeur de la classe ErrorHandler
     * Il instancie une ArrayList pour stocker les erreurs
     */
    public ErrorHandler() {
        this.errors = new ArrayList<>();
    }

    /**
     * Enregistre les érreurs de façon générale et celles spécifiques au traitement des catégories
     * @param errorMessage  Le message d'erreurs que nous allons écrire
     * */
    public void addError(String errorMessage) {
        errors.add(errorMessage);
    }

    /**
     * Enregistre les érreurs spécifique à une activité et recupère la description pour plus de précision
     * @param activityDescription La description de l'activité concerné dans le fichier Json
     * @param errorMessage Le complement du message d'erreurs pour une activité
     * */
    public void addActivityError(String activityDescription, String errorMessage) {
        String error = "Erreur pour l'activité '" + activityDescription + "': " + errorMessage;
        errors.add(error);
    }

    /**
     * Enregistre les érreurs spécifique à la validation du cycle
     * @param cycle Le cycle contenu dans le fichier Json
     * @param errorMessage  Le complement du message d'érreurs liées à la validation du cycle
     * */
    public void addCycleError(String cycle, String errorMessage) {
        String error = "Erreur pour le cycle '" + cycle + "': " + errorMessage;
        errors.add(error);
    }

    /**
     * Enregistre les erreurs générale
     * @param permitNumber Le numeros de permis contenu dans le fichier Json
     * @param errorMessage Le complement du message d'erreur liées à la validation du numéro de permis
     * */
    public void addPermitError(String permitNumber, String errorMessage) {
        String error = "Erreur pour le numéro de permis '" + permitNumber + "' : " + errorMessage;
        errors.add(error);
    }

    /**
     * Permet de récupérer le tableau d'erreurs depuis l'exterieur de la classe
     * @return  La liste des erreurs stocker dans le tableau
     * */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * À la place de verifier tout le temps si le tableau d'erreur est vide en utilisant getError(), nous
     * pouvons directement vérifier cela juste par l'appel de cette méthode
     * @return true si la liste n'est pas vide
     * */
    public boolean hasErrors() {

        return errors.isEmpty();
    }

    /**
     * Ajoute un message d'erreur au ErrorHandler s'il n'est pas nul.
     *
     * @param errorHandler L'instance ErrorHandler où ajouter le message d'erreur, si non nul
     * @param errorMessage Le message d'erreur à ajouter
     */
    public static void addErrorIfNotNull(ErrorHandler errorHandler, String errorMessage) {
        if (errorHandler != null) {
            errorHandler.addError(errorMessage);
        }
    }

}
