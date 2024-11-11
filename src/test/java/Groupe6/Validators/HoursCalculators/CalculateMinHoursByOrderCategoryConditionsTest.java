package Groupe6.Validators.HoursCalculators;

import main.java.com.Inf2050.Groupe6.Enums.ActivityOrder;
import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculateMinHoursByOrderCategoryConditionsTest {
    @Test
    public void testCalculateMinHoursForGEOLOGUES() {
        JSONArray activities = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("description","Rédaction pour le magazine Architecture moderne");
        jsonObject.put("categorie","cours");
        jsonObject.put("heures", 22);
        jsonObject.put("date", "2024-02-13");
        activities.add(jsonObject);
        jsonObject.put("description","Rédaction pour le magazine Architecture moderne");
        jsonObject.put("categorie","projets de recherche");
        jsonObject.put("heures", 3);
        jsonObject.put("date", "2024-02-21");
        activities.add(jsonObject);
        jsonObject.put("description","Rédaction pour le magazine Architecture moderne");
        jsonObject.put("categorie","groupe de discussion");
        jsonObject.put("heures", 1);
        jsonObject.put("date", "2024-02-27");

        ErrorHandler errorHandler = new ErrorHandler();

        // Calcul des heures minimales pour les géologues
        int totalHours = CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(
                ActivityOrder.GEOLOGUES, activities, errorHandler);

        // Vérifier le total d'heures calculé
        assertEquals(26, totalHours);

        // Vérifier qu'aucune erreur n'a été enregistrée (toutes les catégories ont atteint les heures minimales)
        assertTrue(errorHandler.getErrors().isEmpty());
    }

//    @Test
//    public void testCalculateMinHoursForPSYCHOLOGUES() {
//        // Setup : Création d'activités pour les psychologues
//        JSONArray activities = new JSONArray();
//        activities.add(createActivity("cours", 25));  // 25 heures de cours
//
//        // Initialisation du gestionnaire d'erreurs
//        ErrorHandler errorHandler = new ErrorHandler();
//
//        // Calcul des heures minimales pour les psychologues
//        int totalHours = CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(
//                ActivityOrder.PSYCHOLOGUES, activities, errorHandler);
//
//        // Vérifier le total d'heures calculé
//        assertEquals(25, totalHours);
//
//        // Vérifier qu'aucune erreur n'a été enregistrée
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }
//
//    @Test
//    public void testCalculateMinHoursForARCHITECTES() {
//        // Setup : Création d'activités pour les architectes
//        JSONArray activities = new JSONArray();
//        activities.add(createActivity("cours", 17));  // 17 heures de cours
//
//        // Initialisation du gestionnaire d'erreurs
//        ErrorHandler errorHandler = new ErrorHandler();
//
//        // Calcul des heures minimales pour les architectes
//        int totalHours = CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(
//                ActivityOrder.ARCHITECTES, activities, errorHandler);
//
//        // Vérifier le total d'heures calculé
//        assertEquals(17, totalHours);
//
//        // Vérifier qu'aucune erreur n'a été enregistrée
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }
//
//    @Test
//    public void testMissingHoursForGEOLOGUES() {
//        // Setup : Création d'activités pour les géologues avec des heures insuffisantes
//        JSONArray activities = new JSONArray();
//        activities.add(createActivity("cours", 20));  // Seulement 20 heures de cours
//        activities.add(createActivity("projets de recherche", 1));  // 1 heure de projet de recherche
//        activities.add(createActivity("groupe de discussion", 0));  // Aucune heure de groupe de discussion
//
//        // Initialisation du gestionnaire d'erreurs
//        ErrorHandler errorHandler = new ErrorHandler();
//
//        // Calcul des heures minimales pour les géologues
//        int totalHours = CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(
//                ActivityOrder.GEOLOGUES, activities, errorHandler);
//
//        // Vérifier que les heures calculées sont inférieures à celles requises
//        assertEquals(21, totalHours);
//
//        // Vérifier que les erreurs sont enregistrées
//        assertTrue(errorHandler.getErrors().contains("Il manque 1 heures dans la catégorie groupe de discussion. Le minimum autorisé est de 1h."));
//        assertTrue(errorHandler.getErrors().contains("Il manque 2 heures dans la catégorie projets de recherche. Le minimum autorisé est de 3h."));
//    }
//
//    @Test
//    public void testUnknownOrder() {
//        // Test avec un ordre inconnu
//        JSONArray activities = new JSONArray();
//        activities.add(createActivity("cours", 20));  // 20 heures de cours
//
//        // Initialisation du gestionnaire d'erreurs
//        ErrorHandler errorHandler = new ErrorHandler();
//
//        // Calcul des heures minimales pour un ordre inconnu
//        int totalHours = CalculateMinHoursByOrderCategoryConditions.calculateMinHoursByCategoryConditions(
//                null, activities, errorHandler);
//
//        // Vérifier que le total d'heures est 0, car l'ordre est inconnu
//        assertEquals(0, totalHours);
//
//        // Vérifier qu'aucune erreur n'a été enregistrée pour un ordre inconnu
//        assertTrue(errorHandler.getErrors().isEmpty());
//    }

}