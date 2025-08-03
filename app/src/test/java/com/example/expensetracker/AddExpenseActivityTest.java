package com.example.expensetracker;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


import java.util.Calendar;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class AddExpenseActivityTest {


    // -------------------- Assertion tests --------------------
    @Test
    public void testCategoriesAreNotNull() {
        String[] categories = {"Food", "Transport", "Entertainment", "Shopping", "Bills", "Healthcare", "Education", "Others"};
        assertNotNull(categories, "Categories array should not be null");
    }

    @Test
    public void testCategoriesHaveCorrectCount() {
        String[] categories = {"Food", "Transport", "Entertainment", "Shopping", "Bills", "Healthcare", "Education", "Others"};
        assertEquals(8, categories.length, "Should have 8 categories");
    }

    @Test
    public void testTrimmedDescriptionNotEquals() {
        String originalDescription = "  Coffee  ";
        String trimmedDescription = originalDescription.trim();
        assertNotEquals(originalDescription, trimmedDescription, "Trimmed string should differ from original");
    }

    @Test
    public void testNullDescriptionHandling() {
        String description = null;
        assertNull(description, "Null description should remain null");
    }

    @Test
    public void testStringObjectIdentity() {
        String str1 = "Coffee";
        String str2 = "Coffee";
        assertSame(str1, str2, "String literals should be same object");
    }

    @Test
    public void testStringObjectDifference() {
        String str1 = "Coffee";
        String str2 = new String("Coffee");
        assertNotSame(str1, str2, "New string object should not be same");
    }

    @Test
    public void testInvalidAmountThrowsException() {
        assertThrows(NumberFormatException.class, () -> Double.parseDouble("abc123"),
                "Invalid amount should throw NumberFormatException");
    }


    // -------------------- Parameterized tests --------------------
    @ParameterizedTest
    @ValueSource(strings = {"10.50", "25.99", "100.00", "999999.99", "0.01"})
    public void testValidAmountsWithValueSource(String amount) {
        double parsedAmount = Double.parseDouble(amount);
        assertTrue(parsedAmount > 0);
        assertTrue(parsedAmount <= 999999.99);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-10.50", "1000000.00", "", "   "})
    public void testInvalidAmountsWithValueSource(String amount) {
        assertFalse(validateAmount(amount));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test-categories.csv", numLinesToSkip = 1)
    public void testCategoryValidationWithCsvFile(String category, boolean expected) {
        boolean isValid = validateCategory(category);
        assertEquals(expected, isValid);
    }

    @ParameterizedTest
    @MethodSource("provideDateTestData")
    public void testDatePatternWithMethodSource(String date, boolean expectedValid, String testCase) {
        String datePattern = "\\d{4}-\\d{2}-\\d{2}";
        boolean matches = date.matches(datePattern);
        assertEquals(expectedValid, matches, testCase);
    }

    static Stream<Object[]> provideDateTestData() {
        return Stream.of(
                new Object[]{"2025-08-03", true, "Valid date format"},
                new Object[]{"03-08-2025", false, "Invalid DD-MM-YYYY"}
        );
    }

    // -------------------- Helper methods and classes --------------------
    private boolean validateAmount(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) return false;
        try {
            double amount = Double.parseDouble(amountStr.trim());
            return amount > 0 && amount <= 999999.99;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateCategory(String category) {
        String[] validCategories = {"Food", "Transport", "Entertainment", "Shopping", "Bills", "Healthcare", "Education", "Others"};
        if (category == null || category.trim().isEmpty()) return false;
        for (String validCategory : validCategories) {
            if (validCategory.equals(category)) return true;
        }
        return false;
    }

    public interface ExpenseValidator {
        boolean validateDescription(String description);
        boolean validateAmount(String amount);
    }

    public interface CategoryService {
        boolean isValidCategory(String category);
    }

    public interface DateFormatter {
        String formatDate(Calendar calendar);
    }

    public interface ExpenseRepository {
        boolean saveExpense(MockExpense expense);
        int getExpenseCount();
    }

    public static class ExpenseManager {
        private final ExpenseValidator validator;
        private final CategoryService categoryService;
        private final DateFormatter dateFormatter;
        private final ExpenseRepository repository;

        public ExpenseManager(ExpenseValidator validator, CategoryService categoryService,
                              DateFormatter dateFormatter, ExpenseRepository repository) {
            this.validator = validator;
            this.categoryService = categoryService;
            this.dateFormatter = dateFormatter;
            this.repository = repository;
        }

        public boolean validateExpense(String description, String amount, String category) {
            return validator.validateDescription(description)
                    && validator.validateAmount(amount)
                    && categoryService.isValidCategory(category);
        }

        public boolean addExpense(String description, String amount, String category) {
            if (validateExpense(description, amount, category)) {
                MockExpense expense = new MockExpense(description, Double.parseDouble(amount), category,
                        dateFormatter.formatDate(Calendar.getInstance()));
                return repository.saveExpense(expense);
            }
            return false;
        }
    }

    public static class MockExpense {
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
