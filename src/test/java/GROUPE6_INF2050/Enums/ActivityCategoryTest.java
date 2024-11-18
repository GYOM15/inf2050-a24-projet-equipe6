package GROUPE6_INF2050.Enums;

import GROUPE6_INF2050.Handlers.ErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivityCategoryTest {

    private ErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorHandler();
    }

    @Test
    void testGetCategoryFromJsonObj() {
        assertEquals("cours", ActivityCategory.COURS.getCategoryFromJsonObj());
        assertEquals("atelier", ActivityCategory.ATELIER.getCategoryFromJsonObj());
        assertEquals("séminaire", ActivityCategory.SEMINAIRE.getCategoryFromJsonObj());
        assertEquals("colloque", ActivityCategory.COLLOQUE.getCategoryFromJsonObj());
        assertEquals("conférence", ActivityCategory.CONFERENCE.getCategoryFromJsonObj());
        assertEquals("lecture dirigée", ActivityCategory.LECTURE_DIRIGEE.getCategoryFromJsonObj());
        assertEquals("présentation", ActivityCategory.PRESENTATION.getCategoryFromJsonObj());
        assertEquals("groupe de discussion", ActivityCategory.GROUPE_DE_DISCUSSION.getCategoryFromJsonObj());
        assertEquals("projet de recherche", ActivityCategory.PROJET_DE_RECHERCHE.getCategoryFromJsonObj());
        assertEquals("rédaction professionnelle", ActivityCategory.REDACTION_PROFESSIONNELLE.getCategoryFromJsonObj());
        assertEquals("non valide", ActivityCategory.CATEGORIE_NON_VALIDE.getCategoryFromJsonObj());
    }

    @Test
    void testSearchFromJsonCategory_validCategory() {
        ActivityCategory category = ActivityCategory.searchFromJsonCategory("conférence", errorHandler);
        assertEquals(ActivityCategory.CONFERENCE, category);
    }

    @Test
    void testSearchFromJsonCategory_caseInsensitive() {
        ActivityCategory category = ActivityCategory.searchFromJsonCategory("CONFÉRENCE", errorHandler);
        assertEquals(ActivityCategory.CONFERENCE, category);
    }

    @Test
    void testSearchFromJsonCategory_invalidCategory() {
        ActivityCategory category = ActivityCategory.searchFromJsonCategory("invalid_category", errorHandler);
        assertEquals(ActivityCategory.CATEGORIE_NON_VALIDE, category);
        assertTrue(errorHandler.getErrors().contains("La catégorie invalid_category n'est pas valide"));
    }

    @Test
    void testSearchFromJsonCategory_nullCategory() {
        ActivityCategory category = ActivityCategory.searchFromJsonCategory(null, errorHandler);
        assertEquals(ActivityCategory.CATEGORIE_NON_VALIDE, category);
        assertEquals("La catégorie null n'est pas valide", errorHandler.getErrors().getFirst());
    }

    @Test
    void testSearchFromJsonCategory_noErrorHandler() {
        ActivityCategory category = ActivityCategory.searchFromJsonCategory("invalid_category", null);
        assertEquals(ActivityCategory.CATEGORIE_NON_VALIDE, category);
    }
}