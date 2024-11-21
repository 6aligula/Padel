package com.example.clubdiversion.ui.reservaciones;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.clubdiversion.R;

import java.util.List;

public class ReservacionesActivity extends AppCompatActivity implements ReservacionesContract.View {

    private ReservacionesContract.Presenter presenter;
    private ImageView imageInstalacionReser;
    private TextView txtNombreReser, txtSocioReser, editfechaReser;

    private int reservationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservaciones);

        presenter = new ReservacionesPresenter(this, this);

        imageInstalacionReser = findViewById(R.id.imageInstalacionReser);
        txtNombreReser = findViewById(R.id.txtNombreReser);
        txtSocioReser = findViewById(R.id.txtSocioReser);
        editfechaReser = findViewById(R.id.editfechaReser);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle bundle = getIntent().getExtras();
        reservationNumber = bundle.getInt("Imagen");
        int imageResource = bundle.getInt("Imagen", -1); // Valor predeterminado para evitar crashes
        Log.d("ReservacionesActivity", "Recurso recibido: " + imageResource);
        if (imageResource != -1) {
            imageInstalacionReser.setImageResource(imageResource);
        } else {
            Log.e("ReservacionesActivity", "Recurso inválido. No se puede cargar la imagen.");
            Toast.makeText(this, "Error: Imagen no encontrada", Toast.LENGTH_SHORT).show();
        }
        txtNombreReser.setText(bundle.getString("Espacio"));

        editfechaReser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama al presenter para manejar la selección de fecha
                presenter.showDatePicker();
            }
        });


        presenter.loadUserName();
    }
    public void btnAceptarInsta(View view) {
        String fecha = editfechaReser.getText().toString().trim();
        presenter.onAcceptReservation(fecha, reservationNumber);
    }

    @Override
    public void showDate(String date) {
        editfechaReser.setText(date);
    }

    @Override
    public void showReservationSuccess() {
        Toast.makeText(this, "Reservación realizada", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReservationError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showReservationsList(List<String> reservations) {
        for (String reservation : reservations) {
            Toast.makeText(this, reservation, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void showUserName(String name) {
        txtSocioReser.setText("   " + name);
    }

    @Override
    public void showUnknownUser() {
        txtSocioReser.setText("   Usuario desconocido");
    }

    @Override
    public void showDateError() {
        Toast.makeText(this, "Fecha no válida", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ReservacionesActivity", "onDestroy llamado: Recursos liberados.");
    }
}
