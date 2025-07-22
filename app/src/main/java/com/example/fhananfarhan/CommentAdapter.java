package com.example.fhananfarhan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<CommentItem> comments;

    public CommentAdapter(List<CommentItem> comments) {
        this.comments = comments;
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView reviewText, dateText;
        RatingBar ratingBar;

        public CommentViewHolder(View itemView) {
            super(itemView);
            reviewText = itemView.findViewById(R.id.itemCommentText);
            dateText = itemView.findViewById(R.id.itemRatingText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentItem comment = comments.get(position);
        holder.reviewText.setText(comment.getReview());
        holder.dateText.setText(comment.getCreated_at());
        holder.ratingBar.setRating(comment.getRating());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
