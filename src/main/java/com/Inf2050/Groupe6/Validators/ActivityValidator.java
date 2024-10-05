package main.java.com.Inf2050.Groupe6.Validators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Enums.ActivityCategory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est chargée de faire la validation de tout ce qui concerne les activités
 */
public class ActivityValidator {

    /**
     * Vérifie si la catégorie du fichier JSON correspond à l'une des catégories autorisées
     * @param jsonObject    Le JSON contenant les catégories des activités à valider
     * @param errorHandler  L'objet ErrorHandler pour enregistrer les erreurs de validation des catégories
     * */
    public void validateCategory(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        for (int i = 0; i < activities.size(); i++) {
            String category = activities.getJSONObject(i).getString("categorie");
            // On fait la vérification à partir de la méthode de recherche de l'Enumération
            ActivityCategory.searchFromJsonCategory(category, errorHandler);
        }
    }

    /**
     * Vérifie si le nombre d'heures pour les activités est de minimum 40heures
     * Si l'heure est négative ou dépasse 40, on rajoute l'erreur à la liste
     * @param jsonObject    Le JSON contenant les activités à valider avec leurs heures
     * @param errorHandler  L'objet ErroHandler pour enregistrer les erreurs si les heures totales du cycle sont inferieurs à 40
     * */
    public void isCycleMinHours40(JSONObject jsonObject, ErrorHandler errorHandler) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        int totalHours = 0;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.getJSONObject(i).getInt("heures") <1)
            {
                errorHandler.addActivityError(activities.getJSONObject(i).getString("description"),"Les heures d'une catégorie ne peuvent pas être inférieur à 1" );
            }
            totalHours += activities.getJSONObject(i).getInt("heures");
        }

        if (totalHours < 40) {
            errorHandler.addError("Erreur sur le nombnre d'heures total des Activités : Il manque " + (40 - totalHours) + " heures de formation pour compléter le cycle.");
        }
    }

    /**
     * Valide le nombre d'heures transférées d'une activité à une autre
     * Si une erreur trouvée, on la rajoute dans le errorHandler
     * @param jsonObject    Le JSON à partir du quel on accède au champs heures_transferees_du_cycle_precedent
     * @param errorHandler  L'objet ErrorHandler pour enregistrer les erreurs si le nombre d'heures transferable est inferieur à 0 ou supérieur à 7
     * */
    public int validateTransferredHours(JSONObject jsonObject, ErrorHandler errorHandler) {
        final int MAX_HOURS_TRANSFERRABLE =7;
        int transferredHours = jsonObject.getInt("heures_transferees_du_cycle_precedent");

        if (errorHandler!=null){
            if (transferredHours < 0) {
                errorHandler.addError("Le nombre d'heures transférées est négatif et sera ignoré.");
                return 0;
            } else if (transferredHours > MAX_HOURS_TRANSFERRABLE) {
                errorHandler.addError("Le nombre d'heures transférées excède 7. Seulement 7 heures seront comptabilisées.");
                return 7;
            }
        }

        return transferredHours;
    }

    /**
     * Vérifie si le nombre total d'heures des catégoties précisés ci-dessous est inférieur à 17
     * et on stocke les catégories qui ne vérifie pas cette condition dans un tableau
     * @param jsonObject    Le JSON contenant les catégories concernées
     * @param errorHandler  L'objet ErrorHandler pour enregistrer les erreurs si le total des heures est en dessous de 17
     */
    public void isCategoryMin17Hours(JSONObject jsonObject, ErrorHandler errorHandler) {
        // Stocke la liste des catégories qui ne vérifient pas la condition
        List<String> concernedCategories = new ArrayList<>();

        // On initialise les heures requises par les heures transférées, vu que cela doit compter dans le calcul
        int requiredHoursTotal = validateTransferredHours(jsonObject, null);

        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));

        for (int i = 0; i < activities.size(); i++) {
            String categoryFromJsonFile = activities.getJSONObject(i).getString("categorie");
            // On passe null à la methode searchFromJsonCategory car dans ce cas ci nous n'avons pas besoin de stocker les erreurs
            ActivityCategory activityCategory = ActivityCategory.searchFromJsonCategory(categoryFromJsonFile,null);

            // Valider uniquement les catégories qui comptent pour le minimum de 17 heures
            if (activityCategory == ActivityCategory.COURS || activityCategory == ActivityCategory.ATELIER
                    || activityCategory == ActivityCategory.SEMINAIRE || activityCategory == ActivityCategory.COLLOQUE
                    || activityCategory == ActivityCategory.CONFERENCE || activityCategory == ActivityCategory.LECTURE_DIRIGEE) {
                requiredHoursTotal += activities.getJSONObject(i).getInt("heures");

                // Stocke les catégories invalides à cette condition
                if (!concernedCategories.contains(categoryFromJsonFile)) {
                    concernedCategories.add(categoryFromJsonFile);
                }
            }
        }

        if (requiredHoursTotal < 17) {
            // On concatène toutes les catégories concernées et on les rajoute à notre errorHandler
            String categoriesList = String.join(", ", concernedCategories);
            errorHandler.addError("Il manque " + (17 - requiredHoursTotal) + " heures de formation dans les catégories suivantes pour compléter les 17 heures requises : " + categoriesList);
        }
    }

    /**
     * Cette methode est comme une méthode générique qui prend en paramètre une catégorie et éffectue le traitement approprié
     * Cela nous permet d'éviter la répétition de codes pour les catégories ayant les traitements qui se ressemblent
     * @param jsonObject    Le JSON contenant à partir du quel o recupère la catégorie concernée
     * @param concernedCategory     La catégorie JSON à valider, de telle sorte qu'elle ne dépasse pas le nombre d'heures autorisé
     */
    public void validateHoursForCategory(JSONObject jsonObject, ActivityCategory concernedCategory) {
        JSONArray activities = (JSONArray) JSONSerializer.toJSON(jsonObject.getString("activites"));
        for (int i = 0; i < activities.size(); i++) {
            String category = activities.getJSONObject(i).getString("categorie");
            ActivityCategory activityCategory = ActivityCategory.searchFromJsonCategory(category,null);
            if (activityCategory.equals(concernedCategory) && activities.getJSONObject(i).getInt("heures") > activityCategory.getMaxHours());
        }
    }

    /** Valide le nombre d'heures pour la categorie présentation
     * @param jsonObject Le JSON contenant les information de la catégorie présentation
     * */
    public void validatePresentationHours(JSONObject jsonObject) {
        validateHoursForCategory(jsonObject,ActivityCategory.PRESENTATION);
    }

    /** Valide le nombre d'heures pour la categorie droupe de discussion
     * @param jsonObject Le JSON contenant les information la catégorie groupe de discussion
     * */
    public void validateGroupDiscussionHours(JSONObject jsonObject) {
        validateHoursForCategory(jsonObject, ActivityCategory.GROUPE_DE_DISCUSSION);
    }

    /** Valide le nombre d'heures pour la categorie projet de recherche
     * @param jsonObject Le JSON contenant les information la catégorie projet de recherche
     * */
    public void validateProjetDeRechercheHours(JSONObject jsonObject) {
        validateHoursForCategory(jsonObject, ActivityCategory.PROJET_DE_RECHERCHE);
    }

    /** Valide le nombre d'heures pour la categorie rédaction professionnelle
     * @param jsonObject Le JSON contenant les information la catégorie rédation professionnelle
     * */
    public void validateRedactionProHours(JSONObject jsonObject) {
        validateHoursForCategory(jsonObject, ActivityCategory.REDACTION_PROFESSIONNELLE);
    }


}
