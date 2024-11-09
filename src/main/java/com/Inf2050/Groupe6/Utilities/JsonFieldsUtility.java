package main.java.com.Inf2050.Groupe6.Utilities;

import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class JsonFieldsUtility {

    // Liste des clés obligatoires pour le JSON global
    private static final List<String> requiredKeys = Arrays.asList(
            "numero_de_permis", "cycle", "ordre", "activites"
    );

    // Liste des clés obligatoires pour chaque activité dans "activites"
    private static final List<String> requiredActivitiesKeys = Arrays.asList(
            "description", "categorie", "heures", "date"
    );

    /**
     * Vérifie que toutes les clés nécessaires sont présentes dans le JSON et que
     * "heures_transferees_du_cycle_precedent" est conforme aux exigences d'ordre.
     *
     * @param jsonObject L'objet JSON à vérifier
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si toutes les clés sont valides et présentes, sinon false
     */
    public static boolean checkJsonFields(JSONObject jsonObject, ErrorHandler errorHandler) {
        return checkRequiredKeys(jsonObject, errorHandler)
                && checkArchitecteSpecificKey(jsonObject, errorHandler)
                && validateCycleByOrder(jsonObject, errorHandler);
    }

    /**
     * Vérifie que toutes les clés générales requises sont présentes dans l'objet JSON.
     *
     * @param jsonObject L'objet JSON à vérifier
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si toutes les clés nécessaires sont présentes, sinon false
     */
    private static boolean checkRequiredKeys(JSONObject jsonObject, ErrorHandler errorHandler) {
        for (String key : requiredKeys) {
            if (!jsonObject.containsKey(key)) {
                addErrorIfNotNull(errorHandler, "La clé : " + key + " est manquante dans le fichier JSON");
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie que la clé "heures_transferees_du_cycle_precedent" n'existe que pour l'ordre "architectes".
     *
     * @param jsonObject L'objet JSON à vérifier
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si la clé est présente uniquement pour "architectes", sinon false
     */
    private static boolean checkArchitecteSpecificKey(JSONObject jsonObject, ErrorHandler errorHandler) {
        boolean isArchitecteOrder = "architectes".equalsIgnoreCase(jsonObject.getString("ordre"));
        boolean hasTransferHoursKey = jsonObject.containsKey("heures_transferees_du_cycle_precedent");

        if (isArchitecteOrder && !hasTransferHoursKey) {
            addErrorIfNotNull(errorHandler, "La clé 'heures_transferees_du_cycle_precedent' est manquante pour l'ordre architectes.");
            return false;
        } else if (!isArchitecteOrder && hasTransferHoursKey) {
            addErrorIfNotNull(errorHandler, "La clé 'heures_transferees_du_cycle_precedent' ne doit exister que pour l'ordre architectes.");
            return false;
        }
        return true;
    }

    /**
     * Valide que le cycle est conforme aux exigences de l'ordre spécifié.
     *
     * @param jsonObject L'objet JSON contenant le cycle et l'ordre
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si le cycle est valide pour l'ordre, sinon false
     */
    private static boolean validateCycleByOrder(JSONObject jsonObject, ErrorHandler errorHandler) {
        // Récupère l'ordre et le cycle depuis le JSON
        String orderLabel = jsonObject.getString("ordre");
        String cycleLabel = jsonObject.getString("cycle");

        // Utilise ActivityOrder pour rechercher l'ordre et valider le cycle
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(orderLabel, errorHandler);
        Cycle cycle = Cycle.getCycleByLabel(cycleLabel);

        // Vérifie si l'ordre est valide et si le cycle correspond à l'ordre
        if (cycle == null || !ActivityOrder.isCycleValidByOrder(cycle, order)) {
            addErrorIfNotNull(errorHandler, "Le cycle " + cycleLabel + " n'est pas valide pour l'ordre " + orderLabel);
            return false;
        }
        return true;
    }

    /**
     * Vérifie que chaque activité possède toutes les clés nécessaires.
     *
     * @param jsonObject L'objet JSON contenant les activités
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si toutes les activités contiennent les clés nécessaires, sinon false
     */
    public static boolean checkActivitiesFields(JSONObject jsonObject, ErrorHandler errorHandler) {
        for (int i = 0; i < jsonObject.getJSONArray("activites").size(); i++) {
            for (String key : requiredActivitiesKeys) {
                if (!jsonObject.getJSONArray("activites").getJSONObject(i).containsKey(key)) {
                    addErrorIfNotNull(errorHandler, "La clé : " + key + " est manquante pour l'activité " + i);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Vérifie si toutes les clés sont valides pour le JSON donné, y compris pour chaque activité.
     *
     * @param jsonObject L'objet JSON à vérifier
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si toutes les clés sont valides, sinon false
     */
    public static boolean isAllFieldsValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        return checkActivitiesFields(jsonObject, errorHandler) && checkJsonFields(jsonObject, errorHandler);
    }

    /**
     * Ajoute une erreur au ErrorHandler si celui-ci n'est pas nul.
     *
     * @param errorHandler L'instance ErrorHandler où ajouter le message d'erreur, si non nul
     * @param errorMessage Le message d'erreur à ajouter
     */
    private static void addErrorIfNotNull(ErrorHandler errorHandler, String errorMessage) {
        if (errorHandler != null) {
            errorHandler.addError(errorMessage);
        }
    }
}