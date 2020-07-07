package com.example.benevent.API;


import com.example.benevent.Models.Participation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;

public interface ParticipateApi {
    @PATCH("participation")
    Call<Void> ConfirmParticipation(@Body Participation participation);
}
