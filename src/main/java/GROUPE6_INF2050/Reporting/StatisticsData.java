package GROUPE6_INF2050.Reporting;

import java.util.HashMap;
import java.util.Map;

public class StatisticsData {
    private static int totalDeclarations;
    private static int completeDeclarations;
    private static int incompleteOrInvalidDeclarations;
    private static int maleDeclarations;
    private static int femaleDeclarations;
    private static int unknownGenderDeclarations;
    private static int totalActivities;
    private static Map<String, Integer> activitiesByCategory = new HashMap<>();
    private static Map<String, Integer> completeDeclarationsByOrder = new HashMap<>();
    private static Map<String, Integer> validDeclarationsByOrder = new HashMap<>();
    private static int invalidPermitDeclarations;
    private static int validDeclaration;

    // Increment methods
    public static void incrementTotalDeclarations() {
        totalDeclarations++;
    }

    public static void incrementCompleteDeclarations() {
        completeDeclarations++;
    }

    public static void incrementIncompleteOrInvalidDeclarations() {
        incompleteOrInvalidDeclarations++;
    }

    public static void incrementValidDeclarations() {
        validDeclaration++;
    }

    public static void incrementMaleDeclarations() {
        maleDeclarations++;
    }

    public static void incrementFemaleDeclarations() {
        femaleDeclarations++;
    }

    public static void incrementUnknownGenderDeclarations() {
        unknownGenderDeclarations++;
    }

    public static void incrementTotalActivities(int count) {
        totalActivities += count;
    }

    public static void incrementActivitiesByCategory(String category) {
        activitiesByCategory.put(category, activitiesByCategory.getOrDefault(category, 0) + 1);
    }

    public static void incrementCompleteDeclarationsByOrder(String order) {
        completeDeclarationsByOrder.put(order, completeDeclarationsByOrder.getOrDefault(order, 0) + 1);
    }

    public static void incrementValidDeclarationsByOrder(String order) {
        validDeclarationsByOrder.put(order, validDeclarationsByOrder.getOrDefault(order, 0) + 1);
    }

    public static void incrementInvalidPermitDeclarations() {
        invalidPermitDeclarations++;
    }

    // Getters
    public static int getTotalDeclarations() {
        return totalDeclarations;
    }

    public static int getCompleteDeclarations() {
        return completeDeclarations;
    }

    public static int getIncompleteOrInvalidDeclarations() {
        return incompleteOrInvalidDeclarations;
    }

    public static int getValidDeclaration() {
        return validDeclaration;
    }

    public static int getMaleDeclarations() {
        return maleDeclarations;
    }

    public static int getFemaleDeclarations() {
        return femaleDeclarations;
    }

    public static int getUnknownGenderDeclarations() {
        return unknownGenderDeclarations;
    }

    public static int getTotalActivities() {
        return totalActivities;
    }

    public static Map<String, Integer> getActivitiesByCategory() {
        return activitiesByCategory;
    }

    public static Map<String, Integer> getCompleteDeclarationsByOrder() {
        return completeDeclarationsByOrder;
    }

    public static Map<String, Integer> getValidDeclarationsByOrder() {
        return validDeclarationsByOrder;
    }

    public static int getInvalidPermitDeclarations() {
        return invalidPermitDeclarations;
    }

    // Reset method for testing or re-initialization
    public static void reset() {
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
    }

    @Override
    public String toString() {
        return "StatisticsData{" +
                "totalDeclarations=" + totalDeclarations +
                ", completeDeclarations=" + completeDeclarations +
                ", incompleteOrInvalidDeclarations=" + incompleteOrInvalidDeclarations +
                ", maleDeclarations=" + maleDeclarations +
                ", femaleDeclarations=" + femaleDeclarations +
                ", unknownGenderDeclarations=" + unknownGenderDeclarations +
                ", totalActivities=" + totalActivities +
                ", activitiesByCategory=" + activitiesByCategory +
                ", completeDeclarationsByOrder=" + completeDeclarationsByOrder +
                ", validDeclarationsByOrder=" + validDeclarationsByOrder +
                ", invalidPermitDeclarations=" + invalidPermitDeclarations +
                '}';
    }
}