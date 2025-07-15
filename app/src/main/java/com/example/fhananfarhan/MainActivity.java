package com.example.fhananfarhan;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView ratingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = findViewById(R.id.ratingBar);
        ratingText = findViewById(R.id.ratingText);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) { // only handle user click
                    int intRating = (int) rating;
                    ratingText.setText("You gave " + intRating + " star" + (intRating > 1 ? "s" : ""));

                    CommentFragment commentFragment = CommentFragment.newInstance(rating);
                    getSupportFragmentManager().beginTransaction()
                            .replace(android.R.id.content, commentFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });


    }
}
