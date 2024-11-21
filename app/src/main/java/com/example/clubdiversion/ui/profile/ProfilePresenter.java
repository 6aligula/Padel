package com.example.clubdiversion.ui.profile;

import android.content.Context;

import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.entities.SocioDB;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.repository.UserRepository;

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
