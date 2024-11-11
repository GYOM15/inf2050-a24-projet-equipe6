package org.example.Inf2050.Groupe6.Validators.ValidatorsByOrderAndCycle.ActivityFilters;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.example.Inf2050.Groupe6.Enums.ActivityCategory;

import java.util.List;
import java.util.Objects;

public class ActivityJsonBuilderByCategoriesConditions {

    private final JSONArray coursActivities;
    private final JSONArray discussionGroupActivities;
    private final JSONArray researchProjectActivities;
    private final JSONArray architectCategoriesActivitiesForMinHoursCheck;
    private final JSONArray conferenceActivities;
    private final JSONArray presentationActivities;
    private final JSONArray redactionProfessionnelleActivities;


    public JSONObject getCoursJsonObject() {
        return coursJsonObject;
    }

    public JSONObject getResearchProjectJsonObject() {
        return researchProjectJsonObject;
    }

    public JSONObject getDiscussionGroupJsonObject() {
        return discussionGroupJsonObject;
    }

    public JSONObject getPresentationJsonObject() {
        return presentationJsonObject;
    }

    public JSONObject getRedactionProfessionnelleJsonObject() {
        return redactionProfessionnelleJsonObject;
    }

    public JSONObject getConferenceJsonObject() {
        return conferenceJsonObject;
    }

    private final JSONObject coursJsonObject;
    private final JSONObject researchProjectJsonObject;
    private final JSONObject discussionGroupJsonObject;
    private final JSONObject architectJsonObject;
    private final JSONObject presentationJsonObject;
    private final JSONObject redactionProfessionnelleJsonObject;
    private final JSONObject conferenceJsonObject;

    public ActivityJsonBuilderByCategoriesConditions() {
        this.coursActivities = new JSONArray();
        this.discussionGroupActivities = new JSONArray();
        this.researchProjectActivities = new JSONArray();
        this.architectCategoriesActivitiesForMinHoursCheck = new JSONArray();
        this.conferenceActivities = new JSONArray();
        this.presentationActivities = new JSONArray();
        this.redactionProfessionnelleActivities = new JSONArray();

        this.coursJsonObject = new JSONObject();
        this.researchProjectJsonObject = new JSONObject();
        this.discussionGroupJsonObject = new JSONObject();
        this.architectJsonObject = new JSONObject();
        this.presentationJsonObject = new JSONObject();
        this.redactionProfessionnelleJsonObject = new JSONObject();
        this.conferenceJsonObject = new JSONObject();
    }

    /**
     * Filtre les activités selon les catégories spécifiées et les répartit dans les tableaux et objets JSON appropriés.
     *
     * @param activities JSONArray contenant toutes les activités
     * @param categories Liste des catégories à filtrer
     */
    public void filterByCategorieCondition(JSONArray activities, List<String> categories) {
        clearAllFilters();
        for (int i = 0; i < activities.size(); i++) {
            JSONObject activity = activities.getJSONObject(i);
            String activityCategory = ActivityCategory.searchFromJsonCategory(activity.getString("categorie"), null).getCategoryFromJsonObj();
            if (categories.contains(activityCategory) && !Objects.equals(activityCategory, ActivityCategory.CATEGORIE_NON_VALIDE.getCategoryFromJsonObj())) {
                categorizeActivity(activity, activityCategory);
            }
        }
        updateJsonObjects();
    }

    private void categorizeActivity(JSONObject activity, String activityCategory) {
        switch (activityCategory) {
            case "cours" -> coursActivities.add(activity);
            case "groupe de discussion" -> discussionGroupActivities.add(activity);
            case "projet de recherche" -> researchProjectActivities.add(activity);
            case "conférence" -> conferenceActivities.add(activity);
            case "présentation" -> presentationActivities.add(activity);
            case "rédaction professionnelle" -> redactionProfessionnelleActivities.add(activity);
            default -> architectCategoriesActivitiesForMinHoursCheck.add(activity);
        }
    }

    private void clearAllFilters() {
        coursActivities.clear();
        discussionGroupActivities.clear();
        researchProjectActivities.clear();
        architectCategoriesActivitiesForMinHoursCheck.clear();
        conferenceActivities.clear();
        presentationActivities.clear();
        redactionProfessionnelleActivities.clear();

        coursJsonObject.clear();
        researchProjectJsonObject.clear();
        discussionGroupJsonObject.clear();
        architectJsonObject.clear();
        presentationJsonObject.clear();
        redactionProfessionnelleJsonObject.clear();
        conferenceJsonObject.clear();
    }

    private void updateJsonObjects() {
        coursJsonObject.put("activites", coursActivities);
        researchProjectJsonObject.put("activites", researchProjectActivities);
        discussionGroupJsonObject.put("activites", discussionGroupActivities);
        architectJsonObject.put("activites", architectCategoriesActivitiesForMinHoursCheck);
        presentationJsonObject.put("activites", presentationActivities);
        redactionProfessionnelleJsonObject.put("activites", redactionProfessionnelleActivities);
        conferenceJsonObject.put("activites", conferenceActivities);
    }

    public JSONArray getFilteredActivities() {
        JSONArray allActivities = new JSONArray();
        allActivities.addAll(coursActivities);
        allActivities.addAll(discussionGroupActivities);
        allActivities.addAll(researchProjectActivities);
        allActivities.addAll(conferenceActivities);
        allActivities.addAll(presentationActivities);
        allActivities.addAll(redactionProfessionnelleActivities);
        allActivities.addAll(architectCategoriesActivitiesForMinHoursCheck);
        return allActivities;
    }
}