package GROUPE6_INF2050.Exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Groupe6INF2050ExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Une erreur est survenue dans le groupe 6.";
        Groupe6INF2050Exception exception = new Groupe6INF2050Exception(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testExceptionType() {
        try {
            throw new Groupe6INF2050Exception("Erreur de test");
        } catch (Groupe6INF2050Exception e) {
            assertInstanceOf(Groupe6INF2050Exception.class, e);
        }
    }

    @Test
    void testExceptionInheritance() {
        try {
            throw new Groupe6INF2050Exception("Erreur de test");
        } catch (Groupe6INF2050Exception e) {
            assertTrue(true);
        }
    }

}