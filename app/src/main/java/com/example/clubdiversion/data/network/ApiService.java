package com.example.clubdiversion.data.network;

import com.example.clubdiversion.data.entities.LoginRequest;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.entities.RegisterRequest;
import com.example.clubdiversion.data.entities.RegisterResponse;
import com.example.clubdiversion.data.entities.ReservationRequest;
import com.example.clubdiversion.data.entities.ReservationResponse;
import com.example.clubdiversion.data.entities.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    @POST("api/users/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/users/register/")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("api/reservations/add/")
    Call<ReservationResponse> addReservation(
            @Body ReservationRequest reservationRequest,
            @retrofit2.http.Header("Authorization") String token
    );

    @GET("api/users/profile/")
    Call<LoginResponse> getProfile(@retrofit2.http.Header("Authorization") String token);
    @GET("api/reservations/get/")
    Call<List<ReservationResponse>> getReservations(
            @retrofit2.http.Header("Authorization") String token
    );

    @GET("api/users/all/")
    Call<List<UserResponse>> getAllUsers(@retrofit2.http.Header("Authorization") String token);

    @PUT("api/users/update/{id}/")
    Call<UserResponse> updateUser(
            @retrofit2.http.Path("id") int id,
            @retrofit2.http.Header("Authorization") String token,
            @Body UserResponse user);

    @DELETE("api/users/delete/{id}/")
    Call<Void> deleteUser(
            @retrofit2.http.Path("id") int id,
            @retrofit2.http.Header("Authorization") String token);

}

