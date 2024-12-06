package GROUPE6_INF2050.Utilities;

import GROUPE6_INF2050.Reporting.StatisticsData;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.io.*;

/**
 * Classe pour gérer le chargement et la sauvegarde des données statistiques dans un fichier JSON.
 */
public class StatisticsFileManager {
    private static final String FILE_PATH = "src/main/resources/statistics.json";
    private final Object lock = new Object();

    public StatisticsData loadStatistics() throws IOException {
        synchronized (lock) {
            File file = new File(FILE_PATH);
            StatisticsData statisticsData = new StatisticsData();

            if (!file.exists()) {
                System.out.println("Fichier de statistiques introuvable. Initialisation des valeurs par défaut.");
                saveStatistics(statisticsData);
                return statisticsData;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String jsonContent = reader.lines().reduce("", String::concat);
                JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(jsonContent);

                statisticsData.incrementTotalDeclarations(jsonObject.optInt("totalDeclarations", 0));
                statisticsData.incrementCompleteDeclarations(jsonObject.optInt("completeDeclarations", 0));
                statisticsData.incrementIncompleteOrInvalidDeclarations(jsonObject.optInt("incompleteOrInvalidDeclarations", 0));
                statisticsData.incrementMaleDeclarations(jsonObject.optInt("maleDeclarations", 0));
                statisticsData.incrementFemaleDeclarations(jsonObject.optInt("femaleDeclarations", 0));
                statisticsData.incrementUnknownGenderDeclarations(jsonObject.optInt("unknownGenderDeclarations", 0));
                statisticsData.incrementTotalActivities(jsonObject.optInt("totalActivities", 0));

                JSONObject activities = jsonObject.optJSONObject("activitiesByCategory");
                if (activities != null) {
                    for (Object key : activities.keySet()) {
                        statisticsData.incrementActivitiesByCategory(key.toString(), activities.optInt(key.toString()));
                    }
                }

                JSONObject completeByOrder = jsonObject.optJSONObject("completeDeclarationsByOrder");
                if (completeByOrder != null) {
                    for (Object key : completeByOrder.keySet()) {
                        statisticsData.incrementCompleteDeclarationsByOrder(key.toString(), completeByOrder.optInt(key.toString()));
                    }
                }

                JSONObject validByOrder = jsonObject.optJSONObject("validDeclarationsByOrder");
                if (validByOrder != null) {
                    for (Object key : validByOrder.keySet()) {
                        statisticsData.incrementValidDeclarationsByOrder(key.toString(), validByOrder.optInt(key.toString()));
                    }
                }

                statisticsData.incrementInvalidPermitDeclarations(jsonObject.optInt("invalidPermitDeclarations", 0));
            }

            return statisticsData;
        }
    }

    public void saveStatistics(StatisticsData statisticsData) throws IOException {
        synchronized (lock) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("totalDeclarations", statisticsData.getTotalDeclarations());
            jsonObject.put("completeDeclarations", statisticsData.getCompleteDeclarations());
            jsonObject.put("incompleteOrInvalidDeclarations", statisticsData.getIncompleteOrInvalidDeclarations());
            jsonObject.put("maleDeclarations", statisticsData.getMaleDeclarations());
            jsonObject.put("femaleDeclarations", statisticsData.getFemaleDeclarations());
            jsonObject.put("unknownGenderDeclarations", statisticsData.getUnknownGenderDeclarations());
            jsonObject.put("totalActivities", statisticsData.getTotalActivities());
            jsonObject.put("activitiesByCategory", statisticsData.getActivitiesByCategory());
            jsonObject.put("completeDeclarationsByOrder", statisticsData.getCompleteDeclarationsByOrder());
            jsonObject.put("validDeclarationsByOrder", statisticsData.getValidDeclarationsByOrder());
            jsonObject.put("invalidPermitDeclarations", statisticsData.getInvalidPermitDeclarations());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                writer.write(jsonObject.toString(4));
            }
        }
    }
}