package main.java.com.Inf2050.Groupe6.Enums;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ActivityOrderTest {

    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void testIsCycleValidByOrder_Architectes_ValidCycle() {
        Cycle cycle = Cycle.getCycleByLabel("2023-2025");
        assertFalse(ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.ARCHITECTES));
    }

    @Test
    void testIsCycleValidByOrder_Architectes_InvalidCycle() {
        Cycle cycle = Cycle.getCycleByLabel("2018-2020");
        assertFalse(ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.ARCHITECTES));
    }

    @Test
    void testIsCycleValidByOrder_Geologues_ValidCycle() {
        Cycle cycle = Cycle.getCycleByLabel("2021-2024");
        assertFalse(ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.GEOLOGUES));
    }

    @Test
    void testIsCycleValidByOrder_Geologues_InvalidCycle() {
        Cycle cycle = Cycle.getCycleByLabel("2020-2025");
        assertTrue(ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.GEOLOGUES));
    }

    @Test
    void testIsCycleValidByOrder_Psychologues_ValidCycle() {
        Cycle cycle = Cycle.getCycleByLabel("2020-2025");
        assertFalse(ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.PSYCHOLOGUES));
    }

    @Test
    void testIsCycleValidByOrder_Psychologues_InvalidCycle() {
        Cycle cycle = Cycle.getCycleByLabel("2021-2024");
        assertTrue(ActivityOrder.isCycleValidByOrder(cycle, ActivityOrder.PSYCHOLOGUES));
    }

    @Test
    void testSearchFromJsonOrder_ValidOrder() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("architectes", errorHandler);
        assertEquals(ActivityOrder.ARCHITECTES, order);
    }

    @Test
    void testSearchFromJsonOrder_CaseInsensitive() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("ARCHITECTES", errorHandler);
        assertEquals(ActivityOrder.ARCHITECTES, order);
    }

    @Test
    void testSearchFromJsonOrder_InvalidOrder() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("non-existant-order", errorHandler);
        assertEquals(ActivityOrder.ORDER_NON_VALIDE, order);
        assertTrue(errorHandler.getErrors().contains("L'ordre non-existant-order n'est pas valide"));
    }

    @Test
    void testSearchFromJsonOrder_NullOrder() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder(null, errorHandler);
        assertEquals(ActivityOrder.ORDER_NON_VALIDE, order);
        assertTrue(errorHandler.getErrors().contains("L'ordre null n'est pas valide"));
    }

    @Test
    void testSearchFromJsonOrder_NoErrorHandler() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("non-existant-order", null);
        assertEquals(ActivityOrder.ORDER_NON_VALIDE, order);
    }
}