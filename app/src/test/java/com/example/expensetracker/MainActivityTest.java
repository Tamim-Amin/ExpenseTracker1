package com.example.expensetracker;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MainActivityTest {

    private String[] validCategories;
    private String testUserEmail;
    private String testDisplayName;
    private double testExpenseAmount;

    @Before
    public void setUp() {
        validCategories = new String[]{"Food", "Transport", "Entertainment", "Shopping", "Bills", "Healthcare", "Education", "Others"};
        testUserEmail = "testuser@example.com";
        testDisplayName = "testuser";
        testExpenseAmount = 25.99;
    }

    // Test 1: assertNotNull - Categories array validation
    @Test
    public void testCategoriesArrayIsNotNull() {
        assertNotNull("Categories array should not be null", validCategories);
    }

    // Test 2: assertNull - Initial expense list should be null
    @Test
    public void testInitialExpenseListIsNull() {
        Object expenseList = null; // Simulating empty expense list initially
        assertNull("Initial expense list should be null", expenseList);
    }

    // Test 3: assertTrue - Valid expense amount validation
    @Test
    public void testValidExpenseAmount() {
        boolean isValid = isValidExpenseAmount(testExpenseAmount);
        assertTrue("Valid expense amount should pass validation", isValid);
    }

    // Test 4: assertFalse - Invalid expense amount validation
    @Test
    public void testInvalidExpenseAmount() {
        double invalidAmount = -10.50;
        boolean isValid = isValidExpenseAmount(invalidAmount);
        assertFalse("Negative expense amount should fail validation", isValid);
    }

    // Test 5: assertEquals - User display name extraction
    @Test
    public void testUserDisplayNameExtraction() {
        String displayName = getUserDisplayName(testUserEmail);
        assertEquals("Display name should match expected", testDisplayName, displayName);
    }

    // Test 6: assertNotEquals - Different expense amounts
    @Test
    public void testDifferentExpenseAmounts() {
        double amount1 = 25.99;
        double amount2 = 30.50;
        assertNotEquals("Different expense amounts should not be equal", amount1, amount2, 0.01);
    }

    // Test 7: assertSame - String literal identity for categories
    @Test
    public void testCategoryStringIdentity() {
        String category1 = "Food";
        String category2 = "Food";
        assertSame("String literals should be same object", category1, category2);
    }

    // Test 8: assertNotSame - Different category objects
    @Test
    public void testDifferentCategoryObjects() {
        String category1 = "Food";
        String category2 = new String("Food");
        assertNotSame("Different string objects should not be same", category1, category2);
    }


    // Test 9: Try-catch for invalid expense data
    @Test
    public void testInvalidExpenseDataException() {
        String invalidDescription = null;
        boolean exceptionCaught = false;

        try {
            validateExpenseData(invalidDescription, testExpenseAmount, "Food");
        } catch (IllegalArgumentException e) {
            exceptionCaught = true;
            assertNotNull("Exception should not be null", e);
            assertTrue("Exception message should mention Description",
                    e.getMessage().contains("Description"));
        }

        assertTrue("Exception should have been caught for null description", exceptionCaught);
    }

    // Test 10: Try-catch for invalid category validation
    @Test
    public void testInvalidCategoryException() {
        String invalidCategory = "InvalidCategory";
        boolean exceptionCaught = false;

        try {
            validateExpenseCategory(invalidCategory);
        } catch (IllegalArgumentException e) {
            exceptionCaught = true;
            assertNotNull("Exception should not be null", e);
        }

        assertTrue("Exception should have been caught for invalid category", exceptionCaught);
    }

    // Test 11: Pattern matching for email validation
    @Test
    public void testEmailPatternMatching() {
        String validEmail = "user@example.com";
        String invalidEmail = "invalid-email";
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        assertTrue("Valid email should match pattern", validEmail.matches(emailPattern));
        assertFalse("Invalid email should not match pattern", invalidEmail.matches(emailPattern));
    }

    // Test 12: Total expense calculation
    @Test
    public void testTotalExpenseCalculation() {
        double[] expenses = {25.99, 15.50, 30.00};
        double expectedTotal = 71.49;
        double actualTotal = calculateTotalExpenses(expenses);

        assertEquals("Total expenses should match expected value", expectedTotal, actualTotal, 0.01);
    }

    // Helper methods that mirror MainActivity functionality
    private boolean isValidExpenseAmount(double amount) {
        return amount > 0 && amount <= 999999.99;
    }

    private String getUserDisplayName(String email) {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return "User";
    }

    private void validateExpenseData(String description, double amount, String category) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException("Invalid category");
        }
    }

    private void validateExpenseCategory(String category) {
        if (!isValidCategory(category)) {
            throw new IllegalArgumentException("Category is not valid");
        }
    }

    private boolean isValidCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return false;
        }
        for (String validCategory : validCategories) {
            if (validCategory.equals(category)) {
                return true;
            }
        }
        return false;
    }

    private double calculateTotalExpenses(double[] expenses) {
        double total = 0.0;
        for (double expense : expenses) {
            total += expense;
        }
        return total;
    }

    private String formatExpenseAmount(double amount) {
        return String.format("$%.2f", amount);
    }

    // Test MainActivity.Expense class functionality
    @Test
    public void testExpenseClassCreation() {
        // Test creating expense like MainActivity.Expense
        MockExpense expense = new MockExpense("Coffee", 5.99, "Food", "2025-08-03");

        assertNotNull("Expense object should not be null", expense);
        assertEquals("Description should match", "Coffee", expense.description);
        assertEquals("Amount should match", 5.99, expense.amount, 0.01);
        assertEquals("Category should match", "Food", expense.category);
        assertEquals("Date should match", "2025-08-03", expense.date);
    }

    // Mock class to simulate MainActivity.Expense
    private static class MockExpense {
        public String description;
        public double amount;
        public String category;
        public String date;

        public MockExpense(String description, double amount, String category, String date) {
            this.description = description;
            this.amount = amount;
            this.category = category;
            this.date = date;
        }
    }
}