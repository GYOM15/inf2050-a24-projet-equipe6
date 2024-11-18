package GROUPE6_INF2050.Validators.ValidatorsByOrderAndCycle.ActivityFilters;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

public class ActivityJsonBuilderByCategoriesConditions {

    private static final JSONArray coursActivities = new JSONArray();
    private static final JSONArray discussionGroupActivities = new JSONArray();
    private static final JSONArray researchProjectActivities = new JSONArray();
    private static final JSONArray architectCategoriesActivitiesForMinHoursCheck = new JSONArray();
    private static final JSONArray conferenceActivities = new JSONArray();
    private static final JSONArray presentationActivities = new JSONArray();
    private static final JSONArray redactionProfessionnelleActivities = new JSONArray();


    private static final JSONObject coursJsonObject = new JSONObject();
    private static final JSONObject researchProjectJsonObject = new JSONObject();
    private static final JSONObject discussionGroupJsonObject = new JSONObject();
    private static final JSONObject architectJsonObject = new JSONObject();
    private static final JSONObject presentationJsonObject = new JSONObject();
    private static final JSONObject redactionProfessionnelleJsonObject = new JSONObject();
    private static final JSONObject conferenceJsonObject = new JSONObject();

    public static JSONObject getResearchProjectJsonObject() { return researchProjectJsonObject; }
    public static JSONObject getDiscussionGroupJsonObject() { return discussionGroupJsonObject; }
    public static JSONObject getCoursJsonObject() { return coursJsonObject; }
    public static JSONObject getPresentationJsonObject() { return presentationJsonObject; }
    public static JSONObject getConferenceJsonObject() { return conferenceJsonObject; }
    public static JSONObject getRedactionProfessionnelleJsonObject() { return redactionProfessionnelleJsonObject;}

    /**
     * Filtre les activités selon les catégories spécifiées et les répartit dans les tableaux et objets JSON appropriés.
     *
     * @param activities JSONArray contenant toutes les activités
     * @param categories Liste des catégories à filtrer
     */
    public static void filterByCategorieCondition(JSONArray activities, List<String> categories) {
        // Réinitialise les tableaux pour éviter la duplication des données lors de chaque appel
        clearAllFilters();
        for (String category : categories) {
            for (int i = 0; i < activities.size(); i++) {
                JSONObject activity = activities.getJSONObject(i);
                String activityCategory = activity.getString("categorie");
                categorizeActivity(activity, activityCategory);
            }
        }
        updateJsonObjects();
    }

    /**
     * Trie une activité dans le tableau approprié en fonction de sa catégorie.
     *
     * @param activity L'activité à trier
     * @param activityCategory La catégorie de l'activité
     */
    private static void categorizeActivity(JSONObject activity, String activityCategory) {
        switch (activityCategory) {
            case "cours", "conférence" -> {
                architectCategoriesActivitiesForMinHoursCheck.add(activity);
                if ("cours".equals(activityCategory)) coursActivities.add(activity);
                else conferenceActivities.add(activity);
            }
            case "projet de recherche" -> researchProjectActivities.add(activity);
            case "groupe de discussion" -> discussionGroupActivities.add(activity);
            case "présentation" -> presentationActivities.add(activity);
            case "rédaction professionnelle" -> redactionProfessionnelleActivities.add(activity);
            default -> architectCategoriesActivitiesForMinHoursCheck.add(activity);
        }
    }

    /**
     * Réinitialise tous les tableaux de filtrage pour éviter des duplications lors de chaque nouvel appel.
     */
    private static void clearAllFilters() {
        coursActivities.clear();
        discussionGroupActivities.clear();
        researchProjectActivities.clear();
        architectCategoriesActivitiesForMinHoursCheck.clear();
    }

    /**
     * Met à jour les objets JSON avec les tableaux d'activités filtrées.
     */
    private static void updateJsonObjects() {
        researchProjectJsonObject.put("activites", researchProjectActivities);
        discussionGroupJsonObject.put("activites", discussionGroupActivities);
        coursJsonObject.put("activites", coursActivities);
        architectJsonObject.put("activites", architectCategoriesActivitiesForMinHoursCheck);
        presentationJsonObject.put("activites", presentationActivities);
        redactionProfessionnelleJsonObject.put("activites", redactionProfessionnelleActivities);
        conferenceJsonObject.put("activites", conferenceActivities);
    }
}