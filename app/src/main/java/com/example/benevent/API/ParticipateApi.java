package com.example.benevent.API;


import com.example.benevent.Models.Follow;
import com.example.benevent.Models.Participation;
import com.example.benevent.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParticipateApi {
    @POST("participate")
    Call<Void> Participer(@Body Participation participation);

    @PATCH("participate/status")
    Call<Void> UpdateParticipation(@Body Participation participation);

    @PATCH("participate/refuse")
    Call<Void> RefuseParticipation(@Body Participation participation);

    @GET("participate/{idev}/{idu}")
    Call<List<Participation>> checkParticipation(@Path("idev") int idev, @Path("idu") int idu);

}
