package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferredHoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidateTransferredHours_ValidHours() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heures_transferees_du_cycle_precedent", 5);

        // Appel de la méthode à tester
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);

        // Vérification des résultats
        assertEquals(5, result);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateTransferredHours_NegativeHours() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heures_transferees_du_cycle_precedent", -3);

        // Appel de la méthode à tester
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);

        // Vérification des résultats
        assertEquals(0, result); // Les heures négatives sont ignorées
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures transférées est négatif. Il sera ajusté à 0.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateTransferredHours_ExceedingHours() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heures_transferees_du_cycle_precedent", 10);

        // Appel de la méthode à tester
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);

        // Vérification des résultats
        assertEquals(7, result); // Seules 7 heures doivent être comptabilisées
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures transférées excède 7. Seulement 7 heures seront comptabilisées.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateTransferredHours_ZeroHours() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);

        // Appel de la méthode à tester
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);

        // Vérification des résultats
        assertEquals(0, result); // Les heures sont valides mais à zéro
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateTransferredHours_NoErrorHandler() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("heures_transferees_du_cycle_precedent", 8);

        // Appel de la méthode à tester sans ErrorHandler
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, null);

        // Vérification des résultats
        assertEquals(8, result); // Retourne la valeur sans validation
    }
}