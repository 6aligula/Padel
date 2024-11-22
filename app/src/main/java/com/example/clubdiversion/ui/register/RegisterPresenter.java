package com.example.clubdiversion.ui.register;

import android.content.Context;

import com.example.clubdiversion.data.entities.RegisterRequest;
import com.example.clubdiversion.data.entities.RegisterResponse;
import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;

    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void doRegister(String nip, String nombre, String direccion, String telefono, String password, String confirmPassword) {
        if (validateFields(nip, nombre, direccion, telefono, password, confirmPassword)) {
            view.showProgress();

            ApiService apiService = RetrofitClient.getApiService();
            RegisterRequest request = new RegisterRequest(nip, nombre, direccion, telefono, password);

            apiService.register(request).enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    view.hideProgress();
                    if (response.isSuccessful() && response.body() != null) {
                        // Verificar si hay un usuario en SQLite
                        UserRepository userRepository = new UserRepository((Context) view);
                        boolean isAdminLoggedIn = userRepository.isCurrentUserAdmin();
                        if (isAdminLoggedIn) {
                            // Si un administrador está logueado, no guardamos el nuevo usuario en SQLite
                            view.showRegisterSuccess("Usuario registrado exitosamente por el administrador");
                            view.navigateToAdmin(); // Regresar al panel de administración
                        } else {
                            view.showRegisterSuccess("Registro exitoso. Por favor, inicia sesión");
                            view.navigateToLogin(); // Redirigir al login
                        }
                    } else {
                        view.showRegisterError("Error en el registro");
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    view.hideProgress();
                    view.showRegisterError("Error: " + t.getMessage());
                }
            });
        }
    }

    private boolean validateFields(String nip, String nombre, String direccion, String telefono, String password, String confirmPassword) {
        if (nip.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showRegisterError("Por favor, completa todos los campos");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            view.showRegisterError("Las contraseñas no coinciden");
            return false;
        }

        return true;
    }
}