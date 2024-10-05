package main.java.com.Inf2050.Groupe6.Exceptions;

/**
 * Exception personnalisée pour les erreurs spécifiques à l'application.
 * Hérite de la classe Exception et permet de définir des messages d'erreur personnalisés.
 */
public class Groupe6INF2050Exception extends Exception {

    /**
     * Constructeur permettant de créer une exception avec un message d'erreur personnalisé.
     *
     * @param message Le message d'erreur qui sera renvoyé avec l'exception.
     */
    public Groupe6INF2050Exception(String message) {
        super(message);
    }
}
