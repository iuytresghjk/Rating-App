package com.example.fhananfarhan;

import com.google.firebase.Timestamp;

public class CommentItem {
    private String comment;
    private float rating;
    private String email;
    private Timestamp timestamp;

    public CommentItem() {
        // required by Firebase
    }

    public CommentItem(String comment, float rating, String email) {
        this.comment = comment;
        this.rating = rating;
        this.email = email;
        this.timestamp = null; // Firestore will set it server-side
    }

    public String getComment() {
        return comment;
    }

    public float getRating() {
        return rating;
    }

    public String getEmail() {
        return email;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
