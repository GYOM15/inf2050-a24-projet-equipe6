package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;


import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators.GeologueTotalHoursValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeologueTotalHoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidateByCycle_EnoughHours() {
        int totalHours = 60;
        Cycle cycle = Cycle.CYCLE_2020_2025;
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_InsufficientHours() {
        int totalHours = 50;
        Cycle cycle = Cycle.CYCLE_2020_2025;
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 5 heures pour compléter le cycle 2020-2025", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateByCycle_ExactHours() {
        int totalHours = 55;
        Cycle cycle = Cycle.CYCLE_2020_2025;
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_WithDifferentCycle() {
        int totalHours = 52;
        Cycle cycle = Cycle.CYCLE_2021_2024;
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 3 heures pour compléter le cycle 2021-2024", errorHandler.getErrors().get(0));
    }
}