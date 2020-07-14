package com.example.benevent.API;

import com.example.benevent.Models.Login;
import com.example.benevent.Models.Signup;
import com.example.benevent.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @POST("signin/user")
    Call<List<User>> logUser(@Body Login log);

    @POST("signup/user")
    Call<Void> signUser(@Body Signup sign);

    @GET("user/{id}")
    Call<List<User>> getUser(@Path("id") int id);

    @GET("users")
    Call<List<User>> getUsers();

    @PATCH("user/{idu}")
    Call<Void> updateUser(@Path("idu") int id,@Body User user);

    @DELETE("user/{idu}")
    Call<Void> deleteUser(@Path("idu") int id);
}
