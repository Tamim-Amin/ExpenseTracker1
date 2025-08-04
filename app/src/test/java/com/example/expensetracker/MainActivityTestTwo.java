package com.example.expensetracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;

public class MainActivityTestTwo {
    @Test
    public void testGetCategoryColor_Food() {
        assertEquals(0xFF10B981, MainActivity.getCategoryColor("food"));
    }

    @Test
    public void testGetCategoryColor_Unknown() {
        assertEquals(0xFF6B7280, MainActivity.getCategoryColor("random"));
        assertNotEquals(0xFF6B7281, MainActivity.getCategoryColor("random"));
    }

    @ParameterizedTest
    @CsvSource({
            "food, 0xFF10B981",
            "transport, 0xFF3B82F6",
            "entertainment, 0xFFEF4444",
            "shopping, 0xFFF59E0B",
            "bills, 0xFF8B5CF6",
            "healthcare, 0xFFEC4899",
            "education, 0xFF06B6D4",
            "random, 0xFF6B7280"
    })
    public void testGetCategoryColor(String category, String expectedColor) {
        assertEquals(expectedColor, MainActivity.getCategoryColor(category));
    }
    @ParameterizedTest
    @ValueSource(strings = {"food", "Food", "FOOD", "FoOd"})
    public void testGetCategoryColor_FoodVariants(String category) {
        assertEquals(0xFF10B981, MainActivity.getCategoryColor(category));
    }

    @ParameterizedTest
    @ValueSource(strings = {"transport", "Transport", "TRANSPORT"})
    public void testGetCategoryColor_TransportVariants(String category) {
        assertEquals(0xFF3B82F6, MainActivity.getCategoryColor(category));
    }

    @ParameterizedTest
    @ValueSource(strings = {"education ", " education", " EDUCATION "})
    public void testGetCategoryColor_EducationWithSpaces(String category) {
        assertEquals(0xFF6B7280, MainActivity.getCategoryColor(category)); // unless you trim() in method
    }
}
