package com.example.clubdiversion.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clubdiversion.R;
import com.example.clubdiversion.data.entities.UserResponse;
import com.example.clubdiversion.data.repository.UserRepository;
import com.example.clubdiversion.ui.adapters.UserAdapter;
import com.example.clubdiversion.ui.edit.EditActivity;
import android.util.Log;

import java.util.List;

public class AdminUserActivity extends AppCompatActivity implements AdminUserContract.View {
    private AdminUserContract.Presenter presenter;
    private RecyclerView recyclerView;
    private Button btnAddUser;
    private UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        userRepository = new UserRepository(this); // Instancia del repositorio
        String token = userRepository.getToken(); // Obtén el token

        recyclerView = findViewById(R.id.recyclerViewUsers);
        btnAddUser = findViewById(R.id.btnAddUser);

        presenter = new AdminUserPresenter(this, token); // Pasamos el token al Presenter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        presenter.getAllUsers();
        configureAddUserButton();
    }

    private void configureAddUserButton() {
        btnAddUser.setOnClickListener(view -> {
            Intent intent = new Intent(AdminUserActivity.this, com.example.clubdiversion.ui.register.RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getAllUsers(); // Refresca la lista al regresar a esta actividad
    }
    @Override
    public void showUsers(List<UserResponse> users) {
        if (users == null || users.isEmpty()) {
            Toast.makeText(this, "No hay usuarios disponibles", Toast.LENGTH_SHORT).show();
            return;
        }
        UserAdapter adapter = new UserAdapter(users, new UserAdapter.OnUserDeleteListener() {
            @Override
            public void onDelete(int userId) {
                presenter.deleteUser(userId);
            }
        }, user -> {
            Log.d("AdminUserActivity", "isAdmin enviado: " + user.isAdmin());
            Intent intent = new Intent(AdminUserActivity.this, EditActivity.class);
            intent.putExtra("user_id", user.getId());
            intent.putExtra("user_nip", user.getUsername());
            intent.putExtra("user_name", user.getName());
            intent.putExtra("user_direccion", user.getDireccion());
            intent.putExtra("user_telefono", user.getTelefono());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        presenter.getAllUsers();
    }
}
