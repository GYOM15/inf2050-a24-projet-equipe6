package GROUPE6_INF2050;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Handlers.JsonHandler;
import GROUPE6_INF2050.Handlers.HandleGeneralRulesValidator;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.FileTypeDetermine;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        StatisticsFileManager statisticsFileManager = null;
        StatisticsData statisticsData = null;
        String option = null;

        try {
            if (!areArgumentsValid(args)) return;
            option = args[2];
            statisticsFileManager = new StatisticsFileManager();
            statisticsData = statisticsFileManager.loadStatistics();
            String fileType = new FileTypeDetermine().determineFileType(args[0]);
            processFileByType(args, fileType, statisticsData, statisticsFileManager);
        } catch (IOException e) {
            System.err.println("Erreur d'entrée/sortie : " + e.getMessage());
        } catch (Groupe6INF2050Exception e) {
            System.err.println("Erreur de validation : " + e.getMessage());
        } finally {
            handleFinalActions(option, statisticsData, statisticsFileManager);
        }
    }

    private static boolean areArgumentsValid(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Aucun argument valide fourni. Utilisez -S, -SR ou spécifiez des fichiers d'entrée et de sortie.");
        }
        if ((args[2].equals("-S") || args[2].equals("-SR")) && args.length > 4) {
            throw new IllegalArgumentException("Option invalide. Utilisez -S ou -SR suivi de fichiers d'entrée et de sortie.");
        }
        return true;
    }

    private static void processFileByType(String[] args, String fileType, StatisticsData statisticsData, StatisticsFileManager statisticsFileManager) throws IOException, Groupe6INF2050Exception {
        if ("application/json".equals(fileType)) {
            JsonFileUtility obj = new JsonFileUtility(args[0], args[1]);
            HandleGeneralRulesValidator generalRulesValidator = new HandleGeneralRulesValidator(statisticsFileManager);
            JsonHandler jsonHandler = new JsonHandler(generalRulesValidator);
            jsonHandler.handleJson(obj, statisticsData);
        } else {
            System.out.println("Type de fichier non pris en charge : " + fileType);
        }
    }

    private static void resetStatistics(StatisticsData statisticsData, StatisticsFileManager statisticsFileManager) throws IOException {
        statisticsData.reset();
        statisticsFileManager.saveStatistics(statisticsData);
        System.out.println("Statistiques réinitialisées.");
    }

    private static void displayStatistics(StatisticsData statisticsData) {
        System.out.println("Statistiques finales :");
        System.out.println(statisticsData);
    }

    private static void saveStatistics(StatisticsData statisticsData, StatisticsFileManager statisticsFileManager) {
        try {
            if (statisticsFileManager != null && statisticsData != null) {
                statisticsFileManager.saveStatistics(statisticsData);
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des statistiques : " + e.getMessage());
        }
    }

    private static void handleFinalActions(String option, StatisticsData statisticsData, StatisticsFileManager statisticsFileManager) {
        saveStatistics(statisticsData, statisticsFileManager);

        if ("-SR".equals(option)) {
            try {
                resetStatistics(statisticsData, statisticsFileManager);
            } catch (IOException e) {
                System.err.println("Erreur lors de la réinitialisation des statistiques : " + e.getMessage());
            }
        } else if ("-S".equals(option)) {
            displayStatistics(statisticsData);
        }
    }
}