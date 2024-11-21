package com.example.clubdiversion.data.network;

import com.example.clubdiversion.data.entities.LoginRequest;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.entities.RegisterRequest;
import com.example.clubdiversion.data.entities.RegisterResponse;
import com.example.clubdiversion.data.entities.ReservationRequest;
import com.example.clubdiversion.data.entities.ReservationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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

    @POST("api/users/profile/")
    Call<LoginResponse> getProfile(@retrofit2.http.Header("Authorization") String token);

}

