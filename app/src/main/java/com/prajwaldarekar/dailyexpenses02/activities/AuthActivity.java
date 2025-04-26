package com.prajwaldarekar.dailyexpenses02.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prajwaldarekar.dailyexpenses02.R;

import java.util.UUID;

public class AuthActivity extends AppCompatActivity {

    private static final int LOADING_DELAY_MS = 1000;

    private Button btnGoogle, btnGuest;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "DailyExpensesPrefs";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_USER_ID = "user_id"; // Key for unique user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Check if user is already logged in
        if (isUserLoggedIn()) {
            navigateToMainActivity();
            return; // Skip the rest of the code if already logged in
        }

        btnGoogle = findViewById(R.id.btn_google);
        btnGuest = findViewById(R.id.btn_guest);
        progressBar = findViewById(R.id.progressBar);

        // Google Sign-in (coming soon)
        btnGoogle.setOnClickListener(v -> {
            Toast.makeText(this, "Google Sign-In coming soon...", Toast.LENGTH_SHORT).show();
        });

        // Guest Sign-in
        btnGuest.setOnClickListener(v -> {
            showLoading();
            // Simulate login delay
            new Thread(() -> {
                try {
                    Thread.sleep(LOADING_DELAY_MS);
                    runOnUiThread(() -> {
                        // Mark user as logged in and generate unique user ID
                        markUserAsLoggedIn();
                        generateUserId(); // Generate and store unique user ID
                        navigateToMainActivity();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        btnGoogle.setEnabled(false);
        btnGuest.setEnabled(false);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnGoogle.setEnabled(true);
        btnGuest.setEnabled(true);
    }

    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    private void markUserAsLoggedIn() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_LOGGED_IN, true);
        editor.apply();
    }

    private void generateUserId() {
        String userId = sharedPreferences.getString(KEY_USER_ID, null);

        if (userId == null) {
            // Generate a unique ID only if not already created
            userId = UUID.randomUUID().toString(); // Generates a random unique ID
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_USER_ID, userId);
            editor.apply();
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}