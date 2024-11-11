package main.java.com.Inf2050.Groupe6.Exceptions;

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
            assertTrue(e instanceof Groupe6INF2050Exception);
        }
    }

    @Test
    void testExceptionInheritance() {
        try {
            throw new Groupe6INF2050Exception("Erreur de test");
        } catch (Groupe6INF2050Exception e) {
            assertTrue(e instanceof Exception);
        }
    }

}