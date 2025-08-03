package com.example.expensetracker;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;

import com.example.expensetracker.service.AuthService;
import com.example.expensetracker.service.UserService;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginActivityTest {

    @Mock
    AuthService mockAuthService;

    @Mock
    UserService mockUserService;

    @Mock
    FirebaseUser mockFirebaseUser;

    private LoginActivity loginActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create an instance of LoginActivity
        loginActivity = new LoginActivity();

        // Inject mocks into LoginActivity
        loginActivity.setAuthService(mockAuthService);
        loginActivity.setUserService(mockUserService);

        // Initialize EditTexts with dummy context to avoid NullPointerException
        loginActivity.etUsername = new EditText(ApplicationProvider.getApplicationContext());
        loginActivity.etPassword = new EditText(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void testLoginUser_SuccessfulLogin() {
        String email = "test@example.com";
        String password = "password123";

        when(mockAuthService.signInWithEmailAndPassword(email, password)).thenReturn(Tasks.forResult(null));
        when(mockAuthService.getCurrentUser()).thenReturn(mockFirebaseUser);
        when(mockUserService.getDisplayName(mockFirebaseUser)).thenReturn("Test User");

        loginActivity.loginUser(email, password);

        verify(mockAuthService).signInWithEmailAndPassword(email, password);
    }


    @Test
    public void testLoginUser_InvalidEmail() {
        // Arrange
        loginActivity.etUsername.setText("invalid-email");
        loginActivity.etPassword.setText("password123");

        // Act
        loginActivity.loginUser();

        // Verify signInWithEmailAndPassword is never called due to validation fail
        verify(mockAuthService, never()).signInWithEmailAndPassword(any(), any());
    }

    @Test
    public void testLoginUser_EmptyPassword() {
        // Arrange
        loginActivity.etUsername.setText("test@example.com");
        loginActivity.etPassword.setText("");

        // Act
        loginActivity.loginUser();

        // Verify signInWithEmailAndPassword is never called due to validation fail
        verify(mockAuthService, never()).signInWithEmailAndPassword(any(), any());
    }

    @Test
    public void testLoginUser_FailedLogin() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";

        loginActivity.etUsername.setText(email);
        loginActivity.etPassword.setText(password);

        Exception fakeException = new Exception("wrong-password");
        Task<AuthResult> failedTask = Tasks.forException(fakeException);

        when(mockAuthService.signInWithEmailAndPassword(email, password)).thenReturn(failedTask);

        // Act
        loginActivity.loginUser();

        // Verify signInWithEmailAndPassword called once
        verify(mockAuthService).signInWithEmailAndPassword(email, password);
    }

}
