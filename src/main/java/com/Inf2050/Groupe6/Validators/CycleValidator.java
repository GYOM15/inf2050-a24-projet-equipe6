package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Cette classe nous permet de faire la validation de tout ce qui concerne les activités, y compris le cycle
 */
public class CycleValidator {

    private static final String CYCLE_VALID = "2023-2025";
    private static final LocalDate CYCLE_START = LocalDate.parse("2023-04-01");
    private static final LocalDate CYCLE_END = LocalDate.parse("2025-04-01");


    /**
     * Vérifie si le cycle est valide, c'est à dire, entre 2023 et 2025
     * @param jsonObject    Le JSON contenant les informations du cycle
     * @param errorHandler  L'objet ErrorHandler pour stocker les erreurs de validation du cycle
     */
    public static void validateCycle(JSONObject jsonObject, ErrorHandler errorHandler) {
        String cycle = jsonObject.getString("cycle");
        if (!CYCLE_VALID.equals(cycle)) {
            errorHandler.addCycleError(cycle, "Le cycle n'est pas supporté. Seul le cycle 2023-2025 est valide.");
        }
    }


    /**
     * Verifie si la date pour chaque activité, si elle se trouve dans l'intervalle 2023-04-01 et 2025-04-01
     * @param jsonObject    Le JSON contenant les informations des activités
     * @param errorHandler  L'objet ErrorHandler pour enregistrer les erreurs liées aux activités
     */
    public static void validateActivitiesDates(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));

        for (int i = 0; i < activities.size(); i++) {

            JSONObject activity = activities.getJSONObject(i);
            String dateValue = activity.getString("date");
            String description = activity.getString("description");

            try {
                LocalDate date = LocalDate.parse(dateValue);
                if (date.isBefore(CYCLE_START) || date.isAfter(CYCLE_END)) {
                    errorHandler.addActivityError(description, "La date " + date + " doit être entre le cycle '2023-04-01' et '2025-04-01' .");
                }
            } catch (DateTimeParseException e) {
                errorHandler.addActivityError(description, "Date d'activité invalide : " + dateValue);
            }
        }
    }
}
