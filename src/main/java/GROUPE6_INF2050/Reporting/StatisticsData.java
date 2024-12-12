package GROUPE6_INF2050.Reporting;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StatisticsData {
    private int totalDeclarations;
    private int completeDeclarations;
    private int incompleteOrInvalidDeclarations;
    private int maleDeclarations;
    private int femaleDeclarations;
    private int unknownGenderDeclarations;
    private int totalActivities;
    private final Map<String, Integer> activitiesByCategory = new HashMap<>();
    private final Map<String, Integer> completeDeclarationsByOrder = new HashMap<>();
    private final Map<String, Integer> validDeclarationsByOrder = new HashMap<>();
    private int invalidPermitDeclarations;

    public synchronized void incrementTotalDeclarations(int count) {
        totalDeclarations += count;
    }

    public synchronized void incrementCompleteDeclarations(int count) {
        completeDeclarations += count;
    }

    public synchronized void incrementIncompleteOrInvalidDeclarations(int count) {
        incompleteOrInvalidDeclarations += count;
    }

    public synchronized void incrementMaleDeclarations(int count) {
        maleDeclarations += count;
    }

    public synchronized void incrementFemaleDeclarations(int count) {
        femaleDeclarations += count;
    }

    public synchronized void incrementUnknownGenderDeclarations(int count) {
        unknownGenderDeclarations += count;
    }

    public synchronized void incrementTotalActivities(int count) {
        totalActivities += count;
    }

    public synchronized void incrementActivitiesByCategory(String category, int count) {
        activitiesByCategory.merge(category, count, Integer::sum);
    }

    public synchronized void incrementCompleteDeclarationsByOrder(String order, int count) {
        completeDeclarationsByOrder.merge(order, count, Integer::sum);
    }

    public synchronized void incrementValidDeclarationsByOrder(String order, int count) {
        validDeclarationsByOrder.merge(order, count, Integer::sum);
    }

    public synchronized void incrementInvalidPermitDeclarations(int count) {
        invalidPermitDeclarations += count;
    }

    public synchronized int getTotalDeclarations() {
        return totalDeclarations;
    }

    public synchronized int getCompleteDeclarations() {
        return completeDeclarations;
    }

    public synchronized int getIncompleteOrInvalidDeclarations() {
        return incompleteOrInvalidDeclarations;
    }

    public synchronized int getMaleDeclarations() {
        return maleDeclarations;
    }

    public synchronized int getFemaleDeclarations() {
        return femaleDeclarations;
    }

    public synchronized int getUnknownGenderDeclarations() {
        return unknownGenderDeclarations;
    }

    public synchronized int getTotalActivities() {
        return totalActivities;
    }

    public synchronized Map<String, Integer> getActivitiesByCategory() {
        return new HashMap<>(activitiesByCategory);
    }

    public synchronized Map<String, Integer> getCompleteDeclarationsByOrder() {
        return new HashMap<>(completeDeclarationsByOrder);
    }

    public synchronized Map<String, Integer> getValidDeclarationsByOrder() {
        return new HashMap<>(validDeclarationsByOrder);
    }

    public synchronized int getInvalidPermitDeclarations() {
        return invalidPermitDeclarations;
    }

    public synchronized void reset() {
        totalDeclarations = 0;
        completeDeclarations = 0;
        incompleteOrInvalidDeclarations = 0;
        maleDeclarations = 0;
        femaleDeclarations = 0;
        unknownGenderDeclarations = 0;
        totalActivities = 0;
        variablesWithMaps();
    }

    private void variablesWithMaps() {
        activitiesByCategory.clear();
        completeDeclarationsByOrder.clear();
        validDeclarationsByOrder.clear();
        invalidPermitDeclarations = 0;
        System.out.println("Statistics have been reset:");
        System.out.println(this);
    }

    /**
     * Charge les statistiques à partir d'un objet JSON.
     * @param jsonObject L'objet JSON contenant les statistiques.
     */
    public synchronized void populateFromJson(JSONObject jsonObject) {
        populateGeneralStatistics(jsonObject);
        populateActivitiesByCategory(jsonObject.optJSONObject("activitiesByCategory"));
        populateCompleteDeclarationsByOrder(jsonObject.optJSONObject("completeDeclarationsByOrder"));
        populateValidDeclarationsByOrder(jsonObject.optJSONObject("validDeclarationsByOrder"));
    }

    private void populateGeneralStatistics(JSONObject jsonObject) {
        incrementTotalDeclarations(jsonObject.optInt("totalDeclarations", 0));
        incrementCompleteDeclarations(jsonObject.optInt("completeDeclarations", 0));
        incrementIncompleteOrInvalidDeclarations(jsonObject.optInt("incompleteOrInvalidDeclarations", 0));
        incrementMaleDeclarations(jsonObject.optInt("maleDeclarations", 0));
        incrementFemaleDeclarations(jsonObject.optInt("femaleDeclarations", 0));
        incrementUnknownGenderDeclarations(jsonObject.optInt("unknownGenderDeclarations", 0));
        incrementTotalActivities(jsonObject.optInt("totalActivities", 0));
        incrementInvalidPermitDeclarations(jsonObject.optInt("invalidPermitDeclarations", 0));
    }

    private void populateActivitiesByCategory(JSONObject activities) {
        if (activities != null) {
            activities.keySet().forEach(key -> incrementActivitiesByCategory(key.toString(), activities.optInt(key.toString())));
        }
    }

    private void populateCompleteDeclarationsByOrder(JSONObject completeByOrder) {
        if (completeByOrder != null) {
            completeByOrder.keySet().forEach(key -> incrementCompleteDeclarationsByOrder(key.toString(), completeByOrder.optInt(key.toString())));
        }
    }

    private void populateValidDeclarationsByOrder(JSONObject validByOrder) {
        if (validByOrder != null) {
            validByOrder.keySet().forEach(key -> incrementValidDeclarationsByOrder(key.toString(), validByOrder.optInt(key.toString())));
        }
    }

    @Override
    public synchronized String toString() {
        return "StatisticsData{\n" + "  totalDeclarations=" + totalDeclarations + ",\n" + "  completeDeclarations=" + completeDeclarations +
                ",\n" + "  incompleteOrInvalidDeclarations=" + incompleteOrInvalidDeclarations + ",\n" + "  maleDeclarations=" + maleDeclarations +
                ",\n" + "  femaleDeclarations=" + femaleDeclarations + ",\n" + "  unknownGenderDeclarations=" + unknownGenderDeclarations +
                ",\n" + "  totalActivities=" + totalActivities + ",\n" + "  activitiesByCategory=" + (activitiesByCategory.isEmpty() ? "0" : activitiesByCategory) +
                ",\n" + "  completeDeclarationsByOrder=" + (completeDeclarationsByOrder.isEmpty() ? "0" : completeDeclarationsByOrder) +
                ",\n" + "  validDeclarationsByOrder=" + (validDeclarationsByOrder.isEmpty() ? "0" : validDeclarationsByOrder) +
                ",\n" + "  invalidPermitDeclarations=" + invalidPermitDeclarations + "\n" + '}';
    }
}