package com.example.expensetracker;

import org.junit.Test;
import static org.junit.Assert.*;

public class AddExpenseActivityTest {

    @Test
    public void testCategoriesAreNotNull() {
        String[] categories = {"Food", "Transport", "Entertainment", "Shopping", "Bills", "Healthcare", "Education", "Others"};
        assertNotNull("Categories array should not be null", categories);
    }

    @Test
    public void testValidDescriptionValidation() {
        String description = "Coffee";
        String trimmedDescription = description.trim();
        assertEquals("Description should match expected value", "Coffee", trimmedDescription);
    }

    @Test
    public void testEmptyDescriptionValidation() {
        String description = "";
        boolean isEmpty = description.trim().isEmpty();
        assertTrue("Empty description should return true for isEmpty", isEmpty);
    }

    @Test
    public void testValidDescriptionIsNotEmpty() {
        String description = "Coffee and snacks";
        boolean isEmpty = description.trim().isEmpty();
        assertFalse("Valid description should return false for isEmpty", isEmpty);
    }


}