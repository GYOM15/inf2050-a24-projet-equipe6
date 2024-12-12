package GROUPE6_INF2050.Utilities;

import GROUPE6_INF2050.Reporting.StatisticsData;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.*;

public class StatisticsFileManager {
    private static final String RELATIVE_RESOURCE_PATH = "src/main/resources/statistics.json";
    private final String filePath;
    private final Object lock = new Object();

    /**
     * Constructeur par défaut.
     * Utilise le chemin par défaut pour localiser le fichier de statistiques.
     */
    public StatisticsFileManager() {
        this.filePath = resolveStatisticsPath();
    }

    /**
     * Constructeur avec chemin personnalisé.
     *
     * @param filePath Chemin absolu ou relatif du fichier de statistiques.
     */
    public StatisticsFileManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Charge les statistiques depuis le fichier JSON.
     * Si le fichier n'existe pas, initialise les statistiques avec des valeurs par défaut.
     *
     * @return Un objet `StatisticsData` contenant les statistiques chargées.
     * @throws IOException En cas d'erreur de lecture ou d'écriture.
     */
    public StatisticsData loadStatistics() throws IOException {
        synchronized (lock) {
            File file = new File(filePath);
            if (!file.exists()) {
                return initializeDefaultStatistics();
            }
            return readStatisticsFromFile(file);
        }
    }


    /**
     * Initialise un fichier de statistiques avec des valeurs par défaut.
     *
     * @return Un objet `StatisticsData` initialisé.
     * @throws IOException En cas d'erreur de création du fichier.
     */
    private StatisticsData initializeDefaultStatistics() throws IOException {
        StatisticsData statisticsData = new StatisticsData();
        saveStatistics(statisticsData);
        return statisticsData;
    }


    /**
     * Lit les statistiques depuis un fichier existant.
     *
     * @param file Fichier contenant les données statistiques.
     * @return Un objet `StatisticsData` contenant les statistiques lues.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    private StatisticsData readStatisticsFromFile(File file) throws IOException {
        StatisticsData statisticsData = new StatisticsData();
        String jsonContent = readFileContent(file);
        JSONObject jsonObject = parseJsonContent(jsonContent);
        statisticsData.populateFromJson(jsonObject);
        return statisticsData;
    }

    /**
     * Lit le contenu brut d'un fichier texte.
     *
     * @param file Fichier à lire.
     * @return Contenu du fichier sous forme de chaîne de caractères.
     * @throws IOException En cas d'erreur de lecture.
     */
    private String readFileContent(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().reduce("", String::concat);
        }
    }

    /**
     * Convertit une chaîne JSON en objet JSONObject.
     *
     * @param jsonContent Contenu JSON sous forme de chaîne.
     * @return Un objet `JSONObject` correspondant au contenu.
     */
    private JSONObject parseJsonContent(String jsonContent) {
        return (JSONObject) JSONSerializer.toJSON(jsonContent);
    }

    /**
     * Sauvegarde les statistiques dans le fichier JSON.
     *
     * @param statisticsData Données statistiques à sauvegarder.
     * @throws IOException En cas d'erreur d'écriture dans le fichier.
     */
    public void saveStatistics(StatisticsData statisticsData) throws IOException {
        synchronized (lock) {
            writeFile(createStatisticsJson(statisticsData));
        }
    }

    /**
     * Crée un objet JSON à partir des données statistiques.
     *
     * @param statisticsData Données statistiques à convertir.
     * @return Un objet `JSONObject` contenant les données au format JSON.
     */
    private JSONObject createStatisticsJson(StatisticsData statisticsData) {
        JSONObject jsonObject = new JSONObject();
        addGeneralStatistics(jsonObject, statisticsData);
        addCategoryStatistics(jsonObject, statisticsData);
        addOrderStatistics(jsonObject, statisticsData);
        return jsonObject;
    }

    /**
     * Ajoute les statistiques générales à l'objet JSON.
     *
     * @param jsonObject Objet JSON à compléter.
     * @param statisticsData Données statistiques source.
     */
    private void addGeneralStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("totalDeclarations", statisticsData.getTotalDeclarations());
        jsonObject.put("completeDeclarations", statisticsData.getCompleteDeclarations());
        jsonObject.put("incompleteOrInvalidDeclarations", statisticsData.getIncompleteOrInvalidDeclarations());
        jsonObject.put("maleDeclarations", statisticsData.getMaleDeclarations());
        jsonObject.put("femaleDeclarations", statisticsData.getFemaleDeclarations());
        jsonObject.put("unknownGenderDeclarations", statisticsData.getUnknownGenderDeclarations());
        jsonObject.put("totalActivities", statisticsData.getTotalActivities());
        jsonObject.put("invalidPermitDeclarations", statisticsData.getInvalidPermitDeclarations());
    }

    /**
     * Ajoute les statistiques par catégorie à l'objet JSON.
     *
     * @param jsonObject Objet JSON à compléter.
     * @param statisticsData Données statistiques source.
     */
    private void addCategoryStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("activitiesByCategory", statisticsData.getActivitiesByCategory());
    }


    /**
     * Ajoute les statistiques par ordre à l'objet JSON.
     *
     * @param jsonObject Objet JSON à compléter.
     * @param statisticsData Données statistiques source.
     */
    private void addOrderStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("completeDeclarationsByOrder", statisticsData.getCompleteDeclarationsByOrder());
        jsonObject.put("validDeclarationsByOrder", statisticsData.getValidDeclarationsByOrder());
    }

    /**
     * Écrit un objet JSON dans le fichier cible.
     *
     * @param jsonObject Objet JSON à écrire.
     * @throws IOException En cas d'erreur d'écriture.
     */
    private void writeFile(JSONObject jsonObject) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonObject.toString(4));
        }
    }

    /**
     * Résout le chemin absolu du fichier de statistiques.
     *
     * @return Le chemin absolu du fichier de statistiques.
     */
    private String resolveStatisticsPath() {
        return FilePathResolver.resolvePath(RELATIVE_RESOURCE_PATH);
    }


}