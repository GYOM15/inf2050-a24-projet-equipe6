package GROUPE6_INF2050.Handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void testAddError() {
        errorHandler.addError("Une erreur générale");
        List<String> errors = errorHandler.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Une erreur générale", errors.get(0));
    }

    @Test
    void testAddCategoryError() {
        errorHandler.addError("Une erreur de catégorie");
        List<String> errors = errorHandler.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Une erreur de catégorie", errors.get(0));
    }

    @Test
    void testAddActivityError() {
        errorHandler.addActivityError("Activité 1", "Erreur de validation");
        List<String> errors = errorHandler.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Erreur pour l'activité 'Activité 1': Erreur de validation", errors.get(0));
    }

    @Test
    void testAddCycleError() {
        errorHandler.addCycleError("Cycle A", "Erreur de cycle");
        List<String> errors = errorHandler.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Erreur pour le cycle 'Cycle A': Erreur de cycle", errors.get(0));
    }

    @Test
    void testAddPermitError() {
        errorHandler.addPermitError("123456", "Erreur de numéro de permis");
        List<String> errors = errorHandler.getErrors();
        assertEquals(1, errors.size());
        assertEquals("Erreur pour le numéro de permis '123456' : Erreur de numéro de permis", errors.get(0));
    }

    @Test
    void testGetErrorsInitiallyEmpty() {
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testHasErrorsInitiallyFalse() {
        assertTrue(errorHandler.hasErrors());
    }

    @Test
    void testHasErrorsAfterAdding() {
        errorHandler.addError("Une nouvelle erreur");
        assertFalse(errorHandler.hasErrors());
    }


}