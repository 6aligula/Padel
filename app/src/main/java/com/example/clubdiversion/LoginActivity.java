package com.example.clubdiversion;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubdiversion.Utilidades.ApiService;
import com.example.clubdiversion.Utilidades.RetrofitClient;
import com.example.clubdiversion.Entidades.LoginRequest;
import com.example.clubdiversion.Entidades.LoginResponse;
import com.example.clubdiversion.Utilidades.Utilidades;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsername, editPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Botón de inicio de sesión
        btnLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                login(username, password);
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para redirigir al registro
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login(String username, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    saveUserToDatabase(loginResponse);

                    // Redirigir al Instalaciones
                    Intent intent = new Intent(LoginActivity.this, Instalaciones.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToDatabase(LoginResponse loginResponse) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "club_diversion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, loginResponse.getId());
        values.put(Utilidades.CAMPO_USERNAME, loginResponse.getUsername());
        values.put(Utilidades.CAMPO_NAME, loginResponse.getName());
        values.put(Utilidades.CAMPO_DIRECCION, loginResponse.getDireccion());
        values.put(Utilidades.CAMPO_TELEFONO, loginResponse.getTelefono());
        values.put(Utilidades.CAMPO_IS_ADMIN, loginResponse.isAdmin() ? 1 : 0);
        values.put(Utilidades.CAMPO_TOKEN, loginResponse.getToken());

        // Usando el metodo genérico de Utilidades
        long result = Utilidades.Insertar_En_Tabla(Utilidades.TABLA_USERS, values, db);
        if (result == -1) {
            Toast.makeText(this, "Error al guardar el usuario en la base de datos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario guardado exitosamente", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

}
