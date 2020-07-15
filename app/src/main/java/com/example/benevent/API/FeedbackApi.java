package com.example.benevent.API;
import com.example.benevent.Models.Feedback;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FeedbackApi {
    @POST("feedback/bug")
    Call<Void> sendFeedbackBug(@Body Feedback feedback);
    @POST("feedback/rating")
    Call<Void> sendFeedbackEval(@Body Feedback feedback);
}
