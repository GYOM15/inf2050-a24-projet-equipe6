package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.FileTypeDetermine;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;

import java.io.IOException;

public class ApplicationRunner {
    private StatisticsFileManager statisticsFileManager;
    private StatisticsData statisticsData;
    private String option;

    public void run(String[] args) throws IOException{
        validateArguments(args);
        initialize(args);
        try {
            processInputFile(args);
        } catch (IOException e) {
            System.err.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (Groupe6INF2050Exception e) {
            System.err.println("Erreur de validation : " + e.getMessage());
        } finally {
            finalizeActions();
        }
    }

    private void validateArguments(String[] args) {
        if (args.length == 1) {
            if (!args[0].equals("-S") && !args[0].equals("-SR")) {
                throw new IllegalArgumentException("Option invalide. Utilisez '-S' ou '-SR' uniquement lorsque vous fournissez un seul argument.");
            }
            option = args[0];
        } else if (args.length == 2) {
            if (args[0].isEmpty() || args[1].isEmpty()) {
                throw new IllegalArgumentException("Les fichiers d'entrée et de sortie ne doivent pas être vides.");
            }
        } else {
            throw new IllegalArgumentException("Nombre d'arguments invalide. Utilisez 1 argument ('-S' ou '-SR') ou 2 arguments pour spécifier des fichiers d'entrée et de sortie.");
        }
    }

    private void initialize(String[] args) throws IOException {
        statisticsFileManager = new StatisticsFileManager();
        statisticsData = statisticsFileManager.loadStatistics();
    }

    private void processInputFile(String[] args) throws IOException, Groupe6INF2050Exception {
        String fileType = new FileTypeDetermine().determineFileType(args[0]);
        if ("application/json".equals(fileType)) {
            processJsonFile(args);
        } else {
            System.out.println("Type de fichier non pris en charge : " + fileType);
        }
    }

    private void processJsonFile(String[] args) throws IOException, Groupe6INF2050Exception {
        JsonFileUtility jsonFileUtility = new JsonFileUtility(args[0], args[1]);
        HandleGeneralRulesValidator generalRulesValidator = new HandleGeneralRulesValidator(statisticsFileManager);
        JsonHandler jsonHandler = new JsonHandler(generalRulesValidator);
        jsonHandler.handleJson(jsonFileUtility, statisticsData);
    }

    private void finalizeActions() {
        saveStatistics();
        if ("-SR".equals(option)) {
            resetStatistics();
        } else if ("-S".equals(option)) {
            displayStatistics();
        }
    }

    private void saveStatistics() {
        try {
            if (statisticsFileManager != null && statisticsData != null) {
                statisticsFileManager.saveStatistics(statisticsData);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des statistiques : " + e.getMessage());
        }
    }

    private void resetStatistics() {
        try {
            statisticsData.reset();
            statisticsFileManager.saveStatistics(statisticsData);
            System.out.println("Statistiques réinitialisées.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la réinitialisation des statistiques : " + e.getMessage());
        }
    }

    private void displayStatistics() {
        System.out.println("Statistiques finales :");
        System.out.println(statisticsData);
    }
}