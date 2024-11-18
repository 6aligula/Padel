package com.example.clubdiversion.Utilidades;

import com.example.clubdiversion.Entidades.LoginRequest;
import com.example.clubdiversion.Entidades.LoginResponse;
import com.example.clubdiversion.Entidades.RegisterRequest;
import com.example.clubdiversion.Entidades.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/users/login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/users/register/")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
}
