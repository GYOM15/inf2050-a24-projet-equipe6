package GROUPE6_INF2050.Validators.GeneralValidators;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import net.sf.json.JSONObject;

/**
 * Cette classe est chargée de valider les heures transférées d'un cycle à l'autre.
 */
public class TransferredHoursValidator {

    /**
     * Valide le nombre d'heures transférées d'une activité à une autre
     * Si une erreur trouvée, on la rajoute dans le errorHandler
     * @param jsonObject Le JSON à partir du quel on accède au champs heures_transferees_du_cycle_precedent
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs si le nombre d'heures transferable est inferieur à 0 ou supérieur à 7
     * */
    public static int validateTransferredHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        int transferredHours = jsonObject.getInt("heures_transferees_du_cycle_precedent");
        if (errorHandler != null) {
            transferredHours = validateNegativeHours(transferredHours, errorHandler);
            return Math.min(transferredHours, validateExcessHours(transferredHours, errorHandler));
        }
        return transferredHours;
    }

    /**
     * Valide que le nombre d'heures transférées n'est pas négatif.
     *
     * @param transferredHours Le nombre d'heures transférées à valider.
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs si le nombre d'heures est négatif.
     * @return 0 si les heures sont négatives, sinon le nombre d'heures transférées.
     */
    private static int validateNegativeHours(int transferredHours, ErrorHandler errorHandler) {
        if (transferredHours < 0) {
            errorHandler.addError("Le nombre d'heures transférées est négatif. Il sera ajusté à 0.");
            return 0;
        }
        return transferredHours;
    }

    /**
     * Valide le nombre d'heures transférées pour s'assurer qu'elles ne dépassent pas 7
     *
     * @param transferredHours Le nombre d'heures transférées à valider.
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs si le nombre d'heures dépasse 7
     * @return Le nombre d'heures transférées
     */
    private static int validateExcessHours(int transferredHours, ErrorHandler errorHandler) {
        if (transferredHours > 7) {
            errorHandler.addError("Le nombre d'heures transférées excède 7. Seulement 7 heures seront comptabilisées.");
            return 7;
        }
        return transferredHours;
    }
}
