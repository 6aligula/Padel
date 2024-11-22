package com.example.clubdiversion.ui.profile;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;

import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.entities.ReservationResponse;
import com.example.clubdiversion.data.entities.SocioDB;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.repository.UserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {
    private final ProfileContract.View view;
    private final UserRepository userRepository;

    public ProfilePresenter(ProfileContract.View view, Context context) {
        this.view = view;
        this.userRepository = new UserRepository(context);
    }

    @Override
    public void refreshUserProfile() {
        view.showLoading();

        String token = userRepository.getToken();
        if (token == null) {
            view.showError("El usuario no ha iniciado sesión");
            view.hideLoading();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getProfile("Bearer " + token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse userProfile = response.body();
                    userProfile.setToken(token); // Asigna el token al perfil obtenido

                    boolean updated = userRepository.saveUser(userProfile);
                    if (updated) {
                        view.showProfile(userProfile.getName(), userProfile.getUsername(), userProfile.getTelefono(), userProfile.getDireccion());
                    } else {
                        view.showError("No se pudo actualizar el perfil localmente.");
                    }
                } else {
                    view.showError("Error al obtener el perfil del usuario.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideLoading();
                view.showError("Error de red: " + t.getMessage());
            }
        });
    }
    @Override
    public void loadReservations() {
        view.showLoading();

        String token = userRepository.getToken();
        Log.d(TAG, "Token obtenido: " + token); // Log del token
        if (token == null) {
            view.showError("El usuario no ha iniciado sesión");
            view.hideLoading();
            return;
        }
        // Agregar el prefijo "Bearer"
        token = "Bearer " + token;

        ApiService apiService = RetrofitClient.getApiService();
        Log.d(TAG, "Realizando petición a /api/reservations/get/"); // Log antes de enviar la petición
        apiService.getReservations(token).enqueue(new Callback<List<ReservationResponse>>() {
            @Override
            public void onResponse(Call<List<ReservationResponse>> call, Response<List<ReservationResponse>> response) {
                view.hideLoading();
                Log.d(TAG, "Respuesta recibida: " + response.toString()); // Log completo de la respuesta
                if (response.isSuccessful() && response.body() != null) {
                    List<ReservationResponse> reservations = response.body();
                    Log.d(TAG, "Reservas recibidas: " + reservations.toString()); // Log de las reservas
                    view.showReservations(reservations);
                } else {
                    Log.e(TAG, "Error al obtener reservas: " + response.errorBody()); // Log del error
                    view.showError("Error al obtener las reservas");
                }
            }

            @Override
            public void onFailure(Call<List<ReservationResponse>> call, Throwable t) {
                view.hideLoading();
                Log.e(TAG, "Error en la petición: " + t.getMessage()); // Log del fallo
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void loadProfile() {
        view.showLoading();

        // Primero intentamos obtener los datos de la base de datos local
        LoginResponse localUser = mapSocioToLoginResponse(userRepository.getCurrentUser());
        if (localUser != null) {
            view.showProfile(localUser.getName(), localUser.getEmail(), localUser.getTelefono(), localUser.getDireccion());
            view.hideLoading();
            return;
        }

        // Si no hay datos locales, hacemos una llamada al backend
        String token = userRepository.getToken();
        if (token == null) {
            view.showError("El usuario no ha iniciado sesión");
            view.hideLoading();
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getProfile(token).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideLoading();

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse userProfile = response.body();
                    view.showProfile(userProfile.getName(), userProfile.getEmail(), userProfile.getTelefono(), userProfile.getDireccion());
                } else {
                    view.showError("Error al obtener el perfil");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideLoading();
                view.showError("Error: " + t.getMessage());
            }
        });
    }

    private LoginResponse mapSocioToLoginResponse(SocioDB socio) {
        if (socio == null) return null;
        LoginResponse response = new LoginResponse();
        response.setName(socio.getNombre());
        response.setEmail(socio.getCorreo());
        response.setTelefono(socio.getTlf());
        response.setDireccion(socio.getDireccion());
        return response;
    }
}
