package main.java.com.Inf2050.Groupe6.Enums;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;


/**
 * Cette Enumération nous permet d'ajouter et de mieux manipuler nos categories à des fins de vérification
 * Chaque catégorie peut avoir une limite d'heures ou être sans limite.
 * Sources : https://www.jmdoudoux.fr/java/dej/chap-enums.htm
 */
public enum ActivityCategory {
    COURS("cours"),
    ATELIER("atelier"),
    SEMINAIRE("séminaire"),
    COLLOQUE("colloque"),
    CONFERENCE("conférence"),
    LECTURE_DIRIGEE("lecture dirigée"),
    PRESENTATION("présentation", 23),
    GROUPE_DE_DISCUSSION("groupe de discussion", 17),
    PROJET_DE_RECHERCHE("projet de recherche", 23),
    REDACTION_PROFESSIONNELLE("rédaction professionnelle", 17),
    CATEGORIE_NON_VALIDE("non valide");

    private final String categoryFromJsonObj;
    private final int maxHours;

    /**
     * Ce constructeur est implémenté pour les catégories qui n'ont aucune limitation d'heures
     *
     * @param categoryFromJsonObj La catégorie du fichier Json
     * Le deuxième paramètre est mis pour les catégories n'ayant pas le nombre d'heures limitées donc le max des int.
    * */

    ActivityCategory(String categoryFromJsonObj) {
        this(categoryFromJsonObj, Integer.MAX_VALUE);
    }

    /**
     * Constructeur pour catégories avec limitation des heures
     *
     * @param categoryFromJsonObj la catégorie du fichier Json
     * @param maxHours Le maxximum d'heures pour les catégoriee ayant une limitation
     * */
    ActivityCategory(String categoryFromJsonObj, int maxHours) {
        this.categoryFromJsonObj = categoryFromJsonObj;
        this.maxHours = maxHours;
    }

    /** Recupère le nombre d'heures permis pour chaque categorie
     * @return Retourne le maximum d'heures pour chaque catégorie
     * */
    public int getMaxHours() {
        return maxHours;
    }

    /**
     * Recherche dans l'énumération si la catégorie contenu dans l'objet Json, correspond à l'une
     * de celle déclarées dans l'énumération tout en ignorant la casse
     *
     * @param label Qui est la catégorie recupérée de l'objet json à partir de laquelle on vérifie le libélé
     *              de chaque élément de l'énumération
     * @param errorHandler Enregistre l'erreur si la catégorie n'est pas valide
     * @return la catégorie concernée si trouvée ou retourne CATEGORIE_NON_VALIDE
     * */
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
     * Cette méthode permet de faire le contrôle avant de rajouter une erreur au gestionnaire d'erreurs lorsqu'une catégorie
     * n'est pas valide. De ce fait on peut juste faire la vérification sans enregistrer l'erreur, en lui passant null comme valeur
     *
     * @param label La catégorie recupérée de l'objet json à partir de laquelle on vérifie le libélé
     *      *              de chaque élément de l'énumération
     * @param errorHandler Enregistrer les erreurs. Si null, aucune action n'est effectuée.
     */
    private static void addErrorIfHandlerPresent(String label, ErrorHandler errorHandler) {
        if (errorHandler != null) {
            errorHandler.addError("La catégorie " + label +" n'est pas valide");
        }
    }
}
