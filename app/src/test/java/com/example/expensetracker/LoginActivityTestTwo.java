package com.example.expensetracker;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LoginActivityTestTwo {



    @Test
    public void testEmptyEmail_shouldReturnFalse() {
        assertFalse(LoginActivity.isValidInput("", "password123"));
    }


    @Test
    public void testGetLoginErrorMessage_noUserRecord() {
        String msg = LoginActivity.getLoginErrorMessage("no user record");
        assertEquals("No account found with this email. Please register first.", msg);
    }

    @Test
    public void testGetLoginErrorMessage_wrongPassword() {
        String msg = LoginActivity.getLoginErrorMessage("wrong-password");
        assertEquals("Incorrect password. Please try again.", msg);
    }

    @Test
    public void testGetLoginErrorMessage_invalidCredential() {
        String msg = LoginActivity.getLoginErrorMessage("invalid-credential");
        assertEquals("Incorrect password. Please try again.", msg);
    }

    @Test
    public void testGetLoginErrorMessage_invalidEmail() {
        String msg = LoginActivity.getLoginErrorMessage("invalid-email");
        assertEquals("Invalid email format. Please check and try again.", msg);
    }

    @Test
    public void testGetLoginErrorMessage_userDisabled() {
        String msg = LoginActivity.getLoginErrorMessage("user-disabled");
        assertEquals("Your account has been disabled. Please contact support.", msg);
    }

    @Test
    public void testGetLoginErrorMessage_tooManyRequests() {
        String msg = LoginActivity.getLoginErrorMessage("too-many-requests");
        assertEquals("Too many failed attempts. Please try again later.", msg);
    }

    @Test
    public void testGetLoginErrorMessage_otherError() {
        String msg = LoginActivity.getLoginErrorMessage("some other error");
        assertEquals("Login failed: some other error", msg);
    }

    @Test
    public void testGetLoginErrorMessage_nullError() {
        String msg = LoginActivity.getLoginErrorMessage(null);
        assertEquals("Login failed. Please try again.", msg);
    }
    @ParameterizedTest
    @CsvSource({
            "user@example.com,password123,true",    // valid
            "'',password123,false",                 // empty email
            "bademail,password123,false",           // invalid email format
            "user@example.com,'',false",            // empty password
            "user@example.com,123,false"            // short password
    })
    void testIsValidInput(String email, String password, boolean expected) {
        boolean actual = LoginActivity.isValidInput(email, password);
        assertEquals(expected, actual);
    }
    @ParameterizedTest
    @CsvSource({
            "no user record,No account found with this email. Please register first.",
            "wrong-password,Incorrect password. Please try again.",
            "invalid-credential,Incorrect password. Please try again.",
            "invalid-email,Invalid email format. Please check and try again.",
            "user-disabled,Your account has been disabled. Please contact support.",
            "too-many-requests,Too many failed attempts. Please try again later.",
            "'',Login failed: ",         // empty string
            "'Some other error',Login failed: Some other error",
            "'',Login failed. Please try again."  // null error, special case (we'll test separately)
    })
    void testGetLoginErrorMessage(String firebaseError, String expectedMessage) {
        String actual = LoginActivity.getLoginErrorMessage(firebaseError.isEmpty() ? null : firebaseError);
        assertEquals(expectedMessage, actual);
    }
}

