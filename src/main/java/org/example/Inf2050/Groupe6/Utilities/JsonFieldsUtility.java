package org.example.Inf2050.Groupe6.Utilities;

import net.sf.json.JSONObject;
import org.example.Inf2050.Groupe6.Enums.ActivityOrder;
import org.example.Inf2050.Groupe6.Enums.Cycle;
import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;

import java.util.Arrays;
import java.util.List;

public class JsonFieldsUtility {

    private static final List<String> requiredKeys = Arrays.asList(
            "numero_de_permis", "cycle", "ordre", "activites"
    );

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
                ErrorHandler.addErrorIfNotNull(errorHandler, "La clé : " + key + " est manquante dans le fichier JSON");
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
            ErrorHandler.addErrorIfNotNull(errorHandler, "La clé 'heures_transferees_du_cycle_precedent' est manquante pour l'ordre architectes.");
            return false;
        } else if (!isArchitecteOrder && hasTransferHoursKey) {
            ErrorHandler.addErrorIfNotNull(errorHandler, "La clé 'heures_transferees_du_cycle_precedent' ne doit exister que pour l'ordre architectes.");
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
        String orderLabel = jsonObject.getString("ordre");
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(orderLabel, errorHandler);
        Cycle cycle = Cycle.getCycleByLabel(jsonObject.getString("cycle"));
        if (cycle == null || ActivityOrder.isCycleValidByOrder(cycle, order)) {
            ErrorHandler.addErrorIfNotNull(errorHandler,"Le cycle " + jsonObject.getString("cycle") + " n'est pas valide pour l'ordre " + orderLabel);
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
    private static boolean checkActivitiesFields(JSONObject jsonObject, ErrorHandler errorHandler) {
        for (int i = 0; i < jsonObject.getJSONArray("activites").size(); i++) {
            for (String key : requiredActivitiesKeys) {
                if (!jsonObject.getJSONArray("activites").getJSONObject(i).containsKey(key)) {
                    ErrorHandler.addErrorIfNotNull(errorHandler,"La clé : " + key + " est manquante pour l'activité " + i);
                    return false;
                }
            }
        } return true;
    }

    /**
     * Vérifie si toutes les clés sont valides pour le JSON donné, y compris pour chaque activité.
     *
     * @param jsonObject L'objet JSON à vérifier
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si toutes les clés sont valides, sinon false
     */
    public static boolean areAllFieldsValid(JSONObject jsonObject, ErrorHandler errorHandler) {
        return checkActivitiesFields(jsonObject, errorHandler) && checkJsonFields(jsonObject, errorHandler);
    }
}