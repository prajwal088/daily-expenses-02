package com.prajwaldarekar.dailyexpenses02.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.prajwaldarekar.dailyexpenses02.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.prajwaldarekar.dailyexpenses02.utils.NetworkUtils;

/**
 * AuthActivity handles Google Sign-In and Guest Login authentication for the Daily Expenses app.
 */
public class AuthActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "DailyExpensesPrefs";
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final int RC_GOOGLE_SIGN_IN = 1001;
    private static final String TAG = "AuthActivity";

    private Button btnGoogle, btnGuest;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private enum SignInStatus {
        REQUEST_FAILED, SIGN_IN_FAILED, AUTH_FAILED, ID_TOKEN_MISSING,
        NETWORK_ERROR, USER_CANCELLED, FIREBASE_AUTH_ERROR, NO_NETWORK_CONNECTION, UNKNOWN_ERROR
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btnGoogle = findViewById(R.id.btn_google);
        btnGuest = findViewById(R.id.btn_guest);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        configureGoogleSignIn();

        isLoading.observe(this, isLoadingState -> {
            progressBar.setVisibility(isLoadingState ? View.VISIBLE : View.GONE);
            btnGoogle.setEnabled(!isLoadingState);
            btnGuest.setEnabled(!isLoadingState);
        });

        btnGoogle.setOnClickListener(view -> startGoogleSignIn());
        btnGuest.setOnClickListener(view -> continueAsGuest());
    }

    private void configureGoogleSignIn() {
        signInRequest = new BeginSignInRequest.Builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.web_client_id))
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .build();
    }

    private void startGoogleSignIn() {
        if (!NetworkUtils.isNetworkConnected(this)) {
            handleSignInError(new Exception("No network connection"), SignInStatus.NO_NETWORK_CONNECTION);
            return;
        }

        isLoading.setValue(true);
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this, result -> {
                    try {
                        startIntentSenderForResult(result.getPendingIntent().getIntentSender(), RC_GOOGLE_SIGN_IN, null, 0, 0, 0);
                    } catch (Exception e) {
                        handleSignInError(e, SignInStatus.REQUEST_FAILED);
                    }
                })
                .addOnFailureListener(this, e -> handleSignInError(e, SignInStatus.REQUEST_FAILED));
    }

    private void continueAsGuest() {
        isLoading.setValue(true);
        btnGuest.postDelayed(() -> {
            isLoading.setValue(false);
            markLoggedIn();
            navigateToHome();
        }, 1000); // Simulate loading for UX
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            if (data != null) {
                handleGoogleSignInResult(data);
            } else {
                handleSignInError(new Exception("Sign-In Intent data is null"), SignInStatus.SIGN_IN_FAILED);
            }
        }
    }

    private void handleGoogleSignInResult(Intent data) {
        try {
            SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
            String idToken = credential.getGoogleIdToken();
            if (idToken != null) {
                firebaseAuthWithGoogle(idToken);
            } else {
                handleSignInError(new Exception("ID token missing"), SignInStatus.ID_TOKEN_MISSING);
            }
        } catch (ApiException e) {
            if (e.getStatusCode() == CommonStatusCodes.NETWORK_ERROR) {
                handleSignInError(e, SignInStatus.NETWORK_ERROR);
            } else if (e.getStatusCode() == CommonStatusCodes.CANCELED) {
                handleSignInError(e, SignInStatus.USER_CANCELLED);
            } else {
                handleSignInError(e, SignInStatus.SIGN_IN_FAILED);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    isLoading.setValue(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Toast.makeText(this, "Welcome " + (user != null ? user.getDisplayName() : ""), Toast.LENGTH_SHORT).show();
                        markLoggedIn();
                        navigateToHome();
                    } else {
                        handleSignInError(task.getException(), SignInStatus.FIREBASE_AUTH_ERROR);
                    }
                })
                .addOnFailureListener(this, e -> handleSignInError(e, SignInStatus.FIREBASE_AUTH_ERROR));
    }

    private void handleSignInError(Exception e, SignInStatus status) {
        Log.e(TAG, "Sign-In Error [" + status.name() + "]:", e);
        isLoading.setValue(false);

        String message;
        switch (status) {
            case REQUEST_FAILED: message = "Failed to initiate sign-in. Please try again."; break;
            case SIGN_IN_FAILED: message = "Google Sign-In failed. Please try again later."; break;
            case AUTH_FAILED: message = "Authentication with Firebase failed."; break;
            case ID_TOKEN_MISSING: message = "Google ID token missing. Please try again."; break;
            case NETWORK_ERROR: message = "Network error. Please check your connection."; break;
            case USER_CANCELLED: message = "Sign-In cancelled by user."; break;
            case FIREBASE_AUTH_ERROR: message = "Firebase Authentication error. Please try again."; break;
            case NO_NETWORK_CONNECTION: message = "No network connection. Please connect and try again."; break;
            default: message = "An unexpected error occurred."; break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void markLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        prefs.edit().putBoolean(KEY_LOGGED_IN, true).apply();
    }

    private void navigateToHome() {
        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
