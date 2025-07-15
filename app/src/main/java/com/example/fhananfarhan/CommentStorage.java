package com.example.fhananfarhan;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommentStorage {

    private static final String PREF_NAME = "comment_pref";
    private static final String KEY_COMMENTS = "comments";

    public static void saveComment(Context context, CommentItem item) {


        ArrayList<CommentItem> list = loadComments(context);
        list.add(item);

        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_COMMENTS, new Gson().toJson(list));
        editor.apply();
    }

    public static ArrayList<CommentItem> loadComments(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_COMMENTS, null);
        if (json == null) return new ArrayList<>();

        Type type = new TypeToken<ArrayList<CommentItem>>() {}.getType();
        return new Gson().fromJson(json, type);
    }
}
