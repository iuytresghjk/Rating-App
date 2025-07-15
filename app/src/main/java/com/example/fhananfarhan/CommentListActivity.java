package com.example.fhananfarhan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class CommentListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button addButton;
    private CommentAdapter adapter;
    private final ArrayList<CommentItem> commentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        recyclerView = findViewById(R.id.commentRecyclerView);
        addButton = findViewById(R.id.addCommentBtn);

        adapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        loadCommentsFromFirestore();
    }

    private void loadCommentsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("comments")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    commentList.clear();

                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        for (DocumentSnapshot doc : querySnapshot) {
                            CommentItem item = doc.toObject(CommentItem.class);
                            if (item != null && item.getTimestamp() != null) { // ✅ avoid duplicates & nulls
                                commentList.add(item);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
