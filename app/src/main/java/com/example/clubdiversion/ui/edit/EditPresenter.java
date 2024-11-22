package com.example.clubdiversion.ui.edit;

import com.example.clubdiversion.data.entities.UserResponse;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPresenter implements EditContract.Presenter {
    private final EditContract.View view;
    private final ApiService apiService;
    private final String token;

    public EditPresenter(EditContract.View view, String token) {
        this.view = view;
        this.apiService = RetrofitClient.getApiService();
        this.token = token != null ? "Bearer " + token : "";
    }

    @Override
    public void updateUser(int userId, UserResponse user, String password) {
        if (token.isEmpty()) {
            view.showError("Token no disponible. Inicie sesión nuevamente.");
            return;
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        apiService.updateUser(userId, token, user).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    view.showSuccess("Usuario actualizado correctamente.");
                } else {
                    view.showError("Error al actualizar el usuario.");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                view.showError("Error de red: " + t.getMessage());
            }
        });
    }
}
