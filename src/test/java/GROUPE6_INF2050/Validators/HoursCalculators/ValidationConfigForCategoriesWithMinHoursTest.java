package GROUPE6_INF2050.Validators.HoursCalculators;

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
                "Psychologue", 90,
                "Podiatre",60
        );
        List<String> categoryList = List.of("Architecte", "Géologue", "Psychologue","Podiatre");
        ValidationConfigForCategoriesWithMinHours config = new ValidationConfigForCategoriesWithMinHours(minHoursByCategory, categoryList);
        assertSame(40, config.minHoursByCategory().get("Architecte"));
        assertSame(55, config.minHoursByCategory().get("Géologue"));
        assertSame(90, config.minHoursByCategory().get("Psychologue"));
        assertSame(60, config.minHoursByCategory().get("Podiatre"));
        assertTrue(config.categoryList().contains("Architecte"));
        assertTrue(config.categoryList().contains("Géologue"));
        assertTrue(config.categoryList().contains("Psychologue"));
        assertTrue(config.categoryList().contains("Podiatre"));
    }

    @Test
    public void testMinHoursByCategoryReturnsCorrectMap() {
        Map<String, Integer> minHoursByCategory = Map.of(
                "Architecte", 40,
                "Géologue", 55
        );
        List<String> categoryList = List.of("Architecte", "Géologue");
        ValidationConfigForCategoriesWithMinHours config = new ValidationConfigForCategoriesWithMinHours(minHoursByCategory, categoryList);
        assertSame(40, config.minHoursByCategory().get("Architecte"));
        assertSame(55, config.minHoursByCategory().get("Géologue"));
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