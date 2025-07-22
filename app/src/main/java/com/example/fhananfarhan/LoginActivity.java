package com.example.fhananfarhan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailEdit, passwordEdit;
    Button loginBtn, signupRedirect;
    String loginUrl = "https://hmftj.com/interns/UApi/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🔒 Check if already logged in
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            String savedEmail = prefs.getString("saved_email", "User");
            String savedName = prefs.getString("saved_name", "User");

            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            intent.putExtra("username", savedName);
            intent.putExtra("email", savedEmail);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.buttonLogin);
        signupRedirect = findViewById(R.id.buttonToSignup);

        loginBtn.setOnClickListener(v -> loginUser());
        signupRedirect.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class))
        );
    }

    private void loginUser() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                response -> {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // Simulated username, replace if your API returns it
                    String name = "User";

                    // 🔐 Save login session
                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("saved_email", email);
                    editor.putString("saved_name", name);
                    editor.apply();

                    // ➡️ Go to Dashboard
                    Intent intent = new Intent(LoginActivity.this, CommentListActivity.class);
                    intent.putExtra("username", name);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                },
                error -> Toast.makeText(this, "Login Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("email", email);
                param.put("password", password);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
