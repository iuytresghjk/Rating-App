package com.example.fhananfarhan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommentAdapter adapter;
    private List<CommentItem> commentList = new ArrayList<>();

    DrawerLayout drawerLayout;
    BottomNavigationView bottomNav;
    ActionBarDrawerToggle toggle;
    TextView navUsername, navEmail;
    Button navLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard); // Reusing your drawer layout

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Drawer setup
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // SharedPreferences for user info
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = prefs.getString("saved_name", "User");
        String email = prefs.getString("saved_email", "email@example.com");

        navUsername = findViewById(R.id.navUsername);
        navEmail = findViewById(R.id.navEmail);
        navLogout = findViewById(R.id.navLogout);

        navUsername.setText("Welcome, " + username);
        navEmail.setText(email);

        navLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // Bottom Navigation
        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Toast.makeText(this, "Already on Home", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_comments) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            }
            return false;
        });

        // RecyclerView setup (inside the content container)
        recyclerView = findViewById(R.id.commentRecyclerView);
        adapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fetchCommentsFromApi();
    }

    private void fetchCommentsFromApi() {
        CommentApi apiService = ApiClient.getRetrofitInstance().create(CommentApi.class);
        Call<List<CommentItem>> call = apiService.getComments();

        call.enqueue(new Callback<List<CommentItem>>() {
            @Override
            public void onResponse(Call<List<CommentItem>> call, Response<List<CommentItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList.clear();
                    commentList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(CommentListActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommentItem>> call, Throwable t) {
                Toast.makeText(CommentListActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
