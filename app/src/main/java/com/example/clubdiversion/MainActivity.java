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

import com.example.clubdiversion.Entidades.SocioDB;
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
        // Verificar si el usuario está logueado y su rol
        SocioDB currentUser = getUserFromDatabase();

        if (currentUser == null || currentUser.getId() == null) {
            // Si no hay usuario logueado, redirigir al LoginActivity
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish(); // Finalizar MainActivity para que no se quede en el stack
        } else {
            // Si hay usuario, verificar si es administrador
            if (currentUser.getSocioAdmin()) {
                Toast.makeText(this, "Bienvenido Admin: " + currentUser.getNombre(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Bienvenido Usuario: " + currentUser.getNombre(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private SocioDB getUserFromDatabase() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "club_diversion", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        SocioDB usuario = null;
        Cursor cursor = null;

        try {
            // Usar el metodo genérico Listar_Tabla
            cursor = Utilidades.Listar_Tabla(db, "SELECT * FROM " + Utilidades.TABLA_USERS + " LIMIT 1");

            if (cursor.moveToFirst()) {
                usuario = new SocioDB(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Utilidades.CAMPO_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Utilidades.CAMPO_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Utilidades.CAMPO_DIRECCION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Utilidades.CAMPO_TELEFONO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Utilidades.CAMPO_USERNAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Utilidades.CAMPO_IS_ADMIN)) == 1 // Convertir de int a boolean
                );
            }
        } catch (Exception e) {
            Log.e("SQLiteError", "Error al recuperar el usuario: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return usuario;
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

    // Metodo para cerrar sesión
    private void logout() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "club_diversion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Usar el método genérico Eliminar_Tabla
        int result = Utilidades.Eliminar_Tabla(Utilidades.TABLA_USERS, null, null, db);

        if (result > 0) {
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo cerrar sesión", Toast.LENGTH_SHORT).show();
        }

        db.close();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(), "Regreso", Toast.LENGTH_SHORT).show();
        Log.e("Salida","Regreso");
        Inicio();
    }

}