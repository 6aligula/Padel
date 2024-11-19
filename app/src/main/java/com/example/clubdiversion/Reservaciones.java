package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubdiversion.Utilidades.Utilidades;

import java.util.Calendar;
import java.util.Locale;

public class Reservaciones extends AppCompatActivity {

    ImageView imageInstalacionReser;
    TextView txtNombreReser;
    TextView txtSocioReser;
    TextView editfechaReser;

    long FechaActual;
    long FechaReser;
    int NumeroReservacion;
    private int ultimoAnio, ultimoMes, ultimoDiaDelMes;
    int[] imagen = {
            0,
            R.drawable.descarga1,
            R.drawable.descarga2,
            R.drawable.descarga3,
            R.drawable.descarga4,
            R.drawable.descarga5,
            R.drawable.descarga6,
            R.drawable.descarga7,
            R.drawable.descarga8,
            R.drawable.descarga9

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regresar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Salir1) {
            finish(); // Cierra la actividad
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    TextView techado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones);
        imageInstalacionReser = findViewById(R.id.imageInstalacionReser);
        txtNombreReser = findViewById(R.id.txtNombreReser);
        txtSocioReser = findViewById(R.id.txtSocioReser);
        editfechaReser = findViewById(R.id.editfechaReser);
        techado = findViewById(R.id.techado);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle = getIntent().getExtras();
        NumeroReservacion = bundle.getInt("Imagen");
        imageInstalacionReser.setImageResource(imagen[ NumeroReservacion]);
        txtNombreReser.setText(bundle.getString("Espacio"));
        txtSocioReser.setText(String.format("   %s",  bundle.getString("Socio")));

        techado.setText("Techado: "+bundle.getString("techado"));

        final Calendar calendario = Calendar.getInstance();
        ultimoAnio = calendario.get(Calendar.YEAR);
        ultimoMes = calendario.get(Calendar.MONTH);
        ultimoDiaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
        FechaActual = ultimoAnio*10000+ultimoMes*100+ultimoDiaDelMes;

        editfechaReser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(Reservaciones.this, AlertDialog.BUTTON_POSITIVE, listenerDeDatePicker, ultimoAnio, ultimoMes, ultimoDiaDelMes);
                dialogoFecha.show();
            }
        });

        // Recuperar nombre del usuario desde la base de datos
        loadUserName();

    }
    public void refrescarFechaEnEditText() {
        // Formateamos la fecha pero podríamos hacer cualquier otra cosa ;)
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", ultimoDiaDelMes,  ultimoMes+1, ultimoAnio);
        final Calendar calendario = Calendar.getInstance();

        // La ponemos en el editText
        editfechaReser.setText(fecha);
    }

    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int anio, int mes, int diaDelMes) {
            // Esto se llama cuando seleccionan una fecha. Nos pasa la vista, pero más importante, nos pasa:
            // El año, el mes y el día del mes. Es lo que necesitamos para saber la fecha completa
            Log.e("Salida","EMPEZO");

            Log.e("Salida","FechaInicial = "+ultimoAnio+"/"+ultimoMes+"/"+ultimoDiaDelMes);
            Log.e("Salida","FechaReserva = "+anio+"/"+mes+"/"+diaDelMes);

            FechaReser = anio*10000+mes*100+diaDelMes;
            //if(FechaReser<FechaActual)
            Log.e("Salida","Actual = "+FechaActual+" Reser = "+FechaReser);
            //refrescarFechaEnEditText();
            String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", diaDelMes,  mes+1, anio);
            if(FechaActual<FechaReser)
                editfechaReser.setText(fecha);
            else
                editfechaReser.setText("Pulse aquí para la fecha");

        }
    };

    public void btnAceptarInsta(View view) {
        String FECHA = editfechaReser.getText().toString().trim();
        if (!FECHA.equals("Pulse aquí para la fecha")) {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "club_diversion", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            String query = "SELECT * FROM " + Utilidades.T_INST + " WHERE " + Utilidades.INST_FECHA + " ='" + FECHA +
                    "' AND " + Utilidades.INST_ID_INST + "='" + NumeroReservacion + "'";
            if (Utilidades.BuscaLogica(db, query)) {
                Toast.makeText(getApplicationContext(), "Instalación ya reservada", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues values = new ContentValues();
                values.put(Utilidades.INST_FECHA, FECHA);
                values.put(Utilidades.INST_ID_INST, NumeroReservacion);
                Utilidades.Insertar_En_Tabla(Utilidades.T_INST, values, db);
                Toast.makeText(getApplicationContext(), "Reservación realizada", Toast.LENGTH_SHORT).show();
            }
            db.close();
        } else {
            Toast.makeText(getApplicationContext(), "Seleccione la fecha", Toast.LENGTH_SHORT).show();
        }
    }

    public void bntListReser(View view) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "club_diversion", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String query = "SELECT * FROM " + Utilidades.T_INST + " WHERE 1";
        Cursor c = Utilidades.Listar_Tabla(db, query);

        if (c != null && c.moveToFirst()) {
            do {
                int c0 = c.getInt(0);
                String c1 = c.getString(1);
                String c2 = c.getString(2);
                Log.e("Salida", "id=" + c0 + "  " + c1 + "   " + c2 + "  ");
            } while (c.moveToNext());
        } else {
            Log.e("Salida", "Vacio");
        }

        if (c != null) c.close();
        db.close();
    }
    private void loadUserName() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "club_diversion", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String query = "SELECT " + Utilidades.CAMPO_NAME + " FROM " + Utilidades.TABLA_USERS + " LIMIT 1";
        Cursor cursor = Utilidades.Listar_Tabla(db, query);

        if (cursor != null && cursor.moveToFirst()) {
            String userName = cursor.getString(0); // CAMPO_NAME
            txtSocioReser.setText(String.format("   %s", userName));
            cursor.close();
        } else {
            txtSocioReser.setText("   Usuario desconocido");
        }

        db.close();
    }
}