package Groupe6.Validators.HoursCalculators.OrdersHoursTotalCalculators;

import main.java.com.Inf2050.Groupe6.Enums.Cycle;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PsychologueTotalHoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidateByCycle_EnoughHours() {
        int totalHours = 100;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        PsychologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_InsufficientHours() {
        int totalHours = 80;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        PsychologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 10 heures pour compléter le cycle 2023-2025", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateByCycle_ExactHours() {
        int totalHours = 90;
        Cycle cycle = Cycle.CYCLE_2023_2025;
        PsychologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidateByCycle_WithDifferentCycle() {
        int totalHours = 85;
        Cycle cycle = Cycle.CYCLE_2021_2024;
        PsychologueTotalHoursValidator.validateByCycle(cycle, totalHours, errorHandler);
        assertFalse(errorHandler.getErrors().isEmpty());
        assertEquals("Il manque 5 heures pour compléter le cycle 2021-2024", errorHandler.getErrors().get(0));
    }

}