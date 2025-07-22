package com.example.fhananfarhan;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView ratingText;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.ratingText);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CommentListActivity.class);
            startActivity(intent);
            finish(); // Optional: agar current screen close karni hai
        });

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                ratingText.setText("You gave " + (int) rating + " star" + ((int) rating > 1 ? "s" : ""));
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, CommentFragment.newInstance(rating))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
