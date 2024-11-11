package Groupe6.Validators.HoursCalculators;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidationConfigForCategoriesWithMinHoursTest {
    @Test
    public void testInitializationWithValidData() {
        Map<String, Integer> minHoursByCategory = Map.of(
                "Architecte", 40,
                "Géologue", 55,
                "Psychologue", 90
        );
        List<String> categoryList = List.of("Architecte", "Géologue", "Psychologue");
        ValidationConfigForCategoriesWithMinHours config = new ValidationConfigForCategoriesWithMinHours(minHoursByCategory, categoryList);
        assertEquals(40, config.minHoursByCategory().get("Architecte"));
        assertEquals(55, config.minHoursByCategory().get("Géologue"));
        assertEquals(90, config.minHoursByCategory().get("Psychologue"));
        assertTrue(config.categoryList().contains("Architecte"));
        assertTrue(config.categoryList().contains("Géologue"));
        assertTrue(config.categoryList().contains("Psychologue"));
    }

    @Test
    public void testMinHoursByCategoryReturnsCorrectMap() {
        Map<String, Integer> minHoursByCategory = Map.of(
                "Architecte", 40,
                "Géologue", 55
        );
        List<String> categoryList = List.of("Architecte", "Géologue");
        ValidationConfigForCategoriesWithMinHours config = new ValidationConfigForCategoriesWithMinHours(minHoursByCategory, categoryList);
        assertEquals(40, config.minHoursByCategory().get("Architecte"));
        assertEquals(55, config.minHoursByCategory().get("Géologue"));
    }

    @Test
    public void testCategoryListReturnsCorrectList() {
        Map<String, Integer> minHoursByCategory = Map.of(
                "Architecte", 40
        );
        List<String> categoryList = List.of("Architecte");
        ValidationConfigForCategoriesWithMinHours config = new ValidationConfigForCategoriesWithMinHours(minHoursByCategory, categoryList);
        assertTrue(config.categoryList().contains("Architecte"));
    }

    @Test
    public void testInitializationWithEmptyData() {
        Map<String, Integer> minHoursByCategory = Map.of();
        List<String> categoryList = List.of();
        ValidationConfigForCategoriesWithMinHours config = new ValidationConfigForCategoriesWithMinHours(minHoursByCategory, categoryList);
        assertTrue(config.minHoursByCategory().isEmpty());
        assertTrue(config.categoryList().isEmpty());
    }

}