package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMaxHoursValidatorTest {
    private ErrorHandler errorHandler;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    public void testValidatePresentationHours_ValidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 10);
        jsonObject1.put("categorie", "présentation");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        // Création du JSONObject final contenant le JSONArray
        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        // Appel de la méthode à tester
        CategoryMaxHoursValidator.validatePresentationHours(jsonObjectFinal, errorHandler);

        // Vérification qu'il n'y a pas d'erreurs
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidatePresentationHours_InvalidHours() {
        // Création d'un JSON avec des heures dépassant le maximum pour la catégorie présentation
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 25);
        jsonObject1.put("categorie", "présentation");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        // Création du JSONObject final contenant le JSONArray
        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);
        // Appel de la méthode à tester
        CategoryMaxHoursValidator.validatePresentationHours(jsonObjectFinal, errorHandler);

        // Vérification qu'une erreur a été enregistrée
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie présentation dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateGroupDiscussionHours_ValidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 4);
        jsonObject1.put("categorie", "groupe de discussion");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CategoryMaxHoursValidator.validateGroupDiscussionHours(jsonObjectFinal, errorHandler);

        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateGroupDiscussionHours_InvalidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 18);
        jsonObject1.put("categorie", "groupe de discussion");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CategoryMaxHoursValidator.validateGroupDiscussionHours(jsonObjectFinal, errorHandler);

        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie groupe de discussion dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateProjetDeRechercheHours_ValidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 10);
        jsonObject1.put("categorie", "projet de recherche");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CategoryMaxHoursValidator.validateProjetDeRechercheHours(jsonObjectFinal, errorHandler);

        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateProjetDeRechercheHours_InvalidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 24);
        jsonObject1.put("categorie", "projet de recherche");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CategoryMaxHoursValidator.validateProjetDeRechercheHours(jsonObjectFinal, errorHandler);

        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie projet de recherche dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateRedactionProHours_ValidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 7);
        jsonObject1.put("categorie", "rédaction professionnelle");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CategoryMaxHoursValidator.validateRedactionProHours(jsonObjectFinal, errorHandler);

        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateRedactionProHours_InvalidHours() {
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("heures", 18);
        jsonObject1.put("categorie", "rédaction professionnelle");
        JSONArray activities = new JSONArray();
        activities.add(jsonObject1);

        JSONObject jsonObjectFinal = new JSONObject();
        jsonObjectFinal.put("activites", activities);

        CategoryMaxHoursValidator.validateRedactionProHours(jsonObjectFinal, errorHandler);

        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie rédaction professionnelle dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }



}