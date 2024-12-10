package GROUPE6_INF2050.Validators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static GROUPE6_INF2050.Validators.GeneralValidators.DateValidator.validate;


public class CycleValidator {
    private static Cycle currentCycle;

    public static void setCurrentCycle(Cycle currentCycle) {
        CycleValidator.currentCycle = currentCycle;
    }

    private static final List<Cycle> architectes_cycles = new ArrayList<>();

    /**
     * Constructeur pour initialiser un cycle unique pour la validation.
     *
     * @param cycle Cycle spécifique pour l'initialisation
     */
    public CycleValidator(Cycle cycle) {
        setCurrentCycle(cycle);
    }

    /**
     * Constructeur pour initialiser plusieurs cycles pour la validation.
     *
     * @param cycles Tableau de cycles pour initialiser la liste des cycles valides
     */
    public CycleValidator(Cycle... cycles) {
        architectes_cycles.addAll(Arrays.asList(cycles));
    }

    /**
     * Définit le cycle de validation actuel si celui-ci fait partie de la liste des cycles valides.
     *
     * @param cycle Cycle à définir comme cycle de validation
     */
    public static void setCycle(Cycle cycle) {
        if (architectes_cycles.contains(cycle)) {
            setCurrentCycle(cycle);
        }
    }

    /**
     * Vérifie si une date donnée se situe dans les limites du cycle en cours.
     *
     * @param dateValue Date sous forme de chaîne (String)
     * @param errorHandler Gestionnaire d'erreurs pour enregistrer les erreurs éventuelles
     * @return true si la date est dans le cycle, sinon false
     */
    public static boolean isDateWithinCycle(String dateValue, ErrorHandler errorHandler) {
        if (!validate(dateValue, null)) {
            return true;
        }
        LocalDate date = LocalDate.parse(dateValue);
        if (date.isBefore(currentCycle.getStartDate()) || date.isAfter(currentCycle.getEndDate())) {
            ErrorHandler.addErrorIfNotNull(errorHandler, "La date " + dateValue + " doit être dans le cycle " + currentCycle.getLabel() + ".");
            return true;
        } return false;
    }



    /**
     * Retourne la liste des cycles d'architectes.
     *
     * @return Liste des cycles valides pour les architectes
     */
    public static List<Cycle> getArchitectesCycle() {
        return architectes_cycles;
    }

    public static Cycle getCycle(){
        return currentCycle;
    }
}