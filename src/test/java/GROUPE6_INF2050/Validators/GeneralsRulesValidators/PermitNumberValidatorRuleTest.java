package GROUPE6_INF2050.Validators.GeneralsRulesValidators;


import net.sf.json.JSONObject;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermitNumberValidatorRuleTest {
    private ErrorHandler errorHandler;
    private JSONObject jsonObject;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
        jsonObject =new JSONObject();
    }

    @Test
    void testValidatePermitNumber_ArchitecteValid() {
        jsonObject.put("numero_de_permis", "A1234");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertTrue(result);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidatePermitNumber_PsychologueValid() {
        jsonObject.put("numero_de_permis", "83723-34");
        jsonObject.put("ordre", "psychologues");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertTrue(result);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidatePermitNumber_GeologueValid() {
        jsonObject.put("numero_de_permis", "GO3822");
        jsonObject.put("ordre", "géologues");
        jsonObject.put("nom", "Guy");
        jsonObject.put("prenom", "Olivier");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertTrue(result);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidatePermitNumber_PodiatreValid() {
        jsonObject.put("numero_de_permis", "83453");
        jsonObject.put("ordre", "podiatres");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertTrue(result);
        assertTrue(errorHandler.getErrors().isEmpty());
    }

    @Test
    void testValidatePermitNumber_Null() {
        jsonObject.put("numero_de_permis", null);
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals(1,errorHandler.getErrors().size());
    }

    @Test
    void testValidatePermitNumber_WithSpaces() {
        jsonObject.put("numero_de_permis", "A 1234");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
    }

    @Test
    void testValidatePermitNumber_InvalidFirstCharacter() {
        jsonObject.put("numero_de_permis", "X1234");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
    }

    @Test
    void testValidatePermitNumber_InvalidDigits() {
        jsonObject.put("numero_de_permis", "A12B4");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, errorHandler);
        assertFalse(result);
        assertEquals(1, errorHandler.getErrors().size());
    }
}