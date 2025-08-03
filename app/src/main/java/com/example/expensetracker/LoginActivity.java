package com.example.expensetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetracker.service.AuthService;
import com.example.expensetracker.service.UserService;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    public EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    private AuthService authService;
    private UserService userService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // Initialize service classes (can inject mocks in tests)
            authService = new AuthService();
            userService = new UserService();

            // Check if user is already logged in
            FirebaseUser currentUser = authService.getCurrentUser();
            if (currentUser != null) {
                Log.d(TAG, "User already logged in: " + currentUser.getEmail());
                navigateToMainActivity();
                return;
            }

            setContentView(R.layout.activity_login);
            Log.d(TAG, "Login layout set successfully");

            initViews();
            setupClickListeners();
            handleRegistrationSuccess();

        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage(), e);
            Toast.makeText(this, "Error initializing login: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        try {
            etUsername = findViewById(R.id.et_username);
            etPassword = findViewById(R.id.et_password);
            btnLogin = findViewById(R.id.btn_login);
            tvRegister = findViewById(R.id.tv_register);

            if (etUsername == null || etPassword == null || btnLogin == null || tvRegister == null) {
                throw new RuntimeException("One or more views not found in layout");
            }

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing in...");
            progressDialog.setCancelable(false);

            Log.d(TAG, "Views initialized successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in initViews: " + e.getMessage(), e);
            Toast.makeText(this, "Error initializing views: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupClickListeners() {
        try {
            btnLogin.setOnClickListener(v -> {
                Log.d(TAG, "Login button clicked");
                loginUser();
            });

            tvRegister.setOnClickListener(v -> {
                Log.d(TAG, "Register link clicked");
                try {
                    Intent intent = new Intent(this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to register: " + e.getMessage(), e);
                    Toast.makeText(this, "Error opening registration", Toast.LENGTH_SHORT).show();
                }
            });

            Log.d(TAG, "Click listeners set successfully");
        } catch (Exception e) {
            Log.e(TAG, "Error in setupClickListeners: " + e.getMessage(), e);
        }
    }

    private void handleRegistrationSuccess() {
        try {
            Intent intent = getIntent();
            if (intent != null && intent.getBooleanExtra("registration_success", false)) {
                String userEmail = intent.getStringExtra("user_email");
                if (userEmail != null) {
                    etUsername.setText(userEmail);
                    Toast.makeText(this, "Registration successful! Please login with your credentials.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in handleRegistrationSuccess: " + e.getMessage(), e);
        }
    }

    public void loginUser() {
        try {
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            Log.d(TAG, "Attempting login for email: " + email);

            if (!validateInput(email, password)) {
                return;
            }

            if (progressDialog != null) {
                progressDialog.show();
            }

            authService.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            if (task.isSuccessful()) {
                                Log.d(TAG, "Login successful");
                                FirebaseUser user = authService.getCurrentUser();
                                if (user != null) {
                                    String welcomeName = userService.getDisplayName(user);
                                    Toast.makeText(this, "Welcome back, " + welcomeName + "!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
                                }
                                navigateToMainActivity();
                            } else {
                                Log.e(TAG, "Login failed", task.getException());
                                handleLoginError(task.getException());
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error in login completion: " + e.getMessage(), e);
                            Toast.makeText(this, "Error processing login result", Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, "Error in loginUser: " + e.getMessage(), e);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            Toast.makeText(this, "Error during login: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void handleLoginError(Exception exception) {
        String errorMessage = "Login failed. Please try again.";
        if (exception != null) {
            String firebaseError = exception.getMessage();
            Log.e(TAG, "Login failed: " + firebaseError);

            if (firebaseError != null) {
                if (firebaseError.contains("no user record")) {
                    errorMessage = "No account found with this email. Please register first.";
                } else if (firebaseError.contains("wrong-password") || firebaseError.contains("invalid-credential")) {
                    errorMessage = "Incorrect password. Please try again.";
                } else if (firebaseError.contains("invalid-email")) {
                    errorMessage = "Invalid email format. Please check and try again.";
                } else if (firebaseError.contains("user-disabled")) {
                    errorMessage = "Your account has been disabled. Please contact support.";
                } else if (firebaseError.contains("too-many-requests")) {
                    errorMessage = "Too many failed attempts. Please try again later.";
                } else {
                    errorMessage = "Login failed: " + firebaseError;
                }
            }
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private boolean validateInput(String email, String password) {
        try {
            etUsername.setError(null);
            etPassword.setError(null);

            if (email.isEmpty()) {
                etUsername.setError("Please enter email");
                etUsername.requestFocus();
                return false;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etUsername.setError("Enter a valid email");
                etUsername.requestFocus();
                return false;
            }

            if (password.isEmpty()) {
                etPassword.setError("Please enter password");
                etPassword.requestFocus();
                return false;
            }

            if (password.length() < 6) {
                etPassword.setError("Password must be at least 6 characters");
                etPassword.requestFocus();
                return false;
            }

            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error in validateInput: " + e.getMessage(), e);
            return false;
        }
    }

    private void navigateToMainActivity() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error navigating to MainActivity: " + e.getMessage(), e);
            Toast.makeText(this, "Error opening main screen", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            FirebaseUser currentUser = authService.getCurrentUser();
            if (currentUser != null) {
                Log.d(TAG, "User already signed in on start");
                navigateToMainActivity();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onStart: " + e.getMessage(), e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (etPassword != null) {
                etPassword.setText("");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onResume: " + e.getMessage(), e);
        }
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error in onDestroy: " + e.getMessage(), e);
        }
    }
    // New method
    public void loginUser(String email, String password) {
        if (!validateInput(email, password)) return;

        progressDialog.show();

        authService.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        FirebaseUser user = authService.getCurrentUser();
                        String name = userService.getDisplayName(user);
                        Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_SHORT).show();
                        navigateToMainActivity();
                    } else {
                        handleLoginError(task.getException());
                    }
                });
    }


    // For test injection
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
