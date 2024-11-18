package com.example.clubdiversion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clubdiversion.Entidades.LoginRequest;
import com.example.clubdiversion.Entidades.LoginResponse;
import com.example.clubdiversion.Entidades.RegisterRequest;
import com.example.clubdiversion.Entidades.RegisterResponse;
import com.example.clubdiversion.Utilidades.ApiService;
import com.example.clubdiversion.Utilidades.RetrofitClient;
import com.example.clubdiversion.Utilidades.Utilidades;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SocioRegistrar extends AppCompatActivity {

    private RadioButton tipoSocSocio;
    private RadioButton tipoAdmSocio;
    private int TipoUsuario = 0;
    private EditText editNIPSocio;
    private EditText editNombreSocio;
    private EditText editDireccionSocio;
    private EditText editTelefonoSocio;
    private EditText editPassSocio;
    private EditText editRePassSocio;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_regresar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Salir1) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        //tipoSocSocio = findViewById(R.id.tipoSocSocio);
        //tipoAdmSocio = findViewById(R.id.tipoAdmSocio);

        editNIPSocio = findViewById(R.id.editNIPSocio);
        editNombreSocio = findViewById(R.id.editNombreSocio);
        editDireccionSocio = findViewById(R.id.editDireccionSocio);
        editTelefonoSocio = findViewById(R.id.editTelefonoSocio);
        editPassSocio = findViewById(R.id.editPassSocio);
        editRePassSocio = findViewById(R.id.editRePassSocio);
        editNIPSocio.requestFocus();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            VariableGlobales.ChequeaPermisos(getApplicationContext(),this);

        }


    }
    private int TipoUser()
    {
        if(tipoAdmSocio.isChecked())
            TipoUsuario=1;
        else
            TipoUsuario=0;
        return TipoUsuario;
    }


    private ContentValues ValidarCampos(String NIP, String Nombre, String Direccion, String TLF, String PASS, String REPASS)
    {
        ContentValues values = new ContentValues();
        if(NIP.trim().equals("") || Nombre.trim().equals("") || Direccion.trim().equals("") || TLF.trim().equals("")  || PASS.trim().equals("") || REPASS.trim().equals(""))
        {
            values.put(Utilidades.SOCIO_NIP,"");
            values.put(Utilidades.SOCIO_NOMBRE,"");
            values.put(Utilidades.SOCIO_DIRECCION,"");
            values.put(Utilidades.SOCIO_TLF,"");
            values.put(Utilidades.SOCIO_S_A,TipoUsuario);
            values.put(Utilidades.SOCIO_PASS,"");

        }
        else
        {
            values.put(Utilidades.SOCIO_NIP,NIP);
            values.put(Utilidades.SOCIO_NOMBRE,Nombre);
            values.put(Utilidades.SOCIO_DIRECCION,Direccion);
            values.put(Utilidades.SOCIO_TLF,TLF);
            values.put(Utilidades.SOCIO_S_A,TipoUsuario);
            values.put(Utilidades.SOCIO_PASS,PASS);
        }
        return values;

    }

    public void registrarSocio()
    {
        TipoUsuario=TipoUser();
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String NIP = editNIPSocio.getText().toString().trim();
        String Nombre = editNombreSocio.getText().toString().trim();
        String Direccion = editDireccionSocio.getText().toString().trim();
        String TLF = editTelefonoSocio.getText().toString().trim();
        String PASS =   editPassSocio.getText().toString().trim();
        String REPASS =   editRePassSocio.getText().toString().trim();

        ContentValues values = ValidarCampos(NIP, Nombre, Direccion, TLF,  PASS, REPASS);

        if(values.getAsString(Utilidades.SOCIO_NIP).equals(""))
        {
            Toast.makeText(getApplicationContext(), "Algún campo esta vacio!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String query ="SELECT * FROM "+Utilidades.TABLA_SOCIO +" WHERE nip ='"+NIP+"'";
            if (Utilidades.BuscaLogica(db, query))
                Toast.makeText(getApplicationContext(), "Socio ya registrado", Toast.LENGTH_SHORT).show();
            else
            {
                if(PASS.trim().equals(REPASS))
                {
                    Long i = Utilidades.Insertar_En_Tabla(Utilidades.TABLA_SOCIO, values, db );
                    Log.e("Salida","Long = "+i);
                    Log.e("Salida",values.toString());
                    LimpiarCampos();
                }
                else
                    Toast.makeText(getApplicationContext(), "Verifique las contraseña", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();

    }

    private void LimpiarCampos() {
        editNIPSocio.setText("");
        editNombreSocio.setText("");
        editDireccionSocio.setText("");
        editTelefonoSocio.setText("");
        tipoSocSocio.setChecked(true);
        editPassSocio.setText("");
        editRePassSocio.setText("");
        editNIPSocio.requestFocus();
    }

    public void Validar(View view) {
        String username = editNIPSocio.getText().toString().trim();
        String password = editPassSocio.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            login(username, password);
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void listar(View view) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String query ="SELECT  * FROM "+Utilidades.TABLA_SOCIO+" WHERE 1";
        Cursor c= Utilidades.Listar_Tabla(db,query);
        //db.rawQuery("SELECT  * FROM "+Utilidades.TABLA_SOCIO+" WHERE 1",null);
        c.moveToFirst();
        if(c != null && c.getCount()>0)
            do
            {
                int c0= c.getInt(0);
                String c1= c.getString(1);
                String c2= c.getString(2);
                String c3= c.getString(3);
                String c4= c.getString(4);
                String c5= c.getString(5);
                String c6= c.getString(6);
                Log.e("Salida","id="+c0+"  "+c1+"   "+c2+"  "+c3+"   "+c4+"  "+c5+"  "+c6);

            }while (c.moveToNext());
            else
                Log.e("Salida","Vacio");
        c.close();
        db.close();

    }

    public void upgrade(View view) {
        TipoUsuario=TipoUser();
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();


        String NIP = editNIPSocio.getText().toString().trim();
        String Nombre = editNombreSocio.getText().toString().trim();
        String Direccion = editDireccionSocio.getText().toString().trim();
        String TLF = editTelefonoSocio.getText().toString().trim();
        String PASS =   editPassSocio.getText().toString().trim();
        String REPASS =   editPassSocio.getText().toString().trim();

        ContentValues values = ValidarCampos(NIP, Nombre, Direccion, TLF,  PASS, REPASS);
        if(values.getAsString(Utilidades.SOCIO_NIP).equals(""))
        {
            Toast.makeText(getApplicationContext(), "Algún campo esta vacio!!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String query ="SELECT * FROM "+Utilidades.TABLA_SOCIO +" WHERE nip ='"+NIP+"'";
            if (!Utilidades.BuscaLogica(db, query))
                Toast.makeText(getApplicationContext(), "Socio ya registrado", Toast.LENGTH_SHORT).show();
            else
            {
                String[] args = new String []{ NIP };

                db.update(Utilidades.TABLA_SOCIO, values, Utilidades.SOCIO_NIP+"=?", args);
                int i= Utilidades.Upgrade_Tabla(Utilidades.TABLA_SOCIO, values, Utilidades.SOCIO_NIP+"=?", args, db);

                Log.e("Salida","Long = "+i);

                LimpiarCampos();
            }
        }
        db.close();
    }

    public void Eliminar(View view) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String Nip = editNIPSocio.getText().toString().trim();


        String[] args = new String[]{Nip};
        Utilidades.Eliminar_Tabla(Utilidades.TABLA_SOCIO, Utilidades.SOCIO_ID+"=?",  args,  db );
//        db.delete(Utilidades.TABLA_SOCIO, Utilidades.SOCIO_ID+"=?", args);
        db.close();
    }

    public void BorrarTabla(View view) {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), Utilidades.BASE_DATOS,null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //db.execSQL("DELETE from socio where 1");
        String NIP = editNIPSocio.getText().toString().trim();
        String query ="SELECT * FROM "+Utilidades.TABLA_SOCIO +" WHERE nip ='"+NIP+"'";
        Cursor c = Utilidades.BuscaSocio(db, query);

        if (c.moveToFirst())
        {
            Log.e("Salida","Si existe");
            editNombreSocio.setText(c.getString(2));
            editDireccionSocio.setText(c.getString(3));
            editTelefonoSocio.setText(c.getString(4));
            Log.e("Salida",""+c.getInt(6));
            if(c.getInt(6)==0)
                tipoSocSocio.setChecked(true);
            else
                tipoAdmSocio.setChecked(true);
            editPassSocio.setText(c.getString(7));
            editRePassSocio.setText(c.getString(7));
        }
        else
        {
            Log.e("Salida","No existe");
            Toast.makeText(getApplicationContext(), "Socio no registrado", Toast.LENGTH_SHORT).show();
            LimpiarCampos();
        }
        db.close();
    }

    private void login(String username, String password) {
        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(username, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();

                    // Guardar datos en SQLite
                    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(SocioRegistrar.this, "clubdiversion", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    Utilidades.insertUser(loginResponse.getUsername(), loginResponse.getEmail(), token, db);
                    db.close();

                    Toast.makeText(SocioRegistrar.this, "Bienvenido, " + loginResponse.getUsername(), Toast.LENGTH_SHORT).show();

                    // Redirigir al usuario
                    Intent intent = new Intent(SocioRegistrar.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SocioRegistrar.this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SocioRegistrar.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void register(String nip, String nombre, String direccion, String telefono, String password) {
        ApiService apiService = RetrofitClient.getApiService();

        RegisterRequest registerRequest = new RegisterRequest(nip, nombre, direccion, telefono, password);

        apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(SocioRegistrar.this, "Usuario registrado: " + registerResponse.getUsername(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SocioRegistrar.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SocioRegistrar.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}