package com.example.clubdiversion.ui.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.clubdiversion.data.network.ApiService;
import com.example.clubdiversion.data.network.RetrofitClient;
import com.example.clubdiversion.data.entities.LoginRequest;
import com.example.clubdiversion.data.entities.LoginResponse;
import com.example.clubdiversion.data.network.Utilidades;
import com.example.clubdiversion.data.database.ConexionSQLiteHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final Context context;

    public LoginPresenter(LoginContract.View view, Context context) {
        this.view = view;
        this.context = context;
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

                if (response.isSuccessful()) {
                    saveUserToDatabase(response.body());
                    view.showLoginSuccess();
                    view.navigateToHome();
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

    private void saveUserToDatabase(LoginResponse loginResponse) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, "club_diversion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, loginResponse.getId());
        values.put(Utilidades.CAMPO_USERNAME, loginResponse.getUsername());
        values.put(Utilidades.CAMPO_NAME, loginResponse.getName());
        values.put(Utilidades.CAMPO_DIRECCION, loginResponse.getDireccion());
        values.put(Utilidades.CAMPO_TELEFONO, loginResponse.getTelefono());
        values.put(Utilidades.CAMPO_IS_ADMIN, loginResponse.isAdmin() ? 1 : 0);
        values.put(Utilidades.CAMPO_TOKEN, loginResponse.getToken());

        long result = Utilidades.Insertar_En_Tabla(Utilidades.TABLA_USERS, values, db);
        db.close();

        if (result == -1) {
            view.showLoginError("Error al guardar el usuario en la base de datos");
        }
    }
}

