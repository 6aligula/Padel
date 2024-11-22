package com.example.clubdiversion.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clubdiversion.R;
import com.example.clubdiversion.ui.admin.AdminUserActivity;
import com.example.clubdiversion.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    private EditText editNIP, editNombre, editDireccion, editTelefono, editPass, editRePass;
    private Button btnAceptar;
    private ProgressBar progressBar;
    private RegisterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio);

        editNIP = findViewById(R.id.editNIPSocio);
        editNombre = findViewById(R.id.editNombreSocio);
        editDireccion = findViewById(R.id.editDireccionSocio);
        editTelefono = findViewById(R.id.editTelefonoSocio);
        editPass = findViewById(R.id.editPassSocio);
        editRePass = findViewById(R.id.editRePassSocio);
        btnAceptar = findViewById(R.id.btnAceptarSocio);
        progressBar = findViewById(R.id.progressBar);

        presenter = new RegisterPresenter(this);

        btnAceptar.setOnClickListener(v -> {
            String nip = editNIP.getText().toString().trim();
            String nombre = editNombre.getText().toString().trim();
            String direccion = editDireccion.getText().toString().trim();
            String telefono = editTelefono.getText().toString().trim();
            String password = editPass.getText().toString().trim();
            String confirmPassword = editRePass.getText().toString().trim();

            presenter.doRegister(nip, nombre, direccion, telefono, password, confirmPassword);
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRegisterSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void navigateToAdmin() {
        Intent intent = new Intent(RegisterActivity.this, AdminUserActivity.class);
        startActivity(intent);
        finish();
    }

}