package main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.Façade;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.CategoryMin17HoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.OrdersHoursTotalCalculators.ArchitectesTotalHoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.OrdersHoursTotalCalculators.GeologueTotalHoursValidator;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.OrdersHoursTotalCalculators.PsychologueTotalHoursValidator;
import net.sf.json.JSONObject;

public class HandleTotalHours {
    public static void handleHoursTotal(JSONObject jsonObject, int totalHours, ErrorHandler errorHandler) {
        String order = jsonObject.getString("ordre");
        Cycle cycle = Cycle.getCycleByLabel(jsonObject.getString("cycle"));
        if(cycle != null) {
            switch (order) {
                case "architectes":
                    if (CategoryMin17HoursValidator.calculateAndValidateMin17Hours(jsonObject,null) < 17)
                    {
                        totalHours += jsonObject.getInt("heures_transferees_du_cycle_precedent");
                    }
                    ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
                    break;
                case "psychologues":
                    PsychologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
                    break;
                case "géologues":
                    GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
                    break;
            }
        }
    }
}
