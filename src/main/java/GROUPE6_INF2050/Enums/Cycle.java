package GROUPE6_INF2050.Enums;

import java.time.LocalDate;

public enum Cycle {
    CYCLE_2023_2025("2023-2025", LocalDate.of(2023, 4, 1), LocalDate.of(2025, 4, 1)),
    CYCLE_2020_2022("2020-2022", LocalDate.of(2020, 4, 1), LocalDate.of(2022, 4, 1)),
    CYCLE_2018_2020("2018-2020", LocalDate.of(2018, 4, 1), LocalDate.of(2020, 7, 1)),
    CYCLE_2021_2024("2021-2024", LocalDate.of(2021, 6, 1), LocalDate.of(2024, 6, 1)),
    CYCLE_2020_2025("2020-2025", LocalDate.of(2020, 1, 1), LocalDate.of(2025, 1, 1));


    private final String label;
    private final LocalDate startDate;
    private final LocalDate endDate;

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

    public static Cycle getCycleByLabel(String label) {
        for (Cycle cycle : values()) {
            if (cycle.getLabel().equals(label)) {
                return cycle;
            }
        }
        return null;
    }
}