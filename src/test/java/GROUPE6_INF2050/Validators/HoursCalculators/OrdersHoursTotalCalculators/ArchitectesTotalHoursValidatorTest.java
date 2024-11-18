package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArchitectesTotalHoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidateByCycle_EnoughHours_2023_2025() {
        int totalHours = 40;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_InsufficientHours_2023_2025() {
        int totalHours = 35;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 5 heures pour compléter le cycle 2023-2025", errorHandler.getErrors().getFirst());
    }

    @Test
    public void testValidateByCycle_EnoughHours_2020_2022() {
        int totalHours = 42;
        Cycle cycle = Cycle.CYCLE_2020_2022;
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_InsufficientHours_2020_2022() {
        int totalHours = 40;
        Cycle cycle = Cycle.CYCLE_2020_2022;
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 2 heures pour compléter le cycle 2020-2022", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateByCycle_NoRequiredHours_2021_2024() {
        int totalHours = 0;
        Cycle cycle = Cycle.CYCLE_2021_2024;
        ArchitectesTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

}