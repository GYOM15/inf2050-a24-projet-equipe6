package GROUPE6_INF2050.Validators.GeneralValidators;


import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.GeneralValidators.DateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidate_ValidDate() {
        String validDate = "2023-02-28";
        assertTrue(DateValidator.validate(validDate, errorHandler));
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidate_LeapYearDate() {
        String leapYearDate = "2020-02-29";
        assertTrue(DateValidator.validate(leapYearDate, errorHandler));
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    public void testValidate_InvalidDateFormat() {
        String invalidDate = "2023-02-30";
        assertFalse(DateValidator.validate(invalidDate, errorHandler));
        assertTrue(errorHandler.getErrors().contains("Format incorrect ou année non bisextille : 2023-02-30"));
    }

    @Test
    public void testValidate_InvalidMonth() {
        String invalidMonthDate = "2023-13-01";
        assertFalse(DateValidator.validate(invalidMonthDate, errorHandler));
        assertTrue(errorHandler.getErrors().contains("Format incorrect ou année non bisextille : 2023-13-01"));
    }

    @Test
    public void testValidate_InvalidDay() {
        String invalidDayDate = "2023-01-32";
        assertFalse(DateValidator.validate(invalidDayDate, errorHandler));
        assertTrue(errorHandler.getErrors().contains("Format incorrect ou année non bisextille : 2023-01-32"));
    }

    @Test
    public void testValidate_InvalidDateFormat_WithErrorMessage() {
        String invalidDateFormat = "01/01/2023";
        assertFalse(DateValidator.validate(invalidDateFormat, errorHandler));
        assertTrue(errorHandler.getErrors().get(0).contains("Format incorrect ou année non bisextille : 01/01/2023"));
    }
}