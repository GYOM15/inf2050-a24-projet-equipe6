package GROUPE6_INF2050.Utilities;

import GROUPE6_INF2050.Reporting.StatisticsData;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.*;

public class StatisticsFileManager {
    private static final String RELATIVE_RESOURCE_PATH = "src/main/resources/statistics.json";
    private final String filePath;
    private final Object lock = new Object();

    public StatisticsFileManager() {
        this.filePath = resolveStatisticsPath();
    }

    public StatisticsFileManager(String filePath) {
        this.filePath = filePath;
    }

    public StatisticsData loadStatistics() throws IOException {
        synchronized (lock) {
            File file = new File(filePath);
            System.out.println("Using statistics file at: " + file.getAbsolutePath());
            if (!file.exists()) {
                return initializeDefaultStatistics();
            }
            return readStatisticsFromFile(file);
        }
    }

    private StatisticsData initializeDefaultStatistics() throws IOException {
        StatisticsData statisticsData = new StatisticsData();
        saveStatistics(statisticsData);
        return statisticsData;
    }

    private StatisticsData readStatisticsFromFile(File file) throws IOException {
        StatisticsData statisticsData = new StatisticsData();
        String jsonContent = readFileContent(file);
        populateStatisticsData(statisticsData, parseJsonContent(jsonContent));
        return statisticsData;
    }

    private String readFileContent(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines().reduce("", String::concat);
        }
    }

    private JSONObject parseJsonContent(String jsonContent) {
        return (JSONObject) JSONSerializer.toJSON(jsonContent);
    }

    private void populateStatisticsData(StatisticsData statisticsData, JSONObject jsonObject) {
        populateGeneralStatistics(statisticsData, jsonObject);
        populateActivityCategories(statisticsData, jsonObject.optJSONObject("activitiesByCategory"));
        populateCompleteDeclarationsByOrder(statisticsData, jsonObject.optJSONObject("completeDeclarationsByOrder"));
        populateValidDeclarationsByOrder(statisticsData, jsonObject.optJSONObject("validDeclarationsByOrder"));
        statisticsData.incrementInvalidPermitDeclarations(jsonObject.optInt("invalidPermitDeclarations", 0));
    }

    private void populateGeneralStatistics(StatisticsData statisticsData, JSONObject jsonObject) {
        statisticsData.incrementTotalDeclarations(jsonObject.optInt("totalDeclarations", 0));
        statisticsData.incrementCompleteDeclarations(jsonObject.optInt("completeDeclarations", 0));
        statisticsData.incrementIncompleteOrInvalidDeclarations(jsonObject.optInt("incompleteOrInvalidDeclarations", 0));
        statisticsData.incrementMaleDeclarations(jsonObject.optInt("maleDeclarations", 0));
        statisticsData.incrementFemaleDeclarations(jsonObject.optInt("femaleDeclarations", 0));
        statisticsData.incrementUnknownGenderDeclarations(jsonObject.optInt("unknownGenderDeclarations", 0));
        statisticsData.incrementTotalActivities(jsonObject.optInt("totalActivities", 0));
    }

    private void populateActivityCategories(StatisticsData statisticsData, JSONObject activities) {
        if (activities != null) {
            activities.keySet().forEach(key ->
                    statisticsData.incrementActivitiesByCategory(key.toString(), activities.optInt(key.toString())));
        }
    }

    private void populateCompleteDeclarationsByOrder(StatisticsData statisticsData, JSONObject completeByOrder) {
        if (completeByOrder != null) {
            completeByOrder.keySet().forEach(key ->
                    statisticsData.incrementCompleteDeclarationsByOrder(key.toString(), completeByOrder.optInt(key.toString())));
        }
    }

    private void populateValidDeclarationsByOrder(StatisticsData statisticsData, JSONObject validByOrder) {
        if (validByOrder != null) {
            validByOrder.keySet().forEach(key ->
                    statisticsData.incrementValidDeclarationsByOrder(key.toString(), validByOrder.optInt(key.toString())));
        }
    }

    public void saveStatistics(StatisticsData statisticsData) throws IOException {
        synchronized (lock) {
            writeFile(createStatisticsJson(statisticsData));
        }
    }

    private JSONObject createStatisticsJson(StatisticsData statisticsData) {
        JSONObject jsonObject = new JSONObject();
        addGeneralStatistics(jsonObject, statisticsData);
        addCategoryStatistics(jsonObject, statisticsData);
        addOrderStatistics(jsonObject, statisticsData);
        return jsonObject;
    }

    private void addGeneralStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("totalDeclarations", statisticsData.getTotalDeclarations());
        jsonObject.put("completeDeclarations", statisticsData.getCompleteDeclarations());
        jsonObject.put("incompleteOrInvalidDeclarations", statisticsData.getIncompleteOrInvalidDeclarations());
        jsonObject.put("maleDeclarations", statisticsData.getMaleDeclarations());
        jsonObject.put("femaleDeclarations", statisticsData.getFemaleDeclarations());
        jsonObject.put("unknownGenderDeclarations", statisticsData.getUnknownGenderDeclarations());
        jsonObject.put("totalActivities", statisticsData.getTotalActivities());
    }

    private void addCategoryStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("activitiesByCategory", statisticsData.getActivitiesByCategory());
    }

    private void addOrderStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("completeDeclarationsByOrder", statisticsData.getCompleteDeclarationsByOrder());
        jsonObject.put("validDeclarationsByOrder", statisticsData.getValidDeclarationsByOrder());
        jsonObject.put("invalidPermitDeclarations", statisticsData.getInvalidPermitDeclarations());
    }

    private void writeFile(JSONObject jsonObject) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonObject.toString(4));
        }
    }

    private String resolveStatisticsPath() {
        File currentDir = new File(System.getProperty("user.dir"));
        while (currentDir != null) {
            File potentialResource = new File(currentDir, StatisticsFileManager.RELATIVE_RESOURCE_PATH);
            if (potentialResource.exists()) {
                return potentialResource.getAbsolutePath();
            }
            currentDir = currentDir.getParentFile();
        }
        throw new RuntimeException("Unable to locate the resource: " + StatisticsFileManager.RELATIVE_RESOURCE_PATH);
    }
}