package com.example.benevent.API;

import com.example.benevent.Models.Association;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AssociationApi {
    @GET("association/{idas}")
    Call<List<Association>> getAsso(@Path("idas") int id);

    @GET("associations")
    Call<List<Association>> getAllAssos();
}
