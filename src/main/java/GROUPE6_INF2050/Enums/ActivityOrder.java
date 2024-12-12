package GROUPE6_INF2050.Enums;

import GROUPE6_INF2050.Validators.CycleValidator;

public enum ActivityOrder {
    ARCHITECTES("architectes", new CycleValidator(Cycle.getCycleByLabel("2023-2025"), Cycle.getCycleByLabel("2020-2022"), Cycle.getCycleByLabel("2018-2020"))),
    GEOLOGUES("géologues",  new CycleValidator(Cycle.getCycleByLabel("2021-2024"))),
    PSYCHOLOGUES("psychologues", new CycleValidator(Cycle.getCycleByLabel("2020-2025"))),
    PODIATRES("podiatres", new CycleValidator(Cycle.getCycleByLabel("2021-2024"))),
    ORDER_NON_VALIDE("Ordre Inconu", null);

    public String getOrderString() {
        return order;
    }

    private final String order;
    private static ActivityOrder currentOrder;

    public static void setCurrentOrder(ActivityOrder currentOrder) {
        ActivityOrder.currentOrder = currentOrder;
    }

    public static ActivityOrder getCurrentOrder() {
        return currentOrder;
    }

    ActivityOrder(String order, CycleValidator cycleValidator) {
        this.order = order;
    }

    /**
     * Vérifie si un cycle est valide pour un ordre donné.
     *
     * @param cycle Le cycle à valider
     * @param order L'ordre auquel le cycle doit correspondre
     * @return true si le cycle est valide pour l'ordre, sinon false
     */
    public static boolean isCycleValidByOrder(Cycle cycle, ActivityOrder order) {
        if (order == ARCHITECTES && CycleValidator.getArchitectesCycle().contains(cycle) ) {
            CycleValidator.setCycle(cycle);
            return false;
        }
        if (order == GEOLOGUES && cycle == Cycle.CYCLE_2021_2024) { return false; }
        if (order == PODIATRES && cycle == Cycle.CYCLE_2021_2024){ return false; }
        return order != PSYCHOLOGUES || cycle != Cycle.CYCLE_2020_2025;
    }


    /**
     * Recherche et retourne l'ActivityOrder correspondant au label donné dans le fichier JSON.
     * Si l'ordre n'est pas trouvé, enregistre une erreur dans ErrorHandler et retourne ORDER_NON_VALIDE.
     *
     * @param label         Le label de l'ordre à rechercher.
     * @return L'ActivityOrder correspondant au label, ou ORDER_NON_VALIDE si le label est introuvable.
     */
    public static ActivityOrder searchFromJsonOrder(String label) {
        for (ActivityOrder order : ActivityOrder.values()) {
            if (order.order.equalsIgnoreCase(label)) {
                return order;
            }
        }
        return ORDER_NON_VALIDE;
    }

}