package com.example.fhananfarhan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<CommentItem> comments;

    public CommentAdapter(List<CommentItem> comments) {
        this.comments = comments;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView commentText, ratingText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.itemCommentText);
            ratingText = itemView.findViewById(R.id.itemRatingText);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentItem item = comments.get(position);
        holder.ratingText.setText("⭐ " + item.rating);
        holder.commentText.setText(item.comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
