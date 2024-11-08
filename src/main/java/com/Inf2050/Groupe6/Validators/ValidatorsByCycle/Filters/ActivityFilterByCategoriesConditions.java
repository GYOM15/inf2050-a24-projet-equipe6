package main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.Filters;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;


public class ActivityFilterByCategoriesConditions {
    private String ct;
    private static final JSONArray activityByConditions = new JSONArray();
    private static final JSONArray activityByConditionsGrp = new JSONArray();
    private static final JSONArray activityByConditionsProj = new JSONArray();
    private static final JSONArray activityByConditionsForArchitectes = new JSONArray();
    private static final JSONObject jsonObject = new JSONObject();
    private static final JSONObject jsonObjectPr = new JSONObject();
    private static final JSONObject jsonObjectGrp = new JSONObject();
    private static final JSONObject jsonObjectArchitectes = new JSONObject();

    public static JSONObject getJsonObjectPr() {
        return jsonObjectPr;
    }

    public static JSONObject getJsonObjectArc() {
        return jsonObjectArchitectes;
    }


    public static JSONObject getJsonObjectGrp() {
        return jsonObjectGrp;
    }
    public static JSONObject getJsonObject() {
        return jsonObject;
    }

    public static void filterByCategorieCondition(JSONArray activities, List<String> categories) {
        for(String c : categories){
            for (int i = 0; i < activities.size(); i++) {
                JSONObject activity = activities.getJSONObject(i);
                String category = activity.getString("categorie");
                if (c.equals(category) && c.equals("cours")) {
                    activityByConditions.add(activity);
                }if (c.equals(category) && c.equals("projet de recherche")) {
                    activityByConditionsProj.add(activity);
                }if (c.equals(category) && c.equals("groupe de discussion")) {
                    activityByConditionsGrp.add(activity);
                }if (c.equals(category)) {
                    activityByConditionsForArchitectes.add(activity);
                }
            }
        }
        jsonObjectPr.put("activites",activityByConditionsProj);
        jsonObjectGrp.put("activites",activityByConditionsGrp);
        jsonObject.put("activites", activityByConditions);
        jsonObjectArchitectes.put("activites",activityByConditionsForArchitectes);
    }


}