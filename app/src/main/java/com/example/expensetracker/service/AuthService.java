package com.example.expensetracker.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthService {

    private FirebaseAuth mAuth;

    public AuthService() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    // For testing: allow injecting mock FirebaseAuth
    public AuthService(FirebaseAuth firebaseAuth) {
        this.mAuth = firebaseAuth;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public Task<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password);
    }
}
