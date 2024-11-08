package main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.Façade;

import main.java.com.Inf2050.Groupe6.Enums.ActivityCategory;
import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.ActivityHoursCalculator;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByCycle.Filters.ActivityFilterByCategoriesConditions;
import net.sf.json.JSONArray;

import java.util.Arrays;
import java.util.List;


public class ValidateMinHoursByOrder {
    private static final List<String> LIST_GEOLOGUE =  Arrays.asList(ActivityCategory.COURS.getCategoryFromJsonObj(),
            ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(),ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj());

    private static final List<String> LIST_PSYCHOLOGUE = List.of(ActivityCategory.COURS.getCategoryFromJsonObj());
    private static final List<String> LIST_ARCHITECTES = Arrays.asList(
            ActivityCategory.COURS.getCategoryFromJsonObj(), ActivityCategory.ATELIER.getCategoryFromJsonObj(),
            ActivityCategory.SEMINAIRE.getCategoryFromJsonObj(),
            ActivityCategory.COLLOQUE.getCategoryFromJsonObj(), ActivityCategory.CONFERENCE.getCategoryFromJsonObj()
            , ActivityCategory.LECTURE_DIRIGEE.getCategoryFromJsonObj()
    );



    public static int calculer(ActivityOrder order, JSONArray activities, ErrorHandler errorHandler) {
        int heuresCours = 0;
        int heuresProjets = 0;
        int heuresGroupeDisc = 0;
        int heuresMin17Architectes = 0;
        switch (order){
            case GEOLOGUES : {
                ActivityFilterByCategoriesConditions.filterByCategorieCondition(activities, LIST_GEOLOGUE);
                heuresCours =  ActivityHoursCalculator.getTotalHours(ActivityFilterByCategoriesConditions.getJsonObject(), null);
                heuresProjets = ActivityHoursCalculator.getTotalHours(ActivityFilterByCategoriesConditions.getJsonObjectPr(), null);
                heuresGroupeDisc = ActivityHoursCalculator.getTotalHours(ActivityFilterByCategoriesConditions.getJsonObjectGrp(), null);
                if(heuresCours< 22) {
                    errorHandler.addError("Il manque"+ (22-heuresCours) +"Le minimum autorisé dans la catégorie cours est de 22h");
                }
                if (heuresProjets < 3) {
                    errorHandler.addError("Il manque"+ (3-heuresCours) +"Le minimum autorisé dans la catégorie cours est de 22h");
                }
                if (heuresGroupeDisc < 1) {
                    errorHandler.addError("Il manque"+ (1-heuresCours) +"Le minimum autorisé dans la catégorie cours est de 22h");
                }
                break;
            }
            case PSYCHOLOGUES :
                ActivityFilterByCategoriesConditions.filterByCategorieCondition(activities, LIST_PSYCHOLOGUE);
                heuresCours = ActivityHoursCalculator.getTotalHours(ActivityFilterByCategoriesConditions.getJsonObject(), null);
            case ARCHITECTES:
                ActivityFilterByCategoriesConditions.filterByCategorieCondition(activities, LIST_ARCHITECTES);
                heuresMin17Architectes = ActivityHoursCalculator.getTotalHours(ActivityFilterByCategoriesConditions.getJsonObjectArc(), null);
        }
        return heuresCours;
    }
}