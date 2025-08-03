package com.example.expensetracker.service;

import com.google.firebase.auth.FirebaseUser;

public class UserService {

    public String getDisplayName(FirebaseUser user) {
        if (user == null) return null;
        return user.getDisplayName() != null ? user.getDisplayName() : user.getEmail();
    }
}
