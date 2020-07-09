package com.example.benevent.API;

import com.example.benevent.Models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryApi {
    @GET("categories")
    Call<List<Category>> getCat();
}
