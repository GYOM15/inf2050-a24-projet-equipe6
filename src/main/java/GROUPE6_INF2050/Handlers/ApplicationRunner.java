package GROUPE6_INF2050.Handlers;

import GROUPE6_INF2050.Exceptions.Groupe6INF2050Exception;
import GROUPE6_INF2050.Reporting.StatisticsData;
import GROUPE6_INF2050.Utilities.FileTypeDetermine;
import GROUPE6_INF2050.Utilities.JsonFileUtility;
import GROUPE6_INF2050.Utilities.StatisticsFileManager;

import java.io.IOException;

public class ApplicationRunner {

    private final StatisticsFileManager statisticsFileManager;
    private StatisticsData statisticsData;
    private String option;

    /**
     * Constructeur par défaut qui initialise le gestionnaire de statistiques
     * avec la configuration par défaut.
     */
    public ApplicationRunner() {
        this.statisticsFileManager = new StatisticsFileManager();
    }


    /**
     * Constructeur qui permet d'injecter un gestionnaire de statistiques personnalisé.
     *
     * @param statisticsFileManager Instance personnalisée de StatisticsFileManager.
     */
    public ApplicationRunner(StatisticsFileManager statisticsFileManager) {
        this.statisticsFileManager = statisticsFileManager;
    }

    /**
     * Point d'entrée principal pour exécuter l'application.
     *
     * @param args Arguments de la ligne de commande. Accepte soit une option
     *             (-S ou -SR) soit deux fichiers (entrée et sortie).
     * @throws IOException En cas d'erreur d'accès aux fichiers.
     */
    public void run(String[] args) throws IOException {
        validateArguments(args);
        initialize();
        blockTryCatchForMainArgs(args);
    }

    /**
     * Traite les arguments principaux dans un bloc try-catch pour gérer les erreurs.
     *
     * @param args Arguments de la ligne de commande.
     */
    private void blockTryCatchForMainArgs(String[] args) {
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

    /**
     * Valide les arguments fournis par la ligne de commande.
     *
     * @param args Arguments à valider.
     * @throws IllegalArgumentException Si les arguments sont invalides.
     */
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

    /**
     * Initialise les ressources nécessaires, comme le chargement des statistiques.
     *
     * @throws IOException En cas d'échec de chargement des statistiques.
     */
    private void initialize() throws IOException {
        statisticsData = statisticsFileManager.loadStatistics();
    }

    /**
     * Traite le fichier d'entrée spécifié dans les arguments.
     *
     * @param args Arguments de la ligne de commande.
     * @throws IOException En cas d'erreur d'accès au fichier.
     * @throws Groupe6INF2050Exception En cas d'erreurs de validation.
     */
    private void processInputFile(String[] args) throws IOException, Groupe6INF2050Exception {
        if (args[0].equals("-S") || args[0].equals("-SR")) {
            return;
        }

        String fileType = new FileTypeDetermine().determineFileType(args[0]);
        if ("application/json".equals(fileType)) {
            processJsonFile(args);
        } else {
            System.out.println("Type de fichier non pris en charge : " + fileType);
        }
    }

    /**
     * Traite un fichier JSON en effectuant les validations nécessaires.
     *
     * @param args Arguments de la ligne de commande.
     * @throws IOException En cas d'erreur d'accès au fichier.
     * @throws Groupe6INF2050Exception En cas d'erreurs de validation.
     */
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