package com.example.fhananfarhan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.widget.Toolbar;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNav;
    ActionBarDrawerToggle toggle;

    TextView navUsername, navEmail;
    Button navLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // 🔧 Setup toolbar & drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 🎯 Get user data
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = prefs.getString("saved_name", "User");
        String email = prefs.getString("saved_email", "email@example.com");

        navUsername = findViewById(R.id.navUsername);
        navEmail = findViewById(R.id.navEmail);
        navLogout = findViewById(R.id.navLogout);

        navUsername.setText("Welcome, " + username);
        navEmail.setText(email);

        navLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isLoggedIn", false); // 🔐 explicitly set false
            editor.remove("saved_name");
            editor.remove("saved_email");
            editor.apply();

            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


        // ✅ Fix: Initialize bottomNav
        bottomNav = findViewById(R.id.bottomNavigationView);

        // 🧭 Bottom Navigation
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // ✅ Open CommentListActivity as Home
                Intent intent = new Intent(this, CommentListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 🔄 Remove previous instances
                startActivity(intent);
                finish(); // optional, to remove DashboardActivity from backstack
                return true;
            } else if (itemId == R.id.nav_comments) {
                // ✅ Open MainActivity as Add Comment
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish(); // optional
                return true;
            }
            return false;
        });



    }
}
