package main.java.com.Inf2050.Groupe6.Enums;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;


/**
 * Cette Enumération nous permet d'ajouter de mieux manipuler nos categories à des fins de vérification
 * Chaque catégorie peut avoir une limite d'heures associée ou être sans limite.
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
     * @param categoryFromJsonObj la catégorie du fichier Json
     * Le deuxième paramètre est mis pour les catégories n'ayant pas le nombre d'heures limitées donc le max des int.
<<<<<<< HEAD
    * */
=======
     * */
>>>>>>> 1e2eb308657b172003cb745d001677b021f7e97d
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
     * @return retourne le maximum d'heures pour chaque catégorie
     * */
    public int getMaxHours() {
        return maxHours;
    }

    /**
     * Recherche dans l'énumération si la catégorie contenu dans l'objet Json, correspond à l'une
     * de celle déclarées dans l'énumération tout en ignorant la casse
     *
     * @param label qui est la catégorie recupérée de l'objet json à partir de laquelle on vérifie le libélé
     *              de chaque élément de l'énumération
     * @param errorHandler enregistre l'erreur si la catégorie n'est pas valide
     * @return la catégorie concernée si trouvée ou retourne CATEGORIE_NON_VALIDE
     * */
    public static ActivityCategory searchFromJsonCategory(String label, ErrorHandler errorHandler) {
        for (ActivityCategory category : ActivityCategory.values()) {
            if (category.categoryFromJsonObj.equalsIgnoreCase(label)) {
                return category;
            }
        }
        // Ajoute une erreur si la catégorie n'est pas valide
        if (errorHandler != null) {
            errorHandler.addCategoryError("La catégorie " + label+" n'est pas valide");
        }
        return CATEGORIE_NON_VALIDE;
    }
}
