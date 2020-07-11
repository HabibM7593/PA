package com.example.benevent.API;

import com.example.benevent.Models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventApi {
    @GET("events/{idu}")
    Call<List<Event>> getEvents(@Path("idu") int idu);
}
