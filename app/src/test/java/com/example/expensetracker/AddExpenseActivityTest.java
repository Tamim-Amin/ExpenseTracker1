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

    @Test
    public void testTrimmedDescriptionNotEquals() {
        String originalDescription = "  Coffee  ";
        String trimmedDescription = originalDescription.trim();
        assertNotEquals("Trimmed string should differ from original", originalDescription, trimmedDescription);
    }

    @Test
    public void testNullDescriptionHandling() {
        String description = null;
        assertNull("Null description should remain null", description);
    }

    @Test
    public void testStringObjectIdentity() {
        String str1 = "Coffee";
        String str2 = "Coffee";
        assertSame("String literals should be same object", str1, str2);
    }

    @Test
    public void testStringObjectDifference() {
        String str1 = "Coffee";
        String str2 = new String("Coffee");
        assertNotSame("New string object should not be same", str1, str2);
    }
    
}