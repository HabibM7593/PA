package com.example.benevent.API;

import com.example.benevent.Models.Feedback;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

public interface ParticipateApi {
    @PATCH("events")
    Call<Void> sendFeedback(@Body Feedback feedback);
}
