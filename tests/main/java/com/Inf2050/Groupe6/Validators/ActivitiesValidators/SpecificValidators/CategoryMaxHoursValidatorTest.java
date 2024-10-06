package main.java.com.Inf2050.Groupe6.Validators.ActivitiesValidators.SpecificValidators;

import main.java.com.Inf2050.Groupe6.Handlers.ErrorHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMaxHoursValidatorTest {
    private ErrorHandler errorHandler;
    private JSONObject jsonObject, activity;
    private JSONArray activities;

    @BeforeEach
    public void setUp() {
        errorHandler = new ErrorHandler();
        activities = new JSONArray();
        jsonObject = new JSONObject();
        activity = new JSONObject();
    }

    @Test
    public void testValidatePresentationHours_ValidHours() {
        activity.put("heures", 10);
        activity.put("categorie", "présentation");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validatePresentationHours(jsonObject, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidatePresentationHours_InvalidHours() {
        activity.put("heures", 25);
        activity.put("categorie", "présentation");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validatePresentationHours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie présentation dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateGroupDiscussionHours_ValidHours() {
        activity.put("heures", 4);
        activity.put("categorie", "groupe de discussion");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validateGroupDiscussionHours(jsonObject, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateGroupDiscussionHours_InvalidHours() {
        activity.put("heures", 18);
        activity.put("categorie", "groupe de discussion");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validateGroupDiscussionHours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie groupe de discussion dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateProjetDeRechercheHours_ValidHours() {
        activity.put("heures", 10);
        activity.put("categorie", "projet de recherche");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validateProjetDeRechercheHours(jsonObject, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateProjetDeRechercheHours_InvalidHours() {
        activity.put("heures", 24);
        activity.put("categorie", "projet de recherche");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validateProjetDeRechercheHours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie projet de recherche dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }

    @Test
    public void testValidateRedactionProHours_ValidHours() {
        activity.put("heures", 7);
        activity.put("categorie", "rédaction professionnelle");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validateRedactionProHours(jsonObject, errorHandler);
        assertEquals(0, errorHandler.getErrors().size());
    }

    @Test
    public void testValidateRedactionProHours_InvalidHours() {
        activity.put("heures", 18);
        activity.put("categorie", "rédaction professionnelle");
        activities.add(activity);
        jsonObject.put("activites", activities);
        CategoryMaxHoursValidator.validateRedactionProHours(jsonObject, errorHandler);
        assertEquals(1, errorHandler.getErrors().size());
        assertEquals("Le nombre d'heures pour la catégorie rédaction professionnelle dépasse le maximum autorisé.", errorHandler.getErrors().get(0));
    }



}