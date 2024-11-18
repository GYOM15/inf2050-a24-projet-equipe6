package GROUPE6_INF2050;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.JsonHandler;
import GROUPE6_INF2050.Utilities.FileTypeDetermine;
import GROUPE6_INF2050.Utilities.JsonFileUtility;

import java.io.IOException;

import static java.lang.System.exit;

public class Main {
    /**
     * Point d'entrée principal de l'application; vérifie d'abord la validité des arguments puis procède au traitement du fichier
     *
     * @param args Tableau contenant les chemins du fichier d'entrée et de sortie.
     * @throws Groupe6INF2050Exception Si une erreur survient pendant le traitement du fichier.
     */
    public static void main(String[] args) throws Groupe6INF2050Exception {
        if (!areArgumentsValid(args)) return;
        try {
            String fileType = new FileTypeDetermine().determineFileType(args[0]);
            processFileByType(args, fileType);
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Valide le nombre d'arguments passés au programme.
     *
     * @param args Tableau contenant les arguments passés à l'application.
     * @return true si le nombre d'arguments est valide, false sinon.
     */
    private static boolean areArgumentsValid(String[] args) {
        if (args.length != 2) {
            System.out.println("Nombre d'arguments invalide");
            exit(-1);
            return false;
        }
        return true;
    }


    /**
     * Gère le traitement des fichiers en fonction du type MIME spécifié. ci-dessous un exemple illustré avec un fichier pdf
     * Elle peut prendre en charge différents types de fichiers et leur appliquer un traitement spécifique.
     *
     * @param args      Tableau contenant les chemins du fichier d'entrée et de sortie.
     * @param fileType  Le type MIME du fichier (exemple : "application/json" ou "application/pdf").
     * @throws Groupe6INF2050Exception Si une erreur survient lors du traitement du fichier JSON.
     */
    private static void processFileByType(String[] args, String fileType) throws Groupe6INF2050Exception {
        switch (fileType) {
            case "application/pdf" -> System.out.println("C'est un fichier PDF. Traitement spécifique pour les fichiers PDF.");
            case "application/json" -> {
                JsonFileUtility obj = new JsonFileUtility(args[0], args[1]);
                JsonHandler.handleJson(obj);
            }
            default -> System.out.println("Type du fichier non supporté : " + fileType);
        }
    }
}
