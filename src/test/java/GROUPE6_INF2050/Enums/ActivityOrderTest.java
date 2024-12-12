package GROUPE6_INF2050.Enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityOrderTest {

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
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("architectes");
        assertEquals(ActivityOrder.ARCHITECTES, order);
    }

    @Test
    void testSearchFromJsonOrder_CaseInsensitive() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("ARCHITECTES");
        assertEquals(ActivityOrder.ARCHITECTES, order);
    }

    @Test
    void testSearchFromJsonOrder_NoErrorHandler() {
        ActivityOrder order = ActivityOrder.searchFromJsonOrder("non-existant-order");
        assertEquals(ActivityOrder.ORDER_NON_VALIDE, order);
    }
}