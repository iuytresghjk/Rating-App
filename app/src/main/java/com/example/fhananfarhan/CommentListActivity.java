package com.example.fhananfarhan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addButton;
    CommentAdapter adapter;
    ArrayList<CommentItem> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);

        recyclerView = findViewById(R.id.commentRecyclerView);
        addButton = findViewById(R.id.addCommentBtn);

        commentList = CommentStorage.loadComments(this);
        adapter = new CommentAdapter(commentList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        commentList.clear();
        commentList.addAll(CommentStorage.loadComments(this));
        adapter.notifyDataSetChanged();
    }
}
