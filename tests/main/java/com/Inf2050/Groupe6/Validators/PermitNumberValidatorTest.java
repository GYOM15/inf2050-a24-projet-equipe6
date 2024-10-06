package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermitNumberValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void testValidatePermitNumber_Valid() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero_de_permis", "A1234");

        boolean result = PermitNumberValidator.isPermitNumberValid(jsonObject, errorHandler);

        assertTrue(result);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidatePermitNumber_Null() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero_de_permis", null);

        boolean result = PermitNumberValidator.isPermitNumberValid(jsonObject, errorHandler);

        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }

    @Test
    void testValidatePermitNumber_WithSpaces() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero_de_permis", "A 1234");

        boolean result = PermitNumberValidator.isPermitNumberValid(jsonObject, errorHandler);

        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }

    @Test
    void testValidatePermitNumber_InvalidFirstCharacter() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero_de_permis", "X1234");

        boolean result = PermitNumberValidator.isPermitNumberValid(jsonObject, errorHandler);

        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }

    @Test
    void testValidatePermitNumber_InvalidDigits() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numero_de_permis", "A12B4");

        boolean result = PermitNumberValidator.isPermitNumberValid(jsonObject, errorHandler);

        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }



}