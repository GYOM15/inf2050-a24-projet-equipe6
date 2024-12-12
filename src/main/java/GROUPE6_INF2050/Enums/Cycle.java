package GROUPE6_INF2050.Enums;

import java.time.LocalDate;

/**
 * Représente les cycles d'activité possibles avec leurs périodes de validité.
 */
public enum Cycle {
    CYCLE_2023_2025("2023-2025", LocalDate.of(2023, 4, 1), LocalDate.of(2025, 4, 1)),
    CYCLE_2020_2022("2020-2022", LocalDate.of(2020, 4, 1), LocalDate.of(2022, 4, 1)),
    CYCLE_2018_2020("2018-2020", LocalDate.of(2018, 4, 1), LocalDate.of(2020, 7, 1)),
    CYCLE_2021_2024("2021-2024", LocalDate.of(2021, 6, 1), LocalDate.of(2024, 6, 1)),
    CYCLE_2020_2025("2020-2025", LocalDate.of(2020, 1, 1), LocalDate.of(2025, 1, 1)),
    DEFAULT_CYCLE("Cycle Invalide", LocalDate.MIN, LocalDate.MAX);

    private final String label;
    private final LocalDate startDate;
    private final LocalDate endDate;



    /**
     * Constructeur pour définir un cycle.
     *
     * @param label     Le label du cycle.
     * @param startDate La date de début du cycle.
     * @param endDate   La date de fin du cycle.
     */
    Cycle(String label, LocalDate startDate, LocalDate endDate) {
        this.label = label;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getLabel() {
        return label;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }


    /**
     * Trouve un cycle basé sur son label.
     *
     * @param label Le label du cycle recherché.
     * @return Le cycle correspondant au label, ou {@link #DEFAULT_CYCLE} si aucun cycle ne correspond.
     */
    public static Cycle getCycleByLabel(String label) {
        for (Cycle cycle : Cycle.values()) {
            if (cycle.getLabel().equalsIgnoreCase(label)) {
                return cycle;
            }
        }
        return DEFAULT_CYCLE;
    }
}