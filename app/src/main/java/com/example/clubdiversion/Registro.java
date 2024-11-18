package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.clubdiversion.Utilidades.Utilidades;

public class Registro extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regresar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); // Obtén el ID del item seleccionado
        if (itemId == R.id.Salir1) {  // Compara manualmente el ID
            finish(); // Cierra la actividad
            return true;
        }
        return super.onOptionsItemSelected(item); // Maneja otros casos
    }


    private EditText editSocioRegistro;
    private EditText editPasswordRegistro;
    private int TipoUsuario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio);

        // Conexión a la base de datos SQLite
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "clubdiversion", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        // Consultar si hay un usuario registrado en SQLite
        Cursor cursor = Utilidades.getUser(db);

        if (cursor.moveToFirst()) {
            // Validar que las columnas existen en el cursor
            int usernameIndex = cursor.getColumnIndex(Utilidades.COL_USERNAME);
            int emailIndex = cursor.getColumnIndex(Utilidades.COL_EMAIL);

            if (usernameIndex >= 0 && emailIndex >= 0) {
                // Obtener datos del usuario desde el cursor
                String username = cursor.getString(usernameIndex);
                String email = cursor.getString(emailIndex);
                Toast.makeText(this, "Bienvenido de nuevo, " + username, Toast.LENGTH_SHORT).show();

                // Redirigir al usuario a la actividad principal
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Manejo de error si las columnas no existen
                Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Si no hay usuario, mostrar mensaje y continuar con el flujo normal
            Toast.makeText(this, "Por favor, inicia sesión o regístrate", Toast.LENGTH_SHORT).show();
        }

        // Cerrar el cursor y la base de datos para liberar recursos
        cursor.close();
        db.close();
    }

    public void Login(View view) {
        String SOCIO = editSocioRegistro.getText().toString().trim();
        String PASS  = editPasswordRegistro.getText().toString().trim();
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String query ="SELECT "+Utilidades.SOCIO_NOMBRE+", "+Utilidades.SOCIO_S_A+", " +
                Utilidades.SOCIO_ID+" FROM "+Utilidades.TABLA_SOCIO +" " +
                "WHERE "+Utilidades.SOCIO_NIP+" ='"+SOCIO+"' and " +
                ""+Utilidades.SOCIO_PASS+" = '"+PASS+"'";
        Log.e("Salida",query);
        Cursor c = Utilidades.BuscaSocio(db, query);
        c.moveToFirst();
        if(c != null && c.getCount()>0)
        {
            Log.e("Salida","aceptado "+c.getString(0)+"  "+c.getString(1)+"  "+c.getString(2));
            Utilidades.NOMBRE_SOCIO = c.getString(0);
            Utilidades.SOCIO_ADMINISTRADOR = c.getInt(1);
            Utilidades.SO_AD_ID = c.getInt(1);

            Intent intent = new Intent(this, Instalaciones.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Socio no Registrado", Toast.LENGTH_SHORT).show();
        }
        db.close();


    }


}