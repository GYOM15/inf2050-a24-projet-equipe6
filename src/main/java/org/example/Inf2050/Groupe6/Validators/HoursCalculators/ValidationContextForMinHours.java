package org.example.Inf2050.Groupe6.Validators.HoursCalculators;

import java.util.Map;

/**
 * Contexte de validation pour encapsuler les heures minimales et calculées par catégorie.
 */
public record ValidationContextForMinHours(Map<String, Integer> minHoursByCategory, Map<String, Integer> actualHoursByCategory) {
}