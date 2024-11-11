package main.java.com.Inf2050.Groupe6.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.junit.jupiter.api.*;

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
        Cycle cycle = Cycle.CYCLE_2023_2025;
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_InsufficientHours() {
        int totalHours = 50;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        GeologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 5 heures pour compléter le cycle 2023-2025", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateByCycle_ExactHours() {
        int totalHours = 55;
        Cycle cycle = Cycle.CYCLE_2023_2025;
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