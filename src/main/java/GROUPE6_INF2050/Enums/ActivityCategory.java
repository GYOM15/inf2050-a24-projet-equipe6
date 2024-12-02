package GROUPE6_INF2050.Enums;

import GROUPE6_INF2050.Handlers.ErrorHandler;

/**
 * Cette Enumération nous permet d'ajouter et de mieux manipuler nos catégories à des fins de vérification.
 * Chaque catégorie peut avoir une limite d'heures ou être sans limite.
 */
public enum ActivityCategory {
    COURS("cours"),
    ATELIER("atelier"),
    SEMINAIRE("séminaire"),
    COLLOQUE("colloque"),
    CONFERENCE("conférence"),
    LECTURE_DIRIGEE("lecture dirigée"),
    PRESENTATION("présentation"),
    GROUPE_DE_DISCUSSION("groupe de discussion"),
    PROJET_DE_RECHERCHE("projet de recherche"),
    REDACTION_PROFESSIONNELLE("rédaction professionnelle"),
    CATEGORIE_NON_VALIDE("non valide");

    private final String categoryFromJsonObj;

    /**
     * Constructeur pour les catégories sans limitation d'heures.
     *
     * @param categoryFromJsonObj La catégorie du fichier Json
     */
    ActivityCategory(String categoryFromJsonObj) {
        this.categoryFromJsonObj = categoryFromJsonObj;
    }

    public String getCategoryFromJsonObj() {
        return categoryFromJsonObj;
    }
    /**
     * Recherche dans l'énumération si la catégorie contenue dans l'objet JSON correspond à l'une
     * des catégories déclarées dans l'énumération, tout en ignorant la casse.
     *
     * @param label        La catégorie récupérée de l'objet JSON.
     * @param errorHandler Enregistre l'erreur si la catégorie n'est pas valide.
     * @return La catégorie concernée si trouvée ou retourne CATEGORIE_NON_VALIDE.
     */
    public static ActivityCategory searchFromJsonCategory(String label, ErrorHandler errorHandler) {
        for (ActivityCategory category : ActivityCategory.values()) {
            if (category.categoryFromJsonObj.equalsIgnoreCase(label)) {
                return category;
            }
        }
        addErrorIfHandlerPresent(label, errorHandler);
        return CATEGORIE_NON_VALIDE;
    }

    /**
     * Ajoute une erreur au gestionnaire d'erreurs lorsqu'une catégorie n'est pas valide.
     *
     * @param label        La catégorie récupérée de l'objet JSON.
     * @param errorHandler Enregistre les erreurs. Si null, aucune action n'est effectuée.
     */
    private static void addErrorIfHandlerPresent(String label, ErrorHandler errorHandler) {
       ErrorHandler.addErrorIfNotNull(errorHandler,"La catégorie " + label + " n'est pas valide");
    }
}