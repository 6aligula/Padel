package com.example.clubdiversion.ui.admin;

import com.example.clubdiversion.data.entities.UserResponse;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.repository.UserRepository;
import com.example.clubdiversion.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUserPresenter implements AdminUserContract.Presenter {
    private final AdminUserContract.View view;
    private final ApiService apiService;
    private final String token;

    public AdminUserPresenter(AdminUserContract.View view, String token) {
        this.view = view;
        this.apiService = RetrofitClient.getApiService();
        this.token = token != null ? "Bearer " + token : ""; // Aseguro que el token tenga valor
    }

    @Override
    public void getAllUsers() {
        if (token.isEmpty()) {
            view.showError("Token no disponible, el usuario debe iniciar sesión.");
            return;
        }
        apiService.getAllUsers(token).enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showUsers(response.body());
                } else {
                    view.showError("Error al obtener usuarios");
                }
            }

            @Override
            public void onFailure(Call<List<UserResponse>> call, Throwable t) {
                view.showError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void updateUser(int userId, UserResponse user) {
        apiService.updateUser(userId, token, user).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    view.showSuccess("Usuario actualizado");
                } else {
                    view.showError("Error al actualizar usuario");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void deleteUser(int userId) {
        apiService.deleteUser(userId, token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    view.showSuccess("Usuario eliminado");
                } else {
                    view.showError("Error al eliminar usuario");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                view.showError("Error de red: " + t.getMessage());
            }
        });
    }
}
