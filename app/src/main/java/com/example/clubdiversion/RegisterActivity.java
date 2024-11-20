package com.example.clubdiversion;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clubdiversion.Entidades.RegisterRequest;
import com.example.clubdiversion.Entidades.RegisterResponse;
import com.example.clubdiversion.Utilidades.ApiService;
import com.example.clubdiversion.Utilidades.RetrofitClient;
import com.example.clubdiversion.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText editNIP, editNombre, editDireccion, editTelefono, editPass, editRePass;
    private Button btnAceptar, btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio);

        // Asociar elementos del layout
        editNIP = findViewById(R.id.editNIPSocio);
        editNombre = findViewById(R.id.editNombreSocio);
        editDireccion = findViewById(R.id.editDireccionSocio);
        editTelefono = findViewById(R.id.editTelefonoSocio);
        editPass = findViewById(R.id.editPassSocio);
        editRePass = findViewById(R.id.editRePassSocio);
        btnAceptar = findViewById(R.id.btnAceptarSocio);

        // Configurar botón de registro
        btnAceptar.setOnClickListener(v -> {
            String nip = editNIP.getText().toString().trim();
            String nombre = editNombre.getText().toString().trim();
            String direccion = editDireccion.getText().toString().trim();
            String telefono = editTelefono.getText().toString().trim();
            String password = editPass.getText().toString().trim();
            String confirmPassword = editRePass.getText().toString().trim();

            // Validar campos
            if (validateFields(nip, nombre, direccion, telefono, password, confirmPassword)) {
                registerUser(nip, nombre, direccion, telefono, password);
            }
        });
    }

    private boolean validateFields(String nip, String nombre, String direccion, String telefono, String password, String confirmPassword) {
        if (nip.isEmpty() || nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registerUser(String nip, String nombre, String direccion, String telefono, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        RegisterRequest request = new RegisterRequest(nip, nombre, direccion, telefono, password);

        apiService.register(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Registro exitoso. Bienvenido, " + response.body().getUsername(), Toast.LENGTH_SHORT).show();

                    // Redirigir al LoginActivity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RegisterError", t.getMessage());
            }
        });
    }
}
