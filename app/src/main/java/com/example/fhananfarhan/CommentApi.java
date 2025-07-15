package com.example.fhananfarhan;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CommentApi {
    @GET("comments")
    Call<List<CommentItem>> getComments();

    @POST("comments")
    Call<CommentItem> postComment(@Body CommentItem comment);
}
