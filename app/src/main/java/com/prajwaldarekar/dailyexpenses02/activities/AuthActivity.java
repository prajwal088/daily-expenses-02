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

    private Button btnGoogle, btnGuest;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnGoogle = findViewById(R.id.btn_google);
        btnGuest = findViewById(R.id.btn_guest);
        progressBar = findViewById(R.id.progressBar);

//        Google Sign-in
        btnGoogle.setOnClickListener(v -> {
            showLoading();
            Toast.makeText(this, "Google Sign-In coming soon...", Toast.LENGTH_SHORT).show();
            // Delay before hiding loading (to simulate action)
            new Handler(Looper.getMainLooper()).postDelayed(this::hideLoading, 1000);
        });

//          Guest Sign-in
        btnGuest.setOnClickListener(v -> {
            showLoading();
            // Delay for a smoother UX
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                Intent intent = new Intent(AuthActivity.this,   MainActivity.class);
                startActivity(intent);
                finish();
            }, 1000);  // 1 second delay
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
