package com.example.clubdiversion.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.clubdiversion.R;
import com.example.clubdiversion.ui.instalaciones.InstalacionesActivity;
import com.example.clubdiversion.ui.login.LoginActivity;
import com.example.clubdiversion.ui.profile.ProfileActivity;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this, this);

        presenter.checkUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Instalaciones) {
            navigateToInstalaciones();
        } else if (id == R.id.Profile) { // Nuevo caso para Perfil
            navigateToProfile();
        } else if (id == R.id.Logout) {
            presenter.logout();
        } else if (id == R.id.Salir) {
            closeApp();
        }
        return true;
    }

    @Override
    public void navigateToProfile() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showWelcomeMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToInstalaciones() {
        Intent intent = new Intent(MainActivity.this, InstalacionesActivity.class);
        startActivity(intent);
    }

    @Override
    public void closeApp() {
        finish();
    }
}

