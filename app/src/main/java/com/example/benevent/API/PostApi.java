package com.example.benevent.API;

import com.example.benevent.Models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostApi {
    @GET("posts/{iduser}")
    Call<List<Post>> getPosts(@Path("iduser") int id);
    @POST("post/user")
    Call<Void> sendPost(@Body Post post);
}
