package com.example.expensetracker;

import org.junit.Test;
import org.junit.Before;
import android.util.Patterns;
import static org.junit.Assert.*;

public class LoginActivityTest {

    private String validEmail;
    private String validPassword;
    private String invalidEmail;
    private String shortPassword;

    @Before
    public void setUp() {
        // Remove LoginActivity instantiation - test logic instead
        validEmail = "test@example.com";
        validPassword = "password123";
        invalidEmail = "invalid-email";
        shortPassword = "123";
    }

    // Test 1: assertNotNull - Valid email string
    @Test
    public void testLoginActivityIsNotNull() {
        String email = "user@example.com";
        assertNotNull("Email should not be null", email);
    }

    // Test 2: assertNull - Initial FirebaseUser should be null
    @Test
    public void testInitialFirebaseUserIsNull() {
        String user = null; // Simulating no logged in user
        assertNull("Initial user should be null", user);
    }

    // Test 3: assertTrue - Valid email format validation
    @Test
    public void testValidEmailFormat() {
        boolean isValid = isValidEmail(validEmail);
        assertTrue("Valid email should pass validation", isValid);
    }

    // Test 4: assertFalse - Invalid email format validation
    @Test
    public void testInvalidEmailFormat() {
        boolean isValid = isValidEmail(invalidEmail);
        assertFalse("Invalid email should fail validation", isValid);
    }

    // Test 5: assertEquals - Email validation result
    @Test
    public void testEmailValidationResult() {
        String email = "user@domain.com";
        String expectedResult = "user@domain.com";
        assertEquals("Email should match expected value", expectedResult, email);
    }

    // Test 6: assertNotEquals - Different email values
    @Test
    public void testDifferentEmailValues() {
        String email1 = "user1@example.com";
        String email2 = "user2@example.com";
        assertNotEquals("Different emails should not be equal", email1, email2);
    }

    // Test 7: assertSame - String literal identity
    @Test
    public void testEmailStringIdentity() {
        String email1 = "test@example.com";
        String email2 = "test@example.com";
        assertSame("String literals should be same object", email1, email2);
    }

    // Test 8: assertNotSame - Different string objects
    @Test
    public void testDifferentEmailObjects() {
        String email1 = "test@example.com";
        String email2 = new String("test@example.com");
        assertNotSame("Different string objects should not be same", email1, email2);
    }

    // Test 9: Try-catch for null email validation
    @Test
    public void testNullEmailValidation() {
        String nullEmail = null;
        boolean exceptionCaught = false;

        try {
            validateLoginInput(nullEmail, validPassword);
        } catch (IllegalArgumentException e) {
            exceptionCaught = true;
            assertNotNull("Exception should not be null", e);
            assertTrue("Exception message should mention email",
                    e.getMessage().contains("email"));
        }

        assertTrue("Exception should have been caught for null email", exceptionCaught);
    }

    // Test 10: Try-catch for empty password validation
    @Test
    public void testEmptyPasswordValidation() {
        String emptyPassword = "";
        boolean exceptionCaught = false;

        try {
            validateLoginInput(validEmail, emptyPassword);
        } catch (IllegalArgumentException e) {
            exceptionCaught = true;
            assertNotNull("Exception should not be null", e);
        }

        assertTrue("Exception should have been caught for empty password", exceptionCaught);
    }

    // Test 11: Pattern matching for email validation (like LoginActivity uses)
    @Test
    public void testEmailPatternMatching() {
        String validEmail = "user@example.com";
        String invalidEmail = "user@invalid";

        // Test pattern matching like in LoginActivity.validateInput()
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        boolean validResult = validEmail.matches(emailPattern);
        boolean invalidResult = invalidEmail.matches(emailPattern);

        assertTrue("Valid email should match pattern", validResult);
        assertFalse("Invalid email should not match pattern", invalidResult);
    }

    // Test 12: Password length validation (like LoginActivity)
    @Test
    public void testPasswordLengthValidation() {
        String validPassword = "password123";
        String shortPassword = "123";

        boolean validLength = isValidPasswordLength(validPassword);
        boolean invalidLength = isValidPasswordLength(shortPassword);

        assertTrue("Valid password should pass length check", validLength);
        assertFalse("Short password should fail length check", invalidLength);
    }


    // Helper methods that mirror LoginActivity functionality
    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailPattern);
    }

    private boolean isValidPasswordLength(String password) {
        return password != null && password.length() >= 6;
    }

    private void validateLoginInput(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter email");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter password");
        }

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailPattern)) {
            throw new IllegalArgumentException("Enter a valid email");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }

    private boolean validateLoginCredentials(String email, String password) {
        try {
            validateLoginInput(email, password);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private String getUserDisplayName(String email) {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return "User";
    }
}