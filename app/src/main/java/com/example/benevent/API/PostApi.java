package com.example.benevent.API;

import com.example.benevent.Models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostApi {
    @GET("posts/{idu}")
    Call<List<Post>> getPosts(@Path("idu") int id);
}
