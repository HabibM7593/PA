package com.example.benevent.API;

import com.example.benevent.Models.Participation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ParticipateApi {
    @POST("participate")
    Call<Void> Participer(@Body Participation participation);

    @PATCH("participate/status")
    Call<Void> UpdateParticipation(@Body Participation participation);

    @DELETE("participate/refuse/{idevent}/{iduser}")
    Call<Void> RefuseParticipation(@Path("idevent") int idevent, @Path("iduser") int iduser);

    @GET("participate/{idevent}/{iduser}")
    Call<List<Participation>> checkParticipation(@Path("idevent") int idevent, @Path("iduser") int iduser);

    @GET("participate/{idevent}")
    Call<List<Participation>> checkNumberParticipation(@Path("idevent") int idevent);

}
