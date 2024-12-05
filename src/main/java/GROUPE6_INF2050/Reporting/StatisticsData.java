package GROUPE6_INF2050.Reporting;

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
    private int validDeclaration;

    // Increment methods
    public void incrementTotalDeclarations() {
        totalDeclarations++;
    }

    public void incrementCompleteDeclarations() {
        completeDeclarations++;
    }

    public void incrementIncompleteOrInvalidDeclarations() {
        incompleteOrInvalidDeclarations++;
    }

    public void incrementValidDeclarations() {
        validDeclaration++;
    }

    public void incrementMaleDeclarations() {
        maleDeclarations++;
    }

    public void incrementFemaleDeclarations() {
        femaleDeclarations++;
    }

    public void incrementUnknownGenderDeclarations() {
        unknownGenderDeclarations++;
    }

    public void incrementTotalActivities(int count) {
        totalActivities += count;
    }

    public void incrementActivitiesByCategory(String category, int count) {
        activitiesByCategory.put(category, count);
    }

    public void incrementCompleteDeclarationsByOrder(String order) {
        completeDeclarationsByOrder.put(order, completeDeclarationsByOrder.getOrDefault(order, 0) + 1);
    }

    public void incrementValidDeclarationsByOrder(String order) {
        validDeclarationsByOrder.put(order, validDeclarationsByOrder.getOrDefault(order, 0) + 1);
    }

    public void incrementInvalidPermitDeclarations() {
        invalidPermitDeclarations++;
    }

    // Getters
    public Map<String, Integer> getActivitiesByCategory() {
        return new HashMap<>(activitiesByCategory);
    }

    public Map<String, Integer> getCompleteDeclarationsByOrder() {
        return new HashMap<>(completeDeclarationsByOrder);
    }

    public Map<String, Integer> getValidDeclarationsByOrder() {
        return new HashMap<>(validDeclarationsByOrder);
    }

    @Override
    public String toString() {
        return "StatisticsData{\n" +
                "  totalDeclarations=" + totalDeclarations + ",\n" +
                "  completeDeclarations=" + completeDeclarations + ",\n" +
                "  incompleteOrInvalidDeclarations=" + incompleteOrInvalidDeclarations + ",\n" +
                "  maleDeclarations=" + maleDeclarations + ",\n" +
                "  femaleDeclarations=" + femaleDeclarations + ",\n" +
                "  unknownGenderDeclarations=" + unknownGenderDeclarations + ",\n" +
                "  totalActivities=" + totalActivities + ",\n" +
                "  activitiesByCategory=" + (activitiesByCategory.isEmpty() ? "0" : activitiesByCategory) + ",\n" +
                "  completeDeclarationsByOrder=" + (completeDeclarationsByOrder.isEmpty() ? "0" : completeDeclarationsByOrder) + ",\n" +
                "  validDeclarationsByOrder=" + (validDeclarationsByOrder.isEmpty() ? "0" : validDeclarationsByOrder) + ",\n" +
                "  invalidPermitDeclarations=" + invalidPermitDeclarations + "\n" +
                '}';
    }


    public void reset() {
        totalDeclarations = 0;
        completeDeclarations = 0;
        incompleteOrInvalidDeclarations = 0;
        maleDeclarations = 0;
        femaleDeclarations = 0;
        unknownGenderDeclarations = 0;
        totalActivities = 0;
        activitiesByCategory.clear();
        completeDeclarationsByOrder.clear();
        validDeclarationsByOrder.clear();
        invalidPermitDeclarations = 0;
        validDeclaration = 0;
        System.out.println("Statistics have been reset:");
        System.out.println(this);
    }
}