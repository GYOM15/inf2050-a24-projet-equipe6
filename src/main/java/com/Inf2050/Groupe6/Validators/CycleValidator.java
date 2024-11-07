package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators.DateValidator.validate;

public class CycleValidator {

    private static Cycle cycle;

    public static List<Cycle> getCycle() {
        return architectes_cycles;
    }

    private static final List<Cycle> architectes_cycles = new ArrayList<>();

    public CycleValidator(Cycle cycle) {
        CycleValidator.cycle = cycle;
    }

    public CycleValidator(Cycle... cycle) {
        architectes_cycles.addAll(Arrays.asList(cycle));
    }



    public static void setCycle(Cycle cycle) {
        if (architectes_cycles.contains(cycle)) {
            CycleValidator.cycle = cycle;
        }
    }

    public static boolean isDateWithinCycle(String dateValue, ErrorHandler errorHandler) {
        if (!validate(dateValue, null)) {
            return false;
        }
        LocalDate date = LocalDate.parse(dateValue);
        if (date.isBefore(cycle.getStartDate()) || date.isAfter(cycle.getEndDate())) {
            if (errorHandler != null) {
                errorHandler.addError("La date " + dateValue + " doit être dans le cycle " + cycle.getLabel() + ".");
            }
            return false;
        }
        return true;
    }
}