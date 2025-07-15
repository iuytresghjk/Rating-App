package com.example.fhananfarhan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView ratingView = findViewById(R.id.ratingSummary);
        TextView commentView = findViewById(R.id.commentSummary);

        float rating = getIntent().getFloatExtra("rating", 0);
        String comment = getIntent().getStringExtra("comment");

        ratingView.setText("You gave " + rating + " star" + (rating > 1 ? "s" : ""));
        commentView.setText("Your comment: " + comment);
    }
}
