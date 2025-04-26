package com.prajwaldarekar.dailyexpenses02.activities;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prajwaldarekar.dailyexpenses02.R;

import java.util.UUID;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds
    private static final String PREFS_NAME = "UserPrefs";
    private static final String USER_ID_KEY = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Make sure your layout includes an image.

        try {
            // Check if user ID already exists in SharedPreferences
            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String userId = preferences.getString(USER_ID_KEY, null);

            if (userId == null) {
                // If no user ID exists, generate a new one and save it
                userId = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(USER_ID_KEY, userId);
                editor.apply(); // Commit changes asynchronously
            }
        } catch (Exception e) {
            Log.e("SplashActivity", "Error handling SharedPreferences", e);
            // Inform the user that something went wrong
            Toast.makeText(this, "An error occurred. Please restart the app.", Toast.LENGTH_LONG).show();

            // Close the app
            finish();
            return;
        }

        // Delay and go to AuthActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
            startActivity(intent);
            finish(); // Prevent returning to splash screen
        }, SPLASH_DELAY);
    }
}
