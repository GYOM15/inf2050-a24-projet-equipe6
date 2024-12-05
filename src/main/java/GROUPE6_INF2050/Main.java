package GROUPE6_INF2050;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.JsonHandler;
import GROUPE6_INF2050.Handlers.HandleGeneralRulesValidator;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.FileTypeDetermine;
import GROUPE6_INF2050.Utilities.JsonFileUtility;

import java.io.IOException;

public class Main {
    /**
     * Entry point for the application; validates arguments and processes the input file.
     *
     * @param args Array containing the paths of the input and output files.
     * @throws Groupe6INF2050Exception If an error occurs while processing the file.
     */
    public static void main(String[] args) throws Groupe6INF2050Exception {
        if (!areArgumentsValid(args)) return;

        try {
            // Create an instance of StatisticsData
            StatisticsData statisticsData = new StatisticsData();

            // Determine the file type
            String fileType = new FileTypeDetermine().determineFileType(args[0]);

            // Process the file based on its type
            processFileByType(args, fileType, statisticsData);

            // Handle output options for statistics
            if (args[2].equals("-S")) {
                System.out.println(statisticsData); // Display collected statistics
            } else if (args[2].equals("-SR")) {
                statisticsData.reset(); // Reset the statistics
            }
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

    /**
     * Validates the number of arguments passed to the program.
     *
     * @param args Array containing the arguments passed to the application.
     * @return true if the number of arguments is valid, false otherwise.
     */
    private static boolean areArgumentsValid(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Aucun argument valide fourni. Utilisez -S, -SR ou spécifiez des fichiers d'entrée et de sortie.");
        }
        if (args[2].equals("-S") || args[2].equals("-SR")) {
            if (args.length > 4) {
                throw new IllegalArgumentException("Option invalide. Utilisez -S ou -SR suivi de fichiers d'entrée et de sortie.");
            }
        }
        return true;
    }

    /**
     * Processes files based on the MIME type.
     * Supports different file types and applies specific processing logic.
     *
     * @param args           Array containing the paths of the input and output files.
     * @param fileType       The MIME type of the file (e.g., "application/json").
     * @param statisticsData Instance of StatisticsData to track statistics.
     * @throws Groupe6INF2050Exception If an error occurs during JSON file processing.
     */
    private static void processFileByType(String[] args, String fileType, StatisticsData statisticsData) throws Groupe6INF2050Exception {
        switch (fileType) {
            case "application/pdf" -> System.out.println("C'est un fichier PDF. Traitement spécifique pour les fichiers PDF.");
            case "application/json" -> {
                // Create a JsonFileUtility for the input and output files
                JsonFileUtility obj = new JsonFileUtility(args[0], args[1]);

                // Inject dependencies and process the JSON file
                HandleGeneralRulesValidator generalRulesValidator = new HandleGeneralRulesValidator();
                JsonHandler jsonHandler = new JsonHandler(generalRulesValidator);

                // Pass the StatisticsData instance to handleJson
                jsonHandler.handleJson(obj, statisticsData);
            }
            default -> System.out.println("Type du fichier non supporté : " + fileType);
        }
    }
}