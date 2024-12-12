package GROUPE6_INF2050.Reporting;

import GROUPE6_INF2050.Utilities.JsonFileUtility;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import GROUPE6_INF2050.Enums.ActivityCategory;

import java.util.EnumMap;
import java.util.Map;

/**
 * Classe dédiée aux statistiques des activités dans les déclarations.
 */
public class ActivityStatistics {

    /**
     * Renvoie le nombre total d'activités valides après vérification des catégories.
     *
     * @param jsonFileUtility Utilitaire JSON pour accéder aux activités.
     * @return Le nombre total d'activités valides.
     */
    public static int getTotalValidActivities(JsonFileUtility jsonFileUtility) {
        JSONArray activities = jsonFileUtility.getJsonArray();
        int count = 0;
        for (Object activityObj : activities) {
            if (activityObj instanceof JSONObject activity) {
                String category = activity.optString("categorie", "");
                if (ActivityCategory.searchFromJsonCategory(category, null) != ActivityCategory.CATEGORIE_NON_VALIDE) { count++; }
            }
        } return count;
    }

    /**
     * Renvoie le nombre d'activités par catégorie.
     *
     * @param jsonFileUtility Utilitaire JSON pour accéder aux activités.
     * @return Une carte (Map) avec les catégories comme clés et le nombre d'activités comme valeurs.
     */
    public static Map<ActivityCategory, Integer> getTotalActivitiesByCategory(JsonFileUtility jsonFileUtility) {
        JSONArray activities = jsonFileUtility.getJsonArray();
        Map<ActivityCategory, Integer> categoryCounts = new EnumMap<>(ActivityCategory.class);
        activities.forEach(activityObj -> {
            JSONObject activity = (JSONObject) activityObj;
            String categoryLabel = activity.optString("categorie", "");
            ActivityCategory category = ActivityCategory.searchFromJsonCategory(categoryLabel, null);
            if (category != ActivityCategory.CATEGORIE_NON_VALIDE) {
                categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
            }
        }); return categoryCounts;
    }
}