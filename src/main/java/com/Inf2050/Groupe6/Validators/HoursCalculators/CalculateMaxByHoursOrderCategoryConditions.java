package main.java.com.Inf2050.Groupe6.Validators.HoursCalculators;

import main.java.com.Inf2050.Groupe6.Enums.ActivityCategory;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import main.java.com.Inf2050.Groupe6.Validators.ValidatorsByOrderAndCycle.ActivityFilters.ActivityJsonBuilderByCategoriesConditions;
import net.sf.json.JSONArray;

import java.util.List;
import java.util.Map;

public class CalculateMaxByHoursOrderCategoryConditions {

    // Liste des catégories et leurs limites maximales spécifiques
    private static final Map<String, Integer> MAX_HOURS_LIMITS = Map.of(
            ActivityCategory.PRESENTATION.getCategoryFromJsonObj(), 23,
            ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj(), 17,
            ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(), 23,
            ActivityCategory.REDACTION_PROFESSIONNELLE.getCategoryFromJsonObj(), 17,
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj(), 15
    );

    private static final List<String> LIST_PSYCHOLOGUE = List.of(
            ActivityCategory.CONFERENCE.getCategoryFromJsonObj()
    );

    private static final List<String> LIST_ARCHITECTES = List.of(
            ActivityCategory.PRESENTATION.getCategoryFromJsonObj(),
            ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj(),
            ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj(),
            ActivityCategory.REDACTION_PROFESSIONNELLE.getCategoryFromJsonObj()
    );

    /**
     * Calcule la différence pour les conférences des psychologues, si le total dépasse la limite.
     *
     * @param activities Liste des activités en JSON
     * @return La différence entre les heures totales et la limite maximale de 15 heures pour les conférences.
     */
    public static int validatePsychologueMaxHours(JSONArray activities, ErrorHandler errorHandler) {
        ActivityJsonBuilderByCategoriesConditions.filterByCategorieCondition(activities, LIST_PSYCHOLOGUE);
        int totalHours = ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getConferenceJsonObject(), null);
        return applyMaxLimit(totalHours, ActivityCategory.CONFERENCE.getCategoryFromJsonObj());
    }

    /**
     * Calcule la somme des différences entre les heures et les limites maximales pour les architectes.
     *
     * @param activities Liste des activités en JSON
     * @return La somme des différences pour chaque catégorie dépassant sa limite maximale.
     */
    public static int validateArchitecteMaxHours(JSONArray activities, ErrorHandler errorHandler) {
        ActivityJsonBuilderByCategoriesConditions.filterByCategorieCondition(activities, LIST_ARCHITECTES);
        int totalHoursPresentation = ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getPresentationJsonObject(), null);
        int totalHoursGroupeDiscussion = ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getDiscussionGroupJsonObject(), null);
        int totalHoursProjetRecherche = ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getResearchProjectJsonObject(), null);
        int totalHoursRedactionProfessionnelle = ActivityHoursCalculator.getTotalHours(ActivityJsonBuilderByCategoriesConditions.getRedactionProfessionnelleJsonObject(), null);
        return applyMaxLimit(totalHoursPresentation, ActivityCategory.PRESENTATION.getCategoryFromJsonObj())
                + applyMaxLimit(totalHoursGroupeDiscussion, ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj())
                + applyMaxLimit(totalHoursProjetRecherche, ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj())
                + applyMaxLimit(totalHoursRedactionProfessionnelle, ActivityCategory.REDACTION_PROFESSIONNELLE.getCategoryFromJsonObj());
    }

    /**
     * Calcule la différence entre les heures actuelles et la limite maximale pour une catégorie donnée.
     *
     * @param hours    Nombre d'heures actuel pour la catégorie
     * @param category La catégorie d'activité
     * @return La différence entre les heures et la limite maximale si elle est dépassée, sinon 0.
     */
    private static int applyMaxLimit(int hours, String category) {
        Integer maxHours = MAX_HOURS_LIMITS.get(category);
        return (maxHours != null && hours > maxHours) ? hours - maxHours : 0;
    }
}