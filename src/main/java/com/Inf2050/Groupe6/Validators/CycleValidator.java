package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.DateValidator.validate;

/**
 * Cette classe nous permet de faire la validation de tout ce qui concerne les activités, y compris le cycle
 */
public class CycleValidator {

    private static final String CYCLE_VALID = "2023-2025";
    private static final LocalDate CYCLE_START = LocalDate.parse("2023-04-01");
    private static final LocalDate CYCLE_END = LocalDate.parse("2025-04-01");

    private static Cycle cycle;

    public CycleValidator(Cycle cycle) {
        CycleValidator.cycle = cycle;
    }

    /**
     * Vérifie si le cycle est valide, c'est à dire, entre 2023 et 2025
     * @param jsonObject    Le JSON contenant les informations du cycle
     * @param errorHandler  L'objet ErrorHandler pour stocker les erreurs de validation du cycle
     */
    public static void validateCycle(JSONObject jsonObject, ErrorHandler errorHandler) {
        String cycleValue = jsonObject.getString("cycle");
        if (!CYCLE_VALID.equals(cycleValue)) {
            errorHandler.addCycleError(cycleValue, "Le cycle n'est pas supporté. Seul le cycle 2023-2025 est valide.");
        }
    }

    /**
     * Vérifie si la date pour chaque activité se trouve dans l'intervalle 2023-04-01 et 2025-04-01
     * @param jsonObject Le JSON contenant les informations des activités
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs liées aux activités
     */
    public static void checkIfActivityDateInCycle(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        for (int i = 0; i < activities.size(); i++) {
            JSONObject activity = activities.getJSONObject(i);
            String dateValue = activity.getString("date");
            String description = activity.getString("description");
            validateDate(dateValue, description, errorHandler);
        }
    }

    /**
     * Valide une date donnée en vérifiant si elle est dans le cycle.
     *
     * @param dateValue La date à valider en tant que chaîne.
     * @param description La description de l'activité associée à la date.
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs.
     */
    private static void validateDate(String dateValue, String description, ErrorHandler errorHandler) {
        try {
            if (isDateOutOfCycle(LocalDate.parse(dateValue))) {
                errorHandler.addActivityError(description, "La date " + dateValue + " doit être entre le cycle '2023-04-01' et '2025-04-01'.");
            }
        } catch (DateTimeParseException e) {
            errorHandler.addActivityError(description, "Date d'activité invalide : " + dateValue);
        }
    }

    /**
     * Vérifie si la date est en dehors des limites du cycle.
     *
     * @param date La date à vérifier.
     * @return true si la date est hors du cycle, false sinon.
     */
    private static boolean isDateOutOfCycle(LocalDate date) {
        return date.isBefore(CYCLE_START) || date.isAfter(CYCLE_END);
    }

    /**
     * Vérifie si une date donnée est dans le cycle et enregistre une erreur si elle est hors cycle.
     *
     * @param dateValue La date en tant que chaîne de caractères.
     * @param errorHandler L'objet ErrorHandler pour enregistrer les erreurs.
     * @return true si la date est dans le cycle, false sinon.
     */
    public static boolean isDateWithinCycle(String dateValue, ErrorHandler errorHandler) {
        if (!validate(dateValue, null)) {
            return false;
        }
        LocalDate date = LocalDate.parse(dateValue);
        if (date.isBefore(cycle.getStartDate()) || date.isAfter(cycle.getEndDate())) {
            errorHandler.addError("La date " + dateValue + " doit être dans le cycle " + cycle.getLabel() + ".");
            return false;
        }
        return true;
    }
}