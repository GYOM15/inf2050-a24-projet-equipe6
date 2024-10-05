package main.java.com;

import main.java.com.Inf2050.Groupe6.Handlers.JsonHandler;
import main.java.com.Inf2050.Groupe6.Exceptions.Groupe6INF2050Exception;
import main.java.com.Inf2050.Groupe6.Utilities.JsonFileTreatment;
import main.java.com.Inf2050.Groupe6.Utilities.FileTypeDetermine;

import java.io.IOException;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws Groupe6INF2050Exception {
<<<<<<< HEAD
        //Vérification du nombre d'arguments que doit prendre notre main en entrée dès le départ.
=======
>>>>>>> 1e2eb308657b172003cb745d001677b021f7e97d
        if (args.length != 2) {
            System.out.println("Nombre d'arguments invalide");
            exit(-1);
        }
        // On crée une instance de notre Déterminateur du type de fichier
        FileTypeDetermine fileTypeDetermine = new FileTypeDetermine();

        try {
            // On recupère le fichier d'entrée qui est notre premier élément dans le tableau des paramètres, args[0]
            String fileType = fileTypeDetermine.determineFileType(args[0]);
            switch (fileType) {
                case "application/pdf":
                    System.out.println("C'est un fichier PDF. Traitement spécifique pour les fichiers PDF.");
                    break;

                case "application/json":
                    JsonFileTreatment obj = new JsonFileTreatment(args[0], args[1]);
                    JsonHandler.handleJson(obj);
                    break;

                case "text/plain":
                    System.out.println("C'est un fichier texte. Traitement spécifique pour les fichiers texte.");

                    break;

                default:
                    System.out.println("Type du fichier non supporté : " + fileType);
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
