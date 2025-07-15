package com.example.fhananfarhan;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.ratingText);

        ratingBar.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
            if (fromUser) {
                ratingText.setText("You gave " + (int) rating + " star" + ((int) rating > 1 ? "s" : ""));
                // Open comment fragment
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, CommentFragment.newInstance(rating))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
