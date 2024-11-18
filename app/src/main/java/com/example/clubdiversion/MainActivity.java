package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Inicio();
    }

    public void Inicio() {
        // Verificar si el usuario está logueado
        SharedPreferences preferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
        String token = preferences.getString("token", null); // Recuperar el token de sesión

        if (token == null || token.isEmpty()) {
            // Si no hay token, redirigir al LoginActivity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish(); // Finalizar MainActivity para que no se quede en el stack
        } else {
            // Si hay token, continuar en MainActivity
            Toast.makeText(this, "Usuario autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicio, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId(); // Obtén el ID del elemento seleccionado
        Intent intent;

        if (id == R.id.Instalaciones) {
            // Redirige a la actividad Instalaciones
            intent = new Intent(this, Instalaciones.class);
            startActivity(intent);
        } else if (id == R.id.Logout) {
            // Cierra sesión y redirige al LoginActivity
            logout();
        } else if (id == R.id.Salir) {
            // Cierra la aplicación
            finish();
        }
        return true;
    }

    // Método para cerrar sesión
    private void logout() {
        // Eliminar el token de SharedPreferences
        SharedPreferences preferences = getSharedPreferences("appPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("token"); // Eliminar el token almacenado
        editor.apply();

        // Redirigir al LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finalizar MainActivity
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(), "Regreso", Toast.LENGTH_SHORT).show();
        Log.e("Salida","Regreso");
        Inicio();
    }

}