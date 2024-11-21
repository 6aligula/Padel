package com.example.clubdiversion.ui.login;

import android.content.Context;

import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.entities.LoginRequest;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final UserRepository userRepository;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.userRepository = new UserRepository(context);
    }

    @Override
    public void doLogin(String username, String password) {
        view.showProgress();

        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    boolean isSaved = userRepository.saveUser(response.body());
                    if (isSaved) {
                        view.showLoginSuccess();
                        view.navigateToHome();
                    } else {
                        view.showLoginError("Error al guardar el usuario en la base de datos");
                    }
                } else {
                    view.showLoginError("Credenciales inválidas");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showLoginError("Error: " + t.getMessage());
            }
        });
    }

}

