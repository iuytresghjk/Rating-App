package com.example.fhananfarhan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 1500; // 1.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // User is logged in → Go to home
                startActivity(new Intent(SplashActivity.this, CommentListActivity.class));
            } else {
                // User is not logged in → Go to login screen
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

            finish();

        }, SPLASH_TIME);
    }
}
