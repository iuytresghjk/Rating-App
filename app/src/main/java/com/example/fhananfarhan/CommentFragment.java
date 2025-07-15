package com.example.fhananfarhan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CommentFragment extends Fragment {

    private static final String ARG_RATING = "rating";
    private float rating;

    public static CommentFragment newInstance(float rating) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putFloat(ARG_RATING, rating);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rating = getArguments().getFloat(ARG_RATING);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        TextView ratingDisplay = view.findViewById(R.id.ratingDisplay);
        EditText commentInput = view.findViewById(R.id.commentInput);
        Button sendButton = view.findViewById(R.id.sendButton);

        ratingDisplay.setText("Rating: " + rating);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        sendButton.setOnClickListener(v -> {
            String comment = commentInput.getText().toString().trim();

            if (TextUtils.isEmpty(comment)) {
                Toast.makeText(getContext(), "Please add a comment", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("comment", comment);
                data.put("rating", rating);
                data.put("email", "user@example.com");
                data.put("timestamp", FieldValue.serverTimestamp());  // 🔥 for sorting

                db.collection("comments")
                        .add(data)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(getContext(), "Comment submitted!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), CommentListActivity.class));
                            requireActivity().finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(getContext(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        return view;
    }
}
