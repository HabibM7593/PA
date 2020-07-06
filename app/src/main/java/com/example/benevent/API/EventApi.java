package com.example.benevent.API;

import com.example.benevent.Models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EventApi {
    @GET("events")
    Call<List<Event>> getEvents();
}
