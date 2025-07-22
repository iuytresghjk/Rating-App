package com.example.fhananfarhan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CommentApi {
    @GET("interns/api/rating.php")
    Call<List<CommentItem>> getComments();

    @POST("interns/api/rating.php")
    Call<ApiResponse> sendReview(@Body ReviewRequest reviewRequest);
}
