package com.example.clubdiversion.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubdiversion.R;
import com.example.clubdiversion.data.entities.ReservationResponse;
import com.example.clubdiversion.data.repository.UserRepository;
import com.example.clubdiversion.ui.edit.EditActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.View {

    private TextView txtName, txtEmail, txtPhone, txtAddress;
    private ProgressBar progressBar;
    private ProfileContract.Presenter presenter;
    private RecyclerView recyclerViewReservations;
    private ReservationsAdapter reservationsAdapter;
    private UserRepository userRepository; // Para obtener datos del usuario
    private Button btnEditProfile;


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
        btnEditProfile = findViewById(R.id.buttonEditProfile);

        // Configuración del RecyclerView
        reservationsAdapter = new ReservationsAdapter(new ArrayList<>());
        recyclerViewReservations.setAdapter(reservationsAdapter);
        recyclerViewReservations.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar repositorio y presenter
        userRepository = new UserRepository(this);
        presenter = new ProfilePresenter(this, this);
        presenter.loadProfile();
        presenter.loadReservations();

        // Configurar acción del botón Editar Perfil
        btnEditProfile.setOnClickListener(v -> navigateToEditProfile());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshUserProfile();
    }
    private void navigateToEditProfile() {
        if (userRepository.getCurrentUser() != null) {
            Intent intent = new Intent(ProfileActivity.this, EditActivity.class);
            intent.putExtra("user_id", userRepository.getCurrentUser().getId());
            intent.putExtra("user_name", txtName.getText().toString());
            intent.putExtra("user_direccion", txtAddress.getText().toString());
            intent.putExtra("user_telefono", txtPhone.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "No se pudo obtener el usuario actual", Toast.LENGTH_SHORT).show();
        }
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
