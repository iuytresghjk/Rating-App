package com.example.fhananfarhan;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @POST("interns/api/rating.php")
    @Headers("Content-Type: application/json")
    Call<Void> postRating(@Body Map<String, String> body);
}
