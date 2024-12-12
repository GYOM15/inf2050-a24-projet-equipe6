package GROUPE6_INF2050.Validators.GeneralValidators;


import net.sf.json.JSONObject;
import GROUPE6_INF2050.Handlers.ErrorHandler;
import GROUPE6_INF2050.Validators.GeneralValidators.TransferredHoursValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferredHoursValidatorTest {
    private ErrorHandler errorHandler;
    private JSONObject jsonObject;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        jsonObject = new JSONObject();
    }

    @Test
    public void testValidateTransferredHours_ValidHours() {
        jsonObject.put("heures_transferees_du_cycle_precedent", 5);
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);
        assertEquals(5, result);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateTransferredHours_NegativeHours() {
        jsonObject.put("heures_transferees_du_cycle_precedent", -3);
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);
        assertEquals(0, result);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures transférées est négatif. Il sera ajusté à 0.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateTransferredHours_ExceedingHours() {
        jsonObject.put("heures_transferees_du_cycle_precedent", 10);
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);
        assertEquals(7, result);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures transférées excède 7. Seulement 7 heures seront comptabilisées.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateTransferredHours_ZeroHours() {
        jsonObject.put("heures_transferees_du_cycle_precedent", 0);
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, errorHandler);
        assertEquals(0, result);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateTransferredHours_NoErrorHandler() {
        jsonObject.put("heures_transferees_du_cycle_precedent", 8);
        int result = TransferredHoursValidator.validateTransferredHours(jsonObject, null);
        assertEquals(8, result);
    }
}