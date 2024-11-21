package com.example.clubdiversion.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubdiversion.R;
import com.example.clubdiversion.data.entities.ReservationResponse;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    private TextView txtName, txtEmail, txtPhone, txtAddress;
    private ProgressBar progressBar;
    private ProfileContract.Presenter presenter;
    private RecyclerView recyclerViewReservations;
    private ReservationsAdapter reservationsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        progressBar = findViewById(R.id.progressBarProfile);

        recyclerViewReservations = findViewById(R.id.recyclerViewReservations);

        // Configuración del RecyclerView
        reservationsAdapter = new ReservationsAdapter(new ArrayList<>());
        recyclerViewReservations.setAdapter(reservationsAdapter);
        recyclerViewReservations.setLayoutManager(new LinearLayoutManager(this));

        presenter = new ProfilePresenter(this, this);
        presenter.loadProfile();
        presenter.loadReservations();
    }

    @Override
    public void showReservations(List<ReservationResponse> reservations) {
        reservationsAdapter.updateReservations(reservations); // Actualiza el adaptador con las reservas recibidas
    }

    @Override
    public void showProfile(String name, String email, String phone, String address) {
        txtName.setText(name);
        txtEmail.setText(email);
        txtPhone.setText(phone);
        txtAddress.setText(address);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
