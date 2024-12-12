package GROUPE6_INF2050.Validators.GeneralsRulesValidators;


import net.sf.json.JSONObject;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.GeneralsRulesValidators.PermitNumberValidatorRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermitNumberValidatorTest {
    private ErrorHandler errorHandler;
    private JSONObject jsonObject;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
        jsonObject =new JSONObject();
    }

    @Test
    void testValidatePermitNumber_Valid() {
        jsonObject.put("numero_de_permis", "A1234");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertTrue(result);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidatePermitNumber_Null() {
        jsonObject.put("numero_de_permis", null);
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }

    @Test
    void testValidatePermitNumber_WithSpaces() {
        jsonObject.put("numero_de_permis", "A 1234");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }

    @Test
    void testValidatePermitNumber_InvalidFirstCharacter() {
        jsonObject.put("numero_de_permis", "X1234");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }

    @Test
    void testValidatePermitNumber_InvalidDigits() {
        jsonObject.put("numero_de_permis", "A12B4");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertTrue(errorHandler.getErrors().get(0).contains("Le numéro de permis doit être non-null, sans espaces, commencer par A, T, M ou K, et être suivi de 4 chiffres."));
    }



}