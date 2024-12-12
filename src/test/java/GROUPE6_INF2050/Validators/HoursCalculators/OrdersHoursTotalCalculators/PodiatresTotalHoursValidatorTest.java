package GROUPE6_INF2050.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import GROUPE6_INF2050.Enums.Cycle;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PodiatresTotalHoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidateByCycle_EnoughHours() {
        int totalHours = 65;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        PodiatresTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_InsufficientHours() {
        int totalHours = 50;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        PodiatresTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.hasErrors());
    }

    @Test
    public void testValidateByCycle_ExactHours() {
        int totalHours = 60;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        PodiatresTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_WithDifferentCycle() {
        int totalHours = 52;
        Cycle cycle = Cycle.CYCLE_2021_2024;
        PodiatresTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.hasErrors());
    }

}