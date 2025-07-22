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

public class SignupActivity extends AppCompatActivity {

    EditText nameEdit, emailEdit, passwordEdit;
    Button signupBtn, loginRedirect;
    String signupUrl = "https://hmftj.com/interns/UApi/singupp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEdit = findViewById(R.id.editTextName);
        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        signupBtn = findViewById(R.id.buttonSignup);
        loginRedirect = findViewById(R.id.buttonToLogin);

        signupBtn.setOnClickListener(v -> registerUser());
        loginRedirect.setOnClickListener(v ->
                startActivity(new Intent(SignupActivity.this, LoginActivity.class))
        );
    }


    private void registerUser() {
        String name = nameEdit.getText().toString().trim();
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, signupUrl,
                response -> {
                    Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();

                    // 🧠 Save name & email locally
                    SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("saved_name", name);
                    editor.putString("saved_email", email);
                    editor.apply();

                    // 🔁 Go to Login screen
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                },
                error -> Toast.makeText(this, "Signup Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("name", name);
                param.put("email", email);
                param.put("password", password);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

}
