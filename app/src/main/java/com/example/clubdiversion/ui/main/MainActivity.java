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
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this, this);

        presenter = new MainPresenter(this, this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu; // Guarda la referencia al menú
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        // Llamar a checkUser después de inflar el menú
        presenter.checkUser();
        return true;
    }

    @Override
    public void setAdminMenuVisibility(boolean isVisible) {
        if (menu != null) {
            MenuItem adminItem = menu.findItem(R.id.Admin);
            if (adminItem != null) {
                adminItem.setVisible(isVisible);
                android.util.Log.d("MainActivity", "Botón Admin visibilidad: " + isVisible);
            } else {
                android.util.Log.e("MainActivity", "No se encontró el ítem Admin en el menú.");
            }
        } else {
            android.util.Log.e("MainActivity", "El menú no está inicializado.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Instalaciones) {
            navigateToInstalaciones();
        } else if (id == R.id.Profile) {
            navigateToProfile();
        } else if (id == R.id.Logout) {
            presenter.logout();
        } else if (id == R.id.Salir) {
            closeApp();
        } else if (id == R.id.Admin) { // Nuevo caso para Admin
            navigateToAdmin();
        }
        return true;
    }
    @Override
    public void navigateToAdmin() {
        Intent intent = new Intent(MainActivity.this, com.example.clubdiversion.ui.admin.AdminUserActivity.class);
        startActivity(intent);
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

