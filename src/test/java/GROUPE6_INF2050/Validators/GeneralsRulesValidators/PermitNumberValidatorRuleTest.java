package GROUPE6_INF2050.Validators.GeneralsRulesValidators;


import net.sf.json.JSONObject;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermitNumberValidatorRuleTest {
    private StringBuilder message;
    private JSONObject jsonObject;

    @BeforeEach
    void setUp() {
        message = new StringBuilder("error");
        jsonObject =new JSONObject();
    }

    @Test
    void testValidatePermitNumber_ArchitecteValid() {
        jsonObject.put("numero_de_permis", "A1234");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertTrue(result);
    }

    @Test
    void testValidatePermitNumber_PsychologueValid() {
        jsonObject.put("numero_de_permis", "83723-34");
        jsonObject.put("ordre", "psychologues");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertTrue(result);
    }

    @Test
    void testValidatePermitNumber_GeologueValid() {
        jsonObject.put("numero_de_permis", "GO3822");
        jsonObject.put("ordre", "géologues");
        jsonObject.put("nom", "Guy");
        jsonObject.put("prenom", "Olivier");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertTrue(result);
    }

    @Test
    void testValidatePermitNumber_PodiatreValid() {
        jsonObject.put("numero_de_permis", "83453");
        jsonObject.put("ordre", "podiatres");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertTrue(result);
    }

    @Test
    void testValidatePermitNumber_Null() {
        jsonObject.put("numero_de_permis", null);
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertFalse(result);
    }

    @Test
    void testValidatePermitNumber_WithSpaces() {
        jsonObject.put("numero_de_permis", "A 1234");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertFalse(result);
    }

    @Test
    void testValidatePermitNumber_InvalidFirstCharacter() {
        jsonObject.put("numero_de_permis", "X1234");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertFalse(result);
    }

    @Test
    void testValidatePermitNumber_InvalidDigits() {
        jsonObject.put("numero_de_permis", "A12B4");
        jsonObject.put("ordre", "architectes");
        boolean result = PermitNumberValidatorRule.isPermitNumberValid(jsonObject, message);
        assertFalse(result);
    }
}