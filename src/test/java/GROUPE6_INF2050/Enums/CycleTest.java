package GROUPE6_INF2050.Enums;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CycleTest {
    @Test
    void testGetLabel() {
        assertEquals("2023-2025", Cycle.CYCLE_2023_2025.getLabel());
        assertEquals("2020-2022", Cycle.CYCLE_2020_2022.getLabel());
        assertEquals("2018-2020", Cycle.CYCLE_2018_2020.getLabel());
        assertEquals("2021-2024", Cycle.CYCLE_2021_2024.getLabel());
        assertEquals("2020-2025", Cycle.CYCLE_2020_2025.getLabel());
    }

    @Test
    void testGetStartDate() {
        assertEquals(LocalDate.of(2023, 4, 1), Cycle.CYCLE_2023_2025.getStartDate());
        assertEquals(LocalDate.of(2020, 4, 1), Cycle.CYCLE_2020_2022.getStartDate());
        assertEquals(LocalDate.of(2018, 4, 1), Cycle.CYCLE_2018_2020.getStartDate());
        assertEquals(LocalDate.of(2021, 6, 1), Cycle.CYCLE_2021_2024.getStartDate());
        assertEquals(LocalDate.of(2020, 1, 1), Cycle.CYCLE_2020_2025.getStartDate());
    }

    @Test
    void testGetEndDate() {
        assertEquals(LocalDate.of(2025, 4, 1), Cycle.CYCLE_2023_2025.getEndDate());
        assertEquals(LocalDate.of(2022, 4, 1), Cycle.CYCLE_2020_2022.getEndDate());
        assertEquals(LocalDate.of(2020, 7, 1), Cycle.CYCLE_2018_2020.getEndDate());
        assertEquals(LocalDate.of(2024, 6, 1), Cycle.CYCLE_2021_2024.getEndDate());
        assertEquals(LocalDate.of(2025, 1, 1), Cycle.CYCLE_2020_2025.getEndDate());
    }

    @Test
    void testGetCycleByLabel_ValidLabel() {
        Cycle cycle = Cycle.getCycleByLabel("2023-2025");
        assertNotNull(cycle);

        assertEquals(Cycle.CYCLE_2023_2025, cycle);
        cycle = Cycle.getCycleByLabel("2020-2022");
        assertNotNull(cycle);

        assertEquals(Cycle.CYCLE_2020_2022, cycle);
        cycle = Cycle.getCycleByLabel("2018-2020");
        assertNotNull(cycle);
        assertEquals(Cycle.CYCLE_2018_2020, cycle);

        cycle = Cycle.getCycleByLabel("2021-2024");
        assertNotNull(cycle);
        assertEquals(Cycle.CYCLE_2021_2024, cycle);

        cycle = Cycle.getCycleByLabel("2020-2025");
        assertNotNull(cycle);
        assertEquals(Cycle.CYCLE_2020_2025, cycle);
    }
}