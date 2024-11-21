package com.example.clubdiversion.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubdiversion.R;
import com.example.clubdiversion.ui.instalaciones.InstalacionesActivity;
import com.example.clubdiversion.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText editUsername, editPassword;
    private Button btnLogin, btnRegister;
    private ProgressBar progressBar;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);

        presenter = new LoginPresenter(this, this);

        btnLogin.setOnClickListener(v -> {
            String username = editUsername.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                presenter.doLogin(username, password);
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(v -> navigateToRegister());
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
    public void showLoginSuccess() {
        Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, InstalacionesActivity.class);
        startActivity(intent);
        finish();
    }
}