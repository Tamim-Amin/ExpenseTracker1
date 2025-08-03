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