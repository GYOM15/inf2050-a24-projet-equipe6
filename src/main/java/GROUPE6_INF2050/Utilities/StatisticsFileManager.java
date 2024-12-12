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
        JSONObject jsonObject = parseJsonContent(jsonContent);
        statisticsData.populateFromJson(jsonObject);
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
        jsonObject.put("invalidPermitDeclarations", statisticsData.getInvalidPermitDeclarations());
    }

    private void addCategoryStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("activitiesByCategory", statisticsData.getActivitiesByCategory());
    }

    private void addOrderStatistics(JSONObject jsonObject, StatisticsData statisticsData) {
        jsonObject.put("completeDeclarationsByOrder", statisticsData.getCompleteDeclarationsByOrder());
        jsonObject.put("validDeclarationsByOrder", statisticsData.getValidDeclarationsByOrder());
    }

    private void writeFile(JSONObject jsonObject) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(jsonObject.toString(4));
        }
    }

    private String resolveStatisticsPath() {
        return FilePathResolver.resolvePath(RELATIVE_RESOURCE_PATH);
    }


}