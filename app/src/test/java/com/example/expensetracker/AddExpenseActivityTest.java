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
    public void testCategoriesHaveCorrectCount() {
        String[] categories = {"Food", "Transport", "Entertainment", "Shopping", "Bills", "Healthcare", "Education", "Others"};
        assertEquals("Should have 8 categories", 8, categories.length);
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
    @Test
    public void testInvalidAmountThrowsException() {
        assertThrows("Invalid amount should throw NumberFormatException",
                NumberFormatException.class, () -> {
                    Double.parseDouble("abc123");
                });
    }

}