package main.java.com.Inf2050.Groupe6.Validators.HoursCalculators;

import java.util.List;
import java.util.Map;

/**
 * La classe ValidationConfig encapsule la configuration de validation des heures minimales par catégorie.
 * Elle contient les heures minimales requises pour chaque catégorie et la liste des catégories à valider.
 */
public record ValidationConfigForCategoriesWithMinHours(Map<String, Integer> minHoursByCategory, List<String> categoryList) {

    /**
     * Constructeur pour initialiser la configuration de validation des catégories avec un minimum d'heure.
     *
     * @param minHoursByCategory Un Map associant chaque catégorie à son nombre d'heures minimal requis.
     * @param categoryList       La liste des catégories d'activités pour lesquelles la validation est appliquée.
     */
    public ValidationConfigForCategoriesWithMinHours {
    }

    /**
     * Retourne la map des heures minimales requises pour chaque catégorie.
     *
     * @return Un Map associant chaque catégorie à son nombre d'heures minimal requis.
     */
    @Override
    public Map<String, Integer> minHoursByCategory() {
        return minHoursByCategory;
    }

    /**
     * Retourne la liste des catégories à valider.
     *
     * @return Une liste de chaînes représentant les catégories d'activités à valider.
     */
    @Override
    public List<String> categoryList() {
        return categoryList;
    }
}