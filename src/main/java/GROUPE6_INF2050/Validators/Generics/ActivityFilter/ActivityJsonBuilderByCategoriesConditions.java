package GROUPE6_INF2050.Validators.Generics.ActivityFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityJsonBuilderByCategoriesConditions {

    private final Map<String, JSONArray> categorizedActivities;

    public ActivityJsonBuilderByCategoriesConditions() {
        this.categorizedActivities = new HashMap<>();
    }

    /**
     *
     * @param activities JSONArray contenant toutes les activités
     * @param categories Liste des catégories à filtrer
     */
    public void filterByCategorieCondition(JSONArray activities, List<String> categories) {
        clearAllFilters();
        for (int i = 0; i < activities.size(); i++) {
            try {
                Activity activity = new Activity(activities.getJSONObject(i));
                if (categories.contains(activity.getCategory())) {
                    categorizedActivities.computeIfAbsent(activity.getCategory(), _ -> new JSONArray()).add(activity.toJson());
                }
            } catch (Exception e) { System.err.println("Erreur lors du traitement d'une activité : " + e.getMessage()); }
        }
    }

    /**
     * Efface les catégories précédemment filtrées.
     */
    public void clearAllFilters() {
        categorizedActivities.clear();
    }

    /**
     * Retourne un tableau JSON pour une catégorie spécifique.
     *
     * @param category La catégorie à rechercher
     * @return Un tableau JSON des activités pour cette catégorie
     */
    public JSONArray getActivitiesByCategory(String category) {
        return categorizedActivities.getOrDefault(category, new JSONArray());
    }

    /**
     * Classe interne représentant une activité.
     */
    public static class Activity {
        private final String category;
        private final JSONObject json;

        public Activity(JSONObject json) {
            this.json = json;
            this.category = json.optString("categorie", null);
        }

        public String getCategory() {
            return category;
        }

        public JSONObject toJson() {
            return json;
        }
    }
}