package GROUPE6_INF2050.Validators;


import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.CycleValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CycleValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testCycleValidator_OneCycleConstructor() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2021_2024);
        assertEquals(Cycle.CYCLE_2021_2024, cycleValidator.getCycle());
    }

    @Test
    public void testCycleValidator_MultipleCyclesConstructor() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2022, Cycle.CYCLE_2023_2025);
        assertTrue(cycleValidator.getArchitectesCycle().contains(Cycle.CYCLE_2020_2022));
        assertTrue(cycleValidator.getArchitectesCycle().contains(Cycle.CYCLE_2023_2025));
    }

    @Test
    public void testSetCycle_ValidCycle() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2022, Cycle.CYCLE_2023_2025);
        cycleValidator.setCycle(Cycle.CYCLE_2020_2022);
        assertEquals(Cycle.CYCLE_2020_2022, CycleValidator.getCycle());
    }

    @Test
    public void testIsDateWithinCycle_ValidDate() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2022);
        String validDate = "2021-06-01";
        assertFalse(cycleValidator.isDateWithinCycle(validDate, errorHandler));
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testIsDateWithinCycle_InvalidDate() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2022);
        String invalidDate = "2023-01-01";
        assertTrue(cycleValidator.isDateWithinCycle(invalidDate, errorHandler));
        assertFalse(errorHandler.getErrors().isEmpty());
        assertTrue(errorHandler.getErrors().get(0).contains("La date 2023-01-01 doit être dans le cycle"));
    }

    @Test
    public void testIsDateWithinCycle_InvalidFormat() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2022);
        String invalidDate = "01-01-2023";
        assertTrue(cycleValidator.isDateWithinCycle(invalidDate, errorHandler));
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testGetCycle() {
        CycleValidator cycleValidator = new CycleValidator(Cycle.CYCLE_2020_2022, Cycle.CYCLE_2023_2025);
        assertTrue(cycleValidator.getArchitectesCycle().contains(Cycle.CYCLE_2020_2022));
        assertTrue(cycleValidator.getArchitectesCycle().contains(Cycle.CYCLE_2023_2025));
    }
}