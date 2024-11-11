package org.example.Inf2050.Groupe6.Enums;

import org.example.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.example.Inf2050.Groupe6.Validators.CycleValidator;

public enum ActivityOrder {
    ARCHITECTES("architectes", new CycleValidator(Cycle.getCycleByLabel("2023-2025"), Cycle.getCycleByLabel("2020-2022"), Cycle.getCycleByLabel("2018-2020"))),
    GEOLOGUES("géologues",  new CycleValidator(Cycle.getCycleByLabel("2021-2024"))),
    PSYCHOLOGUES("psychologues", new CycleValidator(Cycle.getCycleByLabel("2020-2025"))),
    ORDER_NON_VALIDE("non-valide", null);

    private final String order;
    private final CycleValidator cycleValidator;

    ActivityOrder(String order, CycleValidator cycleValidator) {
        this.order = order;
        this.cycleValidator = cycleValidator;
    }

    /**
     * Vérifie si un cycle est valide pour un ordre donné.
     *
     * @param cycle Le cycle à valider
     * @param order L'ordre auquel le cycle doit correspondre
     * @return true si le cycle est valide pour l'ordre, sinon false
     */
    public static boolean isCycleValidByOrder(Cycle cycle, ActivityOrder order) {
        if (order == ARCHITECTES && CycleValidator.getCycle().contains(cycle) ) {
            CycleValidator.setCycle(cycle);
            return false;
        }
        if (order == GEOLOGUES && cycle == Cycle.CYCLE_2021_2024) {
            return false;
        }
        return order != PSYCHOLOGUES || cycle != Cycle.CYCLE_2020_2025;
    }

    /**
     * Recherche et retourne l'ActivityOrder correspondant au label donné dans le fichier JSON.
     * Si l'ordre n'est pas trouvé, enregistre une erreur dans ErrorHandler et retourne ORDER_NON_VALIDE.
     *
     * @param label         Le label de l'ordre à rechercher.
     * @param errorHandler  Le gestionnaire d'erreurs pour enregistrer une erreur si l'ordre est invalide.
     * @return L'ActivityOrder correspondant au label, ou ORDER_NON_VALIDE si le label est introuvable.
     */
    public static ActivityOrder searchFromJsonOrder(String label, ErrorHandler errorHandler) {
        for (ActivityOrder order : ActivityOrder.values()) {
            if (order.order.equalsIgnoreCase(label)) {
                return order;
            }
        }
        addErrorIfHandlerPresent(label, errorHandler);
        return ORDER_NON_VALIDE;
    }

    /**
     * Ajoute une erreur dans ErrorHandler si celui-ci est présent.
     *
     * @param label         Le label de l'ordre invalide.
     * @param errorHandler  Le gestionnaire d'erreurs pour enregistrer une erreur.
     */
    private static void addErrorIfHandlerPresent(String label, ErrorHandler errorHandler) {
        ErrorHandler.addErrorIfNotNull(errorHandler,"L'ordre " + label + " n'est pas valide");
    }
}