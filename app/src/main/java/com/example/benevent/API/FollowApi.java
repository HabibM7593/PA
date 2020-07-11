package com.example.benevent.API;

import com.example.benevent.Models.Follow;
import com.example.benevent.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FollowApi {
    @POST("follow")
    Call<Void> followAsso(@Body Follow follow);
    @HTTP(method = "DELETE", path = "unfollow", hasBody = true)
    Call<Void> unfollowAsso(@Body Follow unfollow);
    @GET("follow/{idas}/{idu}")
    Call<List<User>> checkFollow(@Path("idas") int idas,@Path("idu") int idu);
}
