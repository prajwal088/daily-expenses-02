package com.prajwaldarekar.dailyexpenses02.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prajwaldarekar.dailyexpenses02.R;

public class AuthActivity extends AppCompatActivity {

    private static final int LOADING_DELAY_MS = 1000;

    private Button btnGoogle, btnGuest;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnGoogle = findViewById(R.id.btn_google);
        btnGuest = findViewById(R.id.btn_guest);
        progressBar = findViewById(R.id.progressBar);

        // Google Sign-in (coming soon)
        btnGoogle.setOnClickListener(v -> {
            showLoading();
            Toast.makeText(this, "Google Sign-In coming soon...", Toast.LENGTH_SHORT).show();

            // TODO: Integrate Google Sign-In logic here
            new Handler(Looper.getMainLooper()).postDelayed(this::hideLoading, LOADING_DELAY_MS);
        });

        // Guest Sign-in
        btnGuest.setOnClickListener(v -> {
            showLoading();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, LOADING_DELAY_MS);
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
}