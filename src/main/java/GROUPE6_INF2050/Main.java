package GROUPE6_INF2050;

import GROUPE6_INF2050.Handlers.ApplicationRunner;

public class Main {
    public static void main(String[] args) {
        try {
            ApplicationRunner applicationRunner = new ApplicationRunner();
            applicationRunner.run(args);
        } catch (Exception e) {
            System.err.println("Erreur critique : " + e.getMessage());
        }
    }
}